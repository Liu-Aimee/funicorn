package com.funicorn.cloud.task.center.constant;

/**
 * @author Aimme
 * @date 2020/12/15 15:18
 */
public enum CommentTypeEnum {

    /**
     * 审批记录类型
     * */
    SP("审批"),
    BH("驳回"),
    CH("撤回"),
    ZC("暂存"),
    QS("签收"),
    WP("委派"),
    ZH("知会"),
    ZY("转阅"),
    YY("已阅"),
    ZB("转办"),
    QJQ("前加签"),
    HJQ("后加签"),
    XTZX("系统执行"),
    TJ("提交"),
    CXTJ("重新提交"),
    SPJS("审批结束"),
    LCZZ("流程终止"),
    SQ("授权"),
    CFTG("重复跳过"),
    XT("协同"),
    PS("评审");

    /**
     * 名称
     * */
    private final String name;

    CommentTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static CommentTypeEnum getCommentTypeEnum(String type){
        for (CommentTypeEnum e : CommentTypeEnum.values()) {
            if (e.toString().equals(type)) {
                return e;
            }
        }

        return CommentTypeEnum.SP;
    }

    /**
     * 通过type获取中文描述
     *
     * @param type 类型标识
     * @return String
     */
    public static String getDescByType(String type) {
        for (CommentTypeEnum e : CommentTypeEnum.values()) {
            if (e.toString().equals(type)) {
                return e.name;
            }
        }
        return "";
    }
}
