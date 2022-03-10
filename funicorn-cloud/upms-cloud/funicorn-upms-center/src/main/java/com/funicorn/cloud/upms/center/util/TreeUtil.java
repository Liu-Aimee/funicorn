package com.funicorn.cloud.upms.center.util;

import com.funicorn.cloud.upms.api.model.MenuTree;
import com.funicorn.cloud.upms.api.model.OrgTree;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Aimme
 * @since 2021/2/2 9:03
 */
public class TreeUtil {


    /**
     * 组织树构建
     * 目前看到最快速的构建方式
     * @param treeNodes 组织树集合
     * @return List 组织树
     * */
    public static List<OrgTree> buildTree(List<OrgTree> treeNodes) {
        //根据parentId进行分组
        Map<Object, List<OrgTree>> treeMap = treeNodes.stream().filter(node -> !"0".equals(node.getParentId())).collect(Collectors.groupingBy(OrgTree::getParentId));
        //设置子节点
        treeNodes.forEach(node -> node.setChildren(treeMap.get(node.getId())));
        //最后返回节点是0的子集
        return treeNodes.stream().filter(node -> "0".equals(node.getParentId())).collect(Collectors.toList());
    }

    /**
     * 组织树构建
     * 目前看到最快速的构建方式
     * @param treeNodes 组织树集合
     * @param orgId 机构id
     * @return List 组织树
     * */
    public static List<OrgTree> buildTreeByOrgId(List<OrgTree> treeNodes,String orgId) {
        //根据parentId进行分组
        Map<Object, List<OrgTree>> treeMap = treeNodes.stream().filter(node -> !"0".equals(node.getParentId())).collect(Collectors.groupingBy(OrgTree::getParentId));
        //设置子节点
        treeNodes.forEach(node -> node.setChildren(treeMap.get(node.getId())));
        //最后返回节点是0的子集
        return treeNodes.stream().filter(node -> orgId.equals(node.getParentId())).collect(Collectors.toList());
    }

    /**
     * 菜单树构建
     * @param treeNodes 菜单数组
     * @return List 菜单树
     * */
    public static List<MenuTree> buildMenuTree(List<MenuTree> treeNodes){
        //根据parentId进行分组
        Map<Object,List<MenuTree>> treeMap = treeNodes.stream().filter(node->!"0".equals(node.getParentId())).collect(Collectors.groupingBy(MenuTree::getParentId));
        //设置子节点
        treeNodes.forEach(node->node.setChildren(treeMap.get(node.getId())));
        //最后返回节点是0的子集
        return treeNodes.stream().filter(node->"0".equals(node.getParentId())).collect(Collectors.toList());
    }

}
