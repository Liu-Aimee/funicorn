package com.funicorn.cloud.system.center.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.funicorn.cloud.system.center.dto.DictTypeDTO;
import com.funicorn.cloud.system.center.entity.DictType;

/**
 * <p>
 * 字典类型 服务类
 * </p>
 *
 * @author Aimee
 * @since 2021-10-28
 */
public interface DictTypeService extends IService<DictType> {

    /**
     * 新增字典类型
     * @param dictTypeDTO 入参
     * */
    void add(DictTypeDTO dictTypeDTO);
}
