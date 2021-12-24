package com.funicorn.cloud.upms.center.dto;

import com.funicorn.basic.common.base.valid.Insert;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * @author Aimee
 * @since 2021/5/6 16:49
 */
@Data
public class UserDTO implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 用户id 自动生成
     */
    private String id;

    /**
     * 用户名账号
     */
    @NotBlank(message = "用户名不能为空",groups = Insert.class)
    private String username;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 昵称
     */
    @NotBlank(message = "昵称不能为空",groups = Insert.class)
    private String nickName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 证件类型 0身份证 1港澳通行证 2学生证 3护照 4士官证 5驾驶证
     */
    private Integer idType;

    /**
     * 证件号码
     */
    private String idCard;

    /**
     * 联系地址
     */
    private String address;

    /**
     * 用户头像
     */
    private String headLogo;

    /**
     * 租户id数组
     * */
    private List<String> tenantIds;
}
