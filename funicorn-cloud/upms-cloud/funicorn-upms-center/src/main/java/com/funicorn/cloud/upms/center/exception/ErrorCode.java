package com.funicorn.cloud.upms.center.exception;

/**
 * @author Aimee
 * @since 2020/12/14 9:28
 * -100 ~ -299
 */
@SuppressWarnings("all")
public enum ErrorCode {

    /**
     * 应用已存在此菜单名称
     * */
    MUNU_NAME_EXISTS(-100,"当前节点下已存在此菜单[%s]"),

    /**
     * 上传文件不存在
     * */
    UPLOAD_FILE_NOT_FOUND(-101,"上传文件不存在"),

    /**
     * 文件名称不能为空
     * */
    FILE_NAME_NOT_NULL(-102,"文件名称不能为空"),

    /**
     * 文件类型不支持
     * */
    FILE_TYPE_NOT_SUPPORT(-103,"文件类型不支持"),

    /**
     * 用户不存在
     * */
    USER_NOT_EXISTS(-104,"用户不存在"),

    /**
     * 用户名已被占用
     * */
    USER_IS_EXISTS(-105,"用户名已被占用"),

    /**
     * 邮箱已被占用
     * */
    EMAIL_IS_EXISTS(-106,"邮箱已被占用"),

    /**
     * 手机号已被注册
     * */
    PHONE_IS_EXISTS(-107,"手机号已被注册"),

    /**
     * 身份证已被注册
     * */
    CARD_IS_EXISTS(-108,"身份证已被注册"),

    /**
     * 当前密码与前三次使用密码相同
     * */
    PASSWORD_THE_SAME_AS_HISTORY(-109,"当前密码与前三次使用密码相同"),

    /**
     * 用户已绑定该角色
     * */
    USER_ROLE_EXISTS(-110,"用户已绑定该角色"),

    /**
     * 用户已隶属其他组织：[%s]
     * */
    USER_IN_ORG(-111,"用户已隶属其他组织：[%s]"),

    /**
     * 职位名称已存在
     * */
    POST_NAME_EXISTS(-112,"职位名称已存在[%s]"),

    /**
     * 角色名称已存在
     * */
    ROLE_NAME_EXISTS(-113,"角色名称已被占用[%s]"),

    /**
     * 应用集合不能为空
     * */
    APPS_REQUIRED_NOT_NULL(-114,"应用集合不能为空"),

    /**
     * 租户名称已存在
     * */
    TENANT_NAME_EXISTS(-115,"租户名称已存在[%s]"),

    /**
     * 租户不存在
     * */
    TENANT_NOT_EXISTS(-116,"租户不存在[%s]"),

    /**
     * 应用id已被注册
     * */
    CLIENT_ID_IS_EXISTS(-117,"应用标识已被占用[%s]"),

    /**
     * 应用名称已被注册
     * */
    APP_NAME_IS_EXISTS(-118,"应用名称已被占用[%s]"),

    /**
     * 应用id不允许修改
     * */
    APP_ID_NOT_SUPPORTED_UPDATE(-119,"应用id不允许修改"),

    /**
     * 未绑定此角色
     * */
    UNBIND_THIS_ROLE(-120,"未绑定此角色"),

    /**
     * 未绑定租户或用户不存在
     * */
    USER_UNBIND_TENANT(-121,"未绑定租户或用户不存在"),

    /**
     * 角色编码已存在
     * */
    ROLE_CODE_EXISTS(-122,"角色编码已存在[%s]"),

    /**
     * 应用不存在
     * */
    APP_NOT_EXISTS(-123,"应用不存在[%s]"),

    /**
     * 应用不存在
     * */
    PARAM_NOT_FOUND(-124,"[%s]参数不能为空"),

    /**
     * 菜单不存在
     * */
    MENU_NOT_FOUND(-125,"菜单不存在[%s]"),

    /**
     * 用户登录密码不正确
     * */
    OLD_PASSWORD_IS_FALSE(-126,"用户登录密码不正确"),

    /**
     * 组织机构不存在
     * */
    ORG_NOT_EXISTS(-127,"组织机构不存在[%s]"),

    /**
     * 组织机构名称已被占用
     * */
    ORG_NAME_IS_EXISTS(-128,"组织机构名称已被占用[%s]"),

    /**
     * 组织机构绑定了用户，不允许删除
     * */
    ORG_BIND_USER(-129,"组织机构绑定了用户，不允许删除"),

    /**
     * 组织机构不属于该租户
     * */
    ORG_NOT_IN_TENANT(-130,"组织机构不属于该租户"),

    /**
     * 不支持的图片格式
     * */
    NOT_SUPPORTED_PIC_SUFFIX(-131,"不支持的图片格式[%s]"),
    ;

    private final int status;
    private final String message;

    private ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }

}
