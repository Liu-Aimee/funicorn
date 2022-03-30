package com.funicorn.cloud.system.center.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.funicorn.basic.common.base.util.JsonUtil;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
                .eq(DictType::getTenantId, multiDictItemDTO.getTenantId()));
        if (dictType ==null || SystemConstant.IS_DELETED.equals(dictType.getIsDelete())){
            throw new SystemException(SystemErrorCode.DICT_TYPE_NOT_FOUND, multiDictItemDTO.getDictType());
        }

        if (multiDictItemDTO.getItems()!=null && !multiDictItemDTO.getItems().isEmpty()){
            // 1、校验是否存在空值
            List<MultiDictItemDTO.Item> invalidItems = multiDictItemDTO.getItems().stream()
                    .filter(item -> StringUtils.isBlank(item.getDictLabel()) || StringUtils.isBlank(item.getDictValue()))
                    .collect(Collectors.toList());
            if (invalidItems.size()>0) {
                throw new SystemException(SystemErrorCode.DICT_TIMES_IS_INVALID);
            }

            // 2、校验是否存在重复的字典项值
            Map<String, List<MultiDictItemDTO.Item>> dictValueMap = multiDictItemDTO.getItems().stream().collect(Collectors.groupingBy(MultiDictItemDTO.Item::getDictValue));
            dictValueMap.forEach((k,v)->{
                if (v.size()>1){
                    throw new SystemException(SystemErrorCode.DICT_TIME_VALUE_REPEAT,k);
                }
            });

            // 3、删除新增
            baseMapper.delete(Wrappers.<DictItem>lambdaQuery().eq(DictItem::getDictType,multiDictItemDTO.getDictType()).eq(DictItem::getTenantId,multiDictItemDTO.getTenantId()));
            List<DictItem> dictItems = new ArrayList<>();
            for (MultiDictItemDTO.Item item: multiDictItemDTO.getItems()) {
                DictItem dictItem = JsonUtil.object2Object(item,DictItem.class);
                dictItem.setDictType(multiDictItemDTO.getDictType());
                dictItem.setTenantId(multiDictItemDTO.getTenantId());
                dictItems.add(dictItem);
            }
            saveBatch(dictItems);
        }
    }
}
