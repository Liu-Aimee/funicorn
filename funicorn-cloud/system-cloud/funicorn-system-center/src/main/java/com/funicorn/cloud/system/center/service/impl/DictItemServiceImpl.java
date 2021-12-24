package com.funicorn.cloud.system.center.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.funicorn.basic.common.base.util.JsonUtil;
import com.funicorn.basic.common.security.util.SecurityUtil;
import com.funicorn.cloud.system.center.constant.SystemConstant;
import com.funicorn.cloud.system.center.dto.DictItemPageDTO;
import com.funicorn.cloud.system.center.dto.MultiDictItemDTO;
import com.funicorn.cloud.system.center.entity.DictItem;
import com.funicorn.cloud.system.center.entity.DictType;
import com.funicorn.cloud.system.center.exception.SystemErrorCode;
import com.funicorn.cloud.system.center.exception.SystemException;
import com.funicorn.cloud.system.center.mapper.DictItemMapper;
import com.funicorn.cloud.system.center.mapper.DictTypeMapper;
import com.funicorn.cloud.system.center.service.DictItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 数据字典 服务实现类
 * </p>
 *
 * @author Aimee
 * @since 2021-10-28
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DictItemServiceImpl extends ServiceImpl<DictItemMapper, DictItem> implements DictItemService {

    @Resource
    private DictTypeMapper dictTypeMapper;

    @Override
    public IPage<DictItem> getPage(DictItemPageDTO dictItemPageDTO) {
        return dictTypeMapper.queryPage(new Page<>(dictItemPageDTO.getCurrent(),dictItemPageDTO.getSize()),dictItemPageDTO);
    }

    @Override
    public void multiSave(MultiDictItemDTO multiDictItemDTO) {
        DictType dictType = dictTypeMapper.selectOne(Wrappers.<DictType>lambdaQuery()
                .eq(DictType::getType, multiDictItemDTO.getDictType())
                .eq(DictType::getTenantId, SecurityUtil.getCurrentUser().getTenantId()));
        if (dictType ==null || SystemConstant.IS_DELETED.equals(dictType.getIsDelete())){
            throw new SystemException(SystemErrorCode.DICT_TYPE_NOT_FOUND, multiDictItemDTO.getDictType());
        }

        if (multiDictItemDTO.getItems()!=null && !multiDictItemDTO.getItems().isEmpty()){
            baseMapper.delete(Wrappers.<DictItem>lambdaQuery().eq(DictItem::getDictType,multiDictItemDTO.getDictType())
                    .eq(DictItem::getTenantId,SecurityUtil.getCurrentUser().getTenantId()));
            List<DictItem> dictItems = new ArrayList<>();
            for (MultiDictItemDTO.Item item: multiDictItemDTO.getItems()) {
                DictItem dictItem = JsonUtil.object2Object(item,DictItem.class);
                dictItem.setDictType(multiDictItemDTO.getDictType());
                dictItem.setTenantId(SecurityUtil.getCurrentUser().getTenantId());
                dictItems.add(dictItem);
            }
            saveBatch(dictItems);
        }
    }
}
