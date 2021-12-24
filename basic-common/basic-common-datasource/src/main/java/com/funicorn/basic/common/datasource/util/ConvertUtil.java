package com.funicorn.basic.common.datasource.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.funicorn.basic.common.base.util.JsonUtil;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 类型转换工具类
 * @author Aimee
 * @since 2021/10/20 8:48
 */
public class ConvertUtil {

    /**
     * 分页出参类型转换
     * @param page 分页参数
     * @param clazz 转换类型
     * @param <T> 泛型
     * @return IPage
     * */
    public static <T> IPage<T> page2Page(IPage<?> page, Class<T> clazz){
        IPage<T> iPage = new Page<>();
        BeanUtils.copyProperties(page,iPage);
        try {
            iPage.setRecords(list2List(page.getRecords(),clazz));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return iPage;
    }

    /**
     * list集合出参类型转换
     * @param originList 原始list
     * @param clazz 转换类型
     * @param <T> 泛型
     * @return List
     * */
    public static <T> List<T> list2List(List<?> originList, Class<T> clazz){
        List<T> rs = new ArrayList<>();
        try {
            for (Object o : originList) {
                rs.add(JsonUtil.object2Object(o,clazz));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  rs;
    }
}
