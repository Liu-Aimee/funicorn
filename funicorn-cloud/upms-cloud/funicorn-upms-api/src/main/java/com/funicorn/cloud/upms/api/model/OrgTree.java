package com.funicorn.cloud.upms.api.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 机构树
 * @author Aimee
 * @since 2021/2/1 22:34
 */
@Data
public class OrgTree implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 机构id
     * */
    private String id;

    /**
     * 父机构id
     * */
    private String parentId;

    /**
     * 组织机构名称
     * */
    private String name;

    /**
     * 备注
     * */
    private String remark;

    /**
     * 全路径
     * */
    private String path;

    /**
     * 子节点
     * */
    private List<OrgTree> children;
}
