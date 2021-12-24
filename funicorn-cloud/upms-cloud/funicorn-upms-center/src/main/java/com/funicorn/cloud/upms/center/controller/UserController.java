package com.funicorn.cloud.upms.center.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.funicorn.basic.common.base.model.Result;
import com.funicorn.basic.common.base.util.DateUtil;
import com.funicorn.basic.common.base.valid.Insert;
import com.funicorn.basic.common.base.valid.Update;
import com.funicorn.basic.common.security.model.CurrentUser;
import com.funicorn.basic.common.security.util.SecurityUtil;
import com.funicorn.cloud.system.api.model.FileLevel;
import com.funicorn.cloud.system.api.model.UploadFileData;
import com.funicorn.cloud.system.api.service.FunicornSystemService;
import com.funicorn.cloud.upms.api.model.UserInfo;
import com.funicorn.cloud.upms.center.constant.UpmsConstant;
import com.funicorn.cloud.upms.center.dto.UserDTO;
import com.funicorn.cloud.upms.center.dto.UserPageDTO;
import com.funicorn.cloud.upms.center.dto.UserPwdDTO;
import com.funicorn.cloud.upms.center.entity.User;
import com.funicorn.cloud.upms.center.exception.ErrorCode;
import com.funicorn.cloud.upms.center.exception.UpmsException;
import com.funicorn.cloud.upms.center.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 用户信息管理 接口
 *
 * @author Aimee
 * @since 2021-10-31
 */
@RestController
@RequestMapping("/User")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private FunicornSystemService funicornSystemService;

    /**
     * 用户列表分页查询
     * @param userPageDTO 分页入参
     * @return Result
     * */
    @GetMapping("/page")
    public Result<IPage<UserInfo>> page(UserPageDTO userPageDTO) {
        return Result.ok(userService.getUserPage(userPageDTO));
    }

    /**
     * 查询用户信息
     * @param username 用户名 默认当前登录用户名
     * @return Result
     * */
    @GetMapping("/getUserInfo")
    public Result<UserInfo> getUserInfo(@RequestParam(required = false) String username){
        return Result.ok(userService.getUserInfo(username));
    }

    /**
     * 新建用户
     * @param userDTO 入参
     * @return Result
     * */
    @PostMapping("/save")
    public Result<User> save(@RequestBody @Validated(Insert.class) UserDTO userDTO){
        userService.validate(userDTO);
        return Result.ok(userService.saveUser(userDTO));
    }

    /**
     * 修改用户
     * @param userDTO 入参
     * @return Result
     * */
    @PostMapping("/update")
    public Result<?> update(@RequestBody @Validated(Update.class) UserDTO userDTO){
        userService.validate(userDTO);
        userService.updateUser(userDTO);
        return Result.ok();
    }

    /**
     * 修改密码
     * @param pwdDto 用户信息
     * @return Result
     * */
    @PostMapping("/modifyPassword")
    public Result<?> modifyPassword(@RequestBody @Validated(Update.class) UserPwdDTO pwdDto){
        CurrentUser loginUser = SecurityUtil.getCurrentUser();
        User user = userService.getById(pwdDto.getUserId());
        if (user==null) {
            throw new UpmsException(ErrorCode.USER_NOT_EXISTS);
        }

        if (UpmsConstant.TENANT_USER_NORMAL.equals(loginUser.getType())) {
            if (BCrypt.checkpw(pwdDto.getOldPassword(),user.getPassword())) {
                throw new UpmsException(ErrorCode.OLD_PASSWORD_IS_FALSE);
            }
        } else {
            User adminUser = userService.getById(loginUser.getId());
            if (!BCrypt.checkpw(pwdDto.getOldPassword(),adminUser.getPassword())) {
                throw new UpmsException(ErrorCode.OLD_PASSWORD_IS_FALSE);
            }
        }

        User realUser = new User();

        //密码不能与前三次相同
        if (StringUtils.isNotBlank(user.getHistoryPwd())){
            String[] hisKeys = user.getHistoryPwd().split(",");
            for (String key:hisKeys) {
                if (BCrypt.checkpw(pwdDto.getPassword(),key)) {
                    throw new UpmsException(ErrorCode.PASSWORD_THE_SAME_AS_HISTORY);
                }
            }

            realUser.setId(pwdDto.getUserId());
            realUser.setPassword(BCrypt.hashpw(pwdDto.getPassword(),BCrypt.gensalt()));
            if (hisKeys.length==3){
                realUser.setHistoryPwd(realUser.getPassword() + user.getHistoryPwd().substring(user.getHistoryPwd().indexOf(",")));
            }else {
                realUser.setHistoryPwd(user.getHistoryPwd() + "," + realUser.getPassword());
            }
        }else {
            realUser.setId(pwdDto.getUserId());
            realUser.setPassword(BCrypt.hashpw(pwdDto.getPassword(),BCrypt.gensalt()));
            realUser.setHistoryPwd(realUser.getPassword());
        }

        realUser.setExpireTime(DateUtil.addMonth(new Date(),3).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        userService.updateById(realUser);
        return Result.ok();
    }

    /**
     * 上传用户头像
     * @param file 文件流
     * @return 头像访问路径
     * */
    @PostMapping(value = "/uploadHeadLogo",consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<String> uploadHeadLogo(@RequestParam(value = "file") MultipartFile file){
        List<String> suffixList = Arrays.asList("jpg","jpeg","png","JPG","JPEG","PNG");
        if (StringUtils.isBlank(file.getContentType()) || !suffixList.contains(file.getContentType().split("/")[1])) {
            throw new UpmsException(ErrorCode.NOT_SUPPORTED_PIC_SUFFIX,file.getContentType());
        }
        Result<UploadFileData> result = funicornSystemService.upload(file,null,true,FileLevel.PUBLIC.getValue());
        if (result==null || !result.isSuccess() || result.getData()==null){
            return Result.error("头像上传失败");
        }

        User userInfo = new User();
        userInfo.setId(SecurityUtil.getCurrentUser().getId());
        userInfo.setHeadLogo(result.getData().getUrl());
        userService.updateById(userInfo);
        return Result.ok(userInfo.getHeadLogo(),"上传用户头像成功");
    }

    /**
     * 启用用户
     * @param userId 用户id
     * @return Result
     */
    @PatchMapping("/enable/{userId}")
    public Result<?> enable(@PathVariable String userId) {
        User user = new User();
        user.setId(userId);
        user.setEnabled(UpmsConstant.ENABLED);
        userService.updateById(user);
        return Result.ok("启用成功");
    }

    /**
     * 禁用用户
     * @param userId 用户id
     * @return Result
     */
    @PatchMapping("/disable/{userId}")
    public Result<?> disable(@PathVariable String userId) {
        User user = new User();
        user.setId(userId);
        user.setEnabled(UpmsConstant.DISABLED);
        userService.updateById(user);
        return Result.ok("禁用成功");
    }
}

