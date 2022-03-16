package com.funicorn.cloud.system.center.exception;

/**
 * @author Aimee
 * @since 2021/9/28 10:49
 * TODO -300 ~ -399
 */
@SuppressWarnings("unused")
public enum SystemErrorCode {

    /**
     * 字典类型已被占用
     * */
    DICT_TYPE_EXISTS(-300,"字典编码已被占用[%s]"),
    /**
     * 字典类型已被占用
     * */
    DICT_TYPE_NOT_FOUND(-301,"字典类型不存在[%s]"),
    /**
     * 字典项不存在
     * */
    DICT_ITEM_NOT_FOUND(-302,"字典项不存在[%s]"),

    /**
     * 文件名不能为空
     * */
    FILE_NAME_NOT_BLANK(-303,"文件名不能为空"),

    /**
     * 无权删除
     * */
    FILE_NOT_BELONGING(-304,"无权删除"),

    /**
     * 文件不存在
     * */
    FILE_NOT_FOUND(-305,"文件不存在"),

    /**
     * 文件名过长
     * */
    FILE_NAME_TOO_LONG(-306,"文件名过长，最长支持200个字符"),

    /**
     * 文件过大
     * */
    FILE_SIZE_TOO_LARGE(-307,"文件过大，最大支持[%s]"),

    /**
     * 非法的文件级别
     * */
    FILE_LEVEL_IS_ILLEGAL(-308,"非法的文件级别[%s]"),

    /**
     * 文件上传失败
     * */
    FILE_UPLOAD_ERROR(-309,"文件上传失败"),

    /**
     * 文件删除失败
     * */
    FILE_DELETE_ERROR(-310,"文件删除失败"),

    /**
     * 文件下载失败
     * */
    FILE_DOWNLOAD_ERROR(-311,"文件下载失败"),

    /**
     * 文件下载失败
     * */
    BUCKET_HAS_FILE(-312,"bucket包含文件，不允许删除"),

    /**
     * 字典项标签或者值已存在
     * */
    DICT_LABEL_OR_VALUE_IS_EXISTS(-313,"字典项标签或者值已存在"),
    /**
     * 绑定了字典项，不允许删除
     * */
    DICT_TYPE_BIND_ITEMS(-314,"绑定了字典项，不允许删除"),

    /**
     * 绑定了字典项，不允许删除
     * */
    ROUTE_PREDICATE_TYPE_IS_EXISTS(-315,"断言配置已存在"),

    /**
     * 绑定了字典项，不允许删除
     * */
    ROUTE_PREDICATE_VALUE_IS_INVALID(-316,"断言配置参数不正确"),

    /**
     * 绑定了字典项，不允许删除
     * */
    ROUTE_FILTER_TYPE_IS_EXISTS(-317,"过滤器配置已存在"),

    /**
     * 绑定了字典项，不允许删除
     * */
    ROUTE_RELOAD_FAILED(-318,"加载失败[%s]"),

    /**
     * 绑定了字典项，不允许删除
     * */
    ROUTE_UNINSTALL_FAILED(-319,"卸载失败"),

    /**
     * 绑定了字典项，不允许删除
     * */
    ROUTE_APP_IS_EXISTS(-320,"应用标识已存在"),

    /**
     * 绑定了字典项，不允许删除
     * */
    ROUTE_URI_IS_EXISTS(-321,"转发地址已存在"),

            ;

    private final int status;
    private final String message;

    SystemErrorCode(int status, String message) {
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
