package com.funicorn.cloud.system.center.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.funicorn.basic.common.base.util.JsonUtil;
import com.funicorn.basic.common.security.util.SecurityUtil;
import com.funicorn.cloud.system.center.constant.SystemConstant;
import com.funicorn.cloud.system.center.dto.DictTypeDTO;
import com.funicorn.cloud.system.center.entity.DictType;
import com.funicorn.cloud.system.center.exception.SystemErrorCode;
import com.funicorn.cloud.system.center.exception.SystemException;
import com.funicorn.cloud.system.center.mapper.DictTypeMapper;
import com.funicorn.cloud.system.center.service.DictTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    @Override
    public void add(DictTypeDTO dictTypeDTO) {
        //1、判断是否是已经删除了的type
        DictType delDictType = baseMapper.selectOne(Wrappers.<DictType>lambdaQuery()
                .eq(DictType::getTenantId, SecurityUtil.getCurrentUser().getTenantId())
                .eq(DictType::getType,dictTypeDTO.getType()).eq(DictType::getIsDelete, SystemConstant.IS_DELETED));
        if (delDictType!=null){
            DictType dictType = JsonUtil.object2Object(dictTypeDTO, DictType.class);
            dictType.setId(delDictType.getId());
            dictType.setIsDelete(SystemConstant.NOT_DELETED);
            baseMapper.updateById(dictType);
        }else {
            int typeCount = baseMapper.selectCount(Wrappers.<DictType>lambdaQuery()
                    .eq(DictType::getType, dictTypeDTO.getType())
                    .eq(DictType::getTenantId, SecurityUtil.getCurrentUser().getTenantId())
                    .eq(DictType::getIsDelete,SystemConstant.NOT_DELETED));
            if (typeCount > 0) {
                throw new SystemException(SystemErrorCode.DICT_TYPE_EXISTS, dictTypeDTO.getType());
            }
            DictType dictType = JsonUtil.object2Object(dictTypeDTO, DictType.class);
            dictType.setTenantId(SecurityUtil.getCurrentUser().getTenantId());
            baseMapper.insert(dictType);
        }
    }
}
