package com.funicorn.cloud.system.center.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.funicorn.cloud.system.center.dto.DictItemPageDTO;
import com.funicorn.cloud.system.center.entity.DictItem;
import com.funicorn.cloud.system.center.entity.DictType;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 字典类型 Mapper 接口
 * </p>
 *
 * @author Aimee
 * @since 2021-10-28
 */
public interface DictTypeMapper extends BaseMapper<DictType> {

    /**
     * 分页查询
     * @param page 分页条件
     * @param dictItemPageDTO 分页条件
     * @return IPage<SysDictItem>
     * */
    IPage<DictItem> queryPage(Page<DictItem> page, @Param("model") DictItemPageDTO dictItemPageDTO);
}
