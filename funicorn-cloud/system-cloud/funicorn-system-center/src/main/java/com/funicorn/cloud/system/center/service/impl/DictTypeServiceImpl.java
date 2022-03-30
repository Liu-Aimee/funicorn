package com.funicorn.cloud.system.center.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.funicorn.basic.common.base.util.JsonUtil;
import com.funicorn.cloud.system.center.constant.SystemConstant;
import com.funicorn.cloud.system.center.dto.DictTypeDTO;
import com.funicorn.cloud.system.center.entity.DictItem;
import com.funicorn.cloud.system.center.entity.DictType;
import com.funicorn.cloud.system.center.exception.SystemErrorCode;
import com.funicorn.cloud.system.center.exception.SystemException;
import com.funicorn.cloud.system.center.mapper.DictItemMapper;
import com.funicorn.cloud.system.center.mapper.DictTypeMapper;
import com.funicorn.cloud.system.center.service.DictTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 字典类型 服务实现类
 * </p>
 *
 * @author Aimee
 * @since 2021-10-28
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DictTypeServiceImpl extends ServiceImpl<DictTypeMapper, DictType> implements DictTypeService {

    @Resource
    private DictItemMapper dictItemMapper;

    @Override
    public void add(DictTypeDTO dictTypeDTO) {
        int typeCount = baseMapper.selectCount(Wrappers.<DictType>lambdaQuery()
                .eq(DictType::getType, dictTypeDTO.getType())
                .eq(DictType::getTenantId, dictTypeDTO.getTenantId())
                .eq(DictType::getIsDelete, SystemConstant.NOT_DELETED));
        if (typeCount > 0) {
            throw new SystemException(SystemErrorCode.DICT_TYPE_EXISTS, dictTypeDTO.getType());
        }
        DictType dictType = JsonUtil.object2Object(dictTypeDTO, DictType.class);
        baseMapper.insert(dictType);
    }

    @Override
    public void delete(String id) {
        DictType dictType = baseMapper.selectById(id);
        if (dictType ==null || SystemConstant.IS_DELETED.equals(dictType.getIsDelete())){
            return;
        }
        DictItem dictItem = new DictItem();
        dictItem.setIsDelete(SystemConstant.IS_DELETED);
        dictItemMapper.update(dictItem,Wrappers.<DictItem>lambdaUpdate().eq(DictItem::getDictType,dictType.getType()));
        dictType.setIsDelete(SystemConstant.IS_DELETED);
        baseMapper.updateById(dictType);
    }
}
