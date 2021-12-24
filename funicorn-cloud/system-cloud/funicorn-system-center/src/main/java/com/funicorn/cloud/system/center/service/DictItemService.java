package com.funicorn.cloud.system.center.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.funicorn.cloud.system.center.dto.MultiDictItemDTO;
import com.funicorn.cloud.system.center.dto.DictItemPageDTO;
import com.funicorn.cloud.system.center.entity.DictItem;

/**
 * <p>
 * 数据字典 服务类
 * </p>
 *
 * @author Aimee
 * @since 2021-10-28
 */
public interface DictItemService extends IService<DictItem> {

    /**
     * 分页查询
     * @param dictItemPageDTO 入参
     * @return IPage<SysDictItem>
     * */
    IPage<DictItem> getPage(DictItemPageDTO dictItemPageDTO);

    /**
     * 批量保存字典项
     * @param multiDictItemDTO 入参
     * */
    void multiSave(MultiDictItemDTO multiDictItemDTO);
}
