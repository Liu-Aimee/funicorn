package com.funicorn.cloud.system.center.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.funicorn.basic.common.base.model.Result;
import com.funicorn.basic.common.security.model.CurrentUser;
import com.funicorn.basic.common.security.util.SecurityUtil;
import com.funicorn.cloud.system.api.model.UploadFileData;
import com.funicorn.cloud.system.center.constant.SystemConstant;
import com.funicorn.cloud.system.center.dto.UploadFileDTO;
import com.funicorn.cloud.system.center.dto.UploadFilePageDTO;
import com.funicorn.cloud.system.center.entity.UploadFile;
import com.funicorn.cloud.system.center.exception.SystemErrorCode;
import com.funicorn.cloud.system.center.exception.SystemException;
import com.funicorn.cloud.system.center.service.UploadFileService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * 文件上传信息 接口
 *
 * @author Aimee
 * @since 2021-11-08
 */
@RestController
@RequestMapping("/File")
public class UploadFileController {

    @Resource
    private UploadFileService uploadFileService;

    /**
     * 上传列表分页查询
     * @param uploadFilePageDTO 入参
     * @return Result
     * */
    @GetMapping("/page")
    public Result<IPage<UploadFile>> page(UploadFilePageDTO uploadFilePageDTO){
        CurrentUser currentUser = SecurityUtil.getCurrentUser();
        LambdaQueryWrapper<UploadFile> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UploadFile::getCreatedBy,currentUser.getUsername());
        queryWrapper.eq(UploadFile::getTenantId,currentUser.getTenantId());
        queryWrapper.eq(UploadFile::getIsDelete, SystemConstant.NOT_DELETED);
        queryWrapper.eq(UploadFile::getTenantId, uploadFilePageDTO.getTenantId());
        if (StringUtils.isNotBlank(uploadFilePageDTO.getFileName())) {
            queryWrapper.like(UploadFile::getFileName,uploadFilePageDTO.getFileName());
        }
        if (StringUtils.isNotBlank(uploadFilePageDTO.getSuffix())) {
            if (!uploadFilePageDTO.getSuffix().startsWith(".")) {
                uploadFilePageDTO.setSuffix("." + uploadFilePageDTO.getSuffix());
            }
            queryWrapper.eq(UploadFile::getSuffix,uploadFilePageDTO.getSuffix());
        }
        if (StringUtils.isNotBlank(uploadFilePageDTO.getBucketName())) {
            queryWrapper.like(UploadFile::getBucketName,uploadFilePageDTO.getBucketName());
        }
        queryWrapper.orderByDesc(UploadFile::getCreatedTime);
        return Result.ok(uploadFileService.page(new Page<>(uploadFilePageDTO.getCurrent(),uploadFilePageDTO.getSize()),queryWrapper));
    }

    /**
     * 下载列表分页查询
     * @param uploadFilePageDTO 入参
     * @return Result
     * */
    @GetMapping("/downloadPage")
    public Result<IPage<UploadFile>> downloadPage(UploadFilePageDTO uploadFilePageDTO){
        CurrentUser currentUser = SecurityUtil.getCurrentUser();
        LambdaQueryWrapper<UploadFile> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UploadFile::getDownFlag,true);
        queryWrapper.eq(UploadFile::getTenantId,currentUser.getTenantId());
        queryWrapper.eq(UploadFile::getIsDelete, SystemConstant.NOT_DELETED);
        if (StringUtils.isNotBlank(uploadFilePageDTO.getFileName())) {
            queryWrapper.like(UploadFile::getFileName,uploadFilePageDTO.getFileName());
        }
        if (StringUtils.isNotBlank(uploadFilePageDTO.getSuffix())) {
            if (!uploadFilePageDTO.getSuffix().startsWith(".")) {
                uploadFilePageDTO.setSuffix("." + uploadFilePageDTO.getSuffix());
            }
            queryWrapper.eq(UploadFile::getSuffix,uploadFilePageDTO.getSuffix());
        }
        if (StringUtils.isNotBlank(uploadFilePageDTO.getBucketName())) {
            queryWrapper.like(UploadFile::getBucketName,uploadFilePageDTO.getBucketName());
        }
        queryWrapper.orderByDesc(UploadFile::getCreatedTime);
        return Result.ok(uploadFileService.page(new Page<>(uploadFilePageDTO.getCurrent(),uploadFilePageDTO.getSize()),queryWrapper));
    }

    /**
     * 单文件上传
     * @param file 文件流
     * @param bucketName 桶名称
     * @param downFlag 是否允许其他人下载 默认false
     * @return Result
     * @throws Exception 异常
     * */
    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<UploadFileData> upload(@RequestParam("file") MultipartFile file,
                                         @RequestParam String bucketName,
                                         @RequestParam(required = false,defaultValue = "false") boolean downFlag) throws Exception {
        return Result.ok(uploadFileService.upload(file,bucketName,downFlag));
    }

    /**
     * 多文件上传
     * @param files 文件流数组
     * @param bucketName 桶名称
     * @param downFlag 是否允许其他人下载 默认false
     * @return Result
     * @throws Exception 异常
     * */
    @PostMapping(value = "/multiUpload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<List<UploadFileData>> multiUpload(@RequestParam("files") MultipartFile[] files,
                                                    @RequestParam String bucketName,
                                                    @RequestParam(required = false,defaultValue = "false") boolean downFlag) throws Exception {
        return Result.ok(uploadFileService.multiUpload(files,bucketName,downFlag));
    }

    /**
     * 文件下载
     * @param fileId 文件id
     * @param response 返回流 不用传
     * */
    @GetMapping("/download")
    public void download(@RequestParam String fileId, HttpServletResponse response) throws Exception {
        InputStream is = uploadFileService.download(fileId);
        if (is==null) {
            throw new SystemException(SystemErrorCode.FILE_NOT_FOUND);
        }

        UploadFile uploadFile = uploadFileService.getById(fileId);
        String fileName = uploadFile.getFileName();
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.setContentType("application/x-download");
        response.addHeader("Content-Disposition" ,"attachment;filename=" +fileName);
        response.setCharacterEncoding("UTF-8");
        IOUtils.copy(is,response.getOutputStream());
    }

    /**
     * 获取文件外链 有效期7天
     * @param fileId 文件id
     * @return Result
     * */
    @GetMapping("/getFileHtmlUrl")
    public Result<String> getFileHtmlUrl(@RequestParam String fileId) throws Exception {
        return Result.ok(uploadFileService.getFileHtmlUrl(fileId),"获取文件外链成功");
    }

    /**
     * 修改文件是否允许下载
     * @param uploadFileDTO 入参
     * @return Result
     * */
    @PostMapping("/updateDownFlag")
    public Result<?> updateDownFlag(@RequestBody @Validated UploadFileDTO uploadFileDTO){
        UploadFile uploadFile = new UploadFile();
        uploadFile.setId(uploadFileDTO.getFileId());
        uploadFile.setDownFlag(uploadFileDTO.getDownFlag());
        uploadFileService.updateById(uploadFile);
        return Result.ok("修改成功");
    }

    /**
     * 删除文件
     * @param fileId 文件id
     * @return Result
     * */
    @DeleteMapping("/{fileId}")
    public Result<?> remove(@PathVariable("fileId") String fileId) throws Exception {
        uploadFileService.remove(fileId);
        return Result.ok("删除成功");
    }
}

