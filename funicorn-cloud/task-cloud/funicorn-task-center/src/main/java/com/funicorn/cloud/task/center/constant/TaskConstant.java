package com.funicorn.cloud.task.center.constant;

/**
 * @author Aimme
 * @since 2020/12/15 15:18
 */
public class TaskConstant {

    /**
     * 模板类型
     * */
    public static final String BPMN_EXTENSION = ".bpmn";
    /**
     * 模板类型
     * */
    public static final String BPMN20_XML_EXTENSION = ".bpmn20.xml";

    /**
     * 文件类型 xml
     * */
    public static final String FILE_TYPE_XML = "xml";
    /**
     * 文件类型 zip
     * */
    public static final String FILE_TYPE_ZIP = "zip";
    /**
     * 文件类型 bpmn
     * */
    public static final String FILE_TYPE_BPMN = ".bpmn";

    /**
     * 审批意见定义 同意
     * */
    public static final Integer OPINION_YES = 0;
    /**
     * 审批意见定义 拒绝
     * */
    public static final Integer OPINION_NO = 1;
    /**
     * 审批意见定义 驳回
     * */
    public static final Integer OPINION_BACK = 2;

    /**
     * 文件夹名称 bpmn
     * */
    public static final String DIR_NAME_PROCESSES = "processes";

    /**
     * 挂起状态
     */
    public static final int SUSPENSION_STATE = 2;
    /**
     * 激活状态
     */
    public static final int ACTIVATE_STATE = 1;

    /**
     * 提交人的变量名称
     */
    public static final String FLOW_SUBMITTER_VAR = "initiator";

    /**
     * 下一个审批人
     * */
    public static final String ASSIGNEE = "assignee";

    /**
     * 自动跳过节点设置属性
     */
    public static final String FLOWABLE_SKIP_EXPRESSION_ENABLED = "_FLOWABLE_SKIP_EXPRESSION_ENABLED";

    /**
     * 表单id
     * */
    public static final String FORM_ID_KEY = "formId";

    /**
     * 模型类型
     */
    public static final int MODEL_TYPE_BPMN = 0;
    public static final int MODEL_TYPE_FORM = 2;
    public static final int MODEL_TYPE_APP = 3;
    public static final int MODEL_TYPE_DECISION_TABLE = 4;
    public static final int MODEL_TYPE_CPMN = 5;

    /**
     * 后加签
     */
    public static final String AFTER_ADD_SIGN = "after";
    /**
     * 前加签
     */
    public static final String BEFORE_ADD_SIGN = "before";
}
