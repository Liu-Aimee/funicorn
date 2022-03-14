package com.funicorn.cloud.system.center.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.funicorn.basic.common.base.model.Result;
import com.funicorn.basic.common.base.util.JsonUtil;
import com.funicorn.basic.common.base.valid.Insert;
import com.funicorn.basic.common.base.valid.Query;
import com.funicorn.basic.common.base.valid.Update;
import com.funicorn.basic.common.security.util.SecurityUtil;
import com.funicorn.cloud.system.api.dto.DictItemQueryDTO;
import com.funicorn.cloud.system.api.vo.DictItemVO;
import com.funicorn.cloud.system.center.constant.SystemConstant;
import com.funicorn.cloud.system.center.dto.DictItemDTO;
import com.funicorn.cloud.system.center.dto.MultiDictItemDTO;
import com.funicorn.cloud.system.center.entity.DictItem;
import com.funicorn.cloud.system.center.exception.SystemErrorCode;
import com.funicorn.cloud.system.center.exception.SystemException;
import com.funicorn.cloud.system.center.service.DictItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 字典项接口
 *
 * @author Aimee
 * @since 2021-10-15
 */
@RestController
@RequestMapping("/DictItem")
public class DictItemController {

    @Resource
    private DictItemService dictItemService;

    /**
     * 根据条件查询所有
     * @param dictItemQueryDTO 分页条件
     * @return Result
     * */
    @GetMapping("/list")
    public Result<List<DictItemVO>> list(@Validated(Query.class) DictItemQueryDTO dictItemQueryDTO){
        LambdaQueryWrapper<DictItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DictItem::getIsDelete, SystemConstant.NOT_DELETED);
        if (StringUtils.isNotBlank(dictItemQueryDTO.getDictType())){
            queryWrapper.eq(DictItem::getDictType,dictItemQueryDTO.getDictType());
        }
        if (StringUtils.isNotBlank(dictItemQueryDTO.getDictValue())){
            queryWrapper.like(DictItem::getDictValue,dictItemQueryDTO.getDictValue());
        }
        if (StringUtils.isNotBlank(dictItemQueryDTO.getDictLabel())){
            queryWrapper.like(DictItem::getDictLabel,dictItemQueryDTO.getDictLabel());
        }
        List<DictItem> items = dictItemService.list(queryWrapper);
        if (items==null || items.isEmpty()){
            return Result.ok(new ArrayList<>());
        }
        List<DictItemVO> itemVOList = new ArrayList<>();
        items.forEach(sysDictItem -> itemVOList.add(JsonUtil.object2Object(sysDictItem, DictItemVO.class)));
        return Result.ok(itemVOList);
    }

    /**
     * 批量保存字典项
     * @param multiDictItemDTO 入参
     * @return Result
     * */
    @PostMapping("/multiSave")
    public Result<?> multiSave(@RequestBody MultiDictItemDTO multiDictItemDTO){
        dictItemService.multiSave(multiDictItemDTO);
        return Result.ok("保存成功");
    }

    /**
     * 单个新增字典项
     * @param dictItemDTO 入参
     * @return Result
     * */
    @PostMapping("/save")
    public Result<?> save(@RequestBody @Validated({Insert.class}) DictItemDTO dictItemDTO){
        int count = dictItemService.count(Wrappers.<DictItem>lambdaQuery()
                .eq(DictItem::getDictType,dictItemDTO.getDictType())
                .eq(DictItem::getIsDelete,SystemConstant.NOT_DELETED)
                .eq(DictItem::getTenantId, SecurityUtil.getCurrentUser().getTenantId())
                .and(wrapper-> wrapper.eq(DictItem::getDictValue,dictItemDTO.getDictValue()).or().eq(DictItem::getDictLabel,dictItemDTO.getDictLabel())));
        if (count>0) {
            throw new SystemException(SystemErrorCode.DICT_LABEL_OR_VALUE_IS_EXISTS);
        }
        DictItem dictItem = JsonUtil.object2Object(dictItemDTO,DictItem.class);
        dictItem.setTenantId(SecurityUtil.getCurrentUser().getTenantId());
        dictItemService.save(dictItem);
        return Result.ok("新增成功");
    }

    /**
     * 修改字典
     * @param dictItemDTO 入参
     * @return Result
     * */
    @PostMapping("/update")
    public Result<?> update(@RequestBody @Validated({Update.class}) DictItemDTO dictItemDTO){
        int count = dictItemService.count(Wrappers.<DictItem>lambdaQuery()
                .eq(DictItem::getDictType,dictItemDTO.getDictType())
                .eq(DictItem::getIsDelete,SystemConstant.NOT_DELETED)
                .eq(DictItem::getTenantId, SecurityUtil.getCurrentUser().getTenantId())
                .and(wrapper-> wrapper.eq(DictItem::getDictValue,dictItemDTO.getDictValue()).or().eq(DictItem::getDictLabel,dictItemDTO.getDictLabel()))
                .ne(DictItem::getId,dictItemDTO.getId()));

        if (count>0) {
            throw new SystemException(SystemErrorCode.DICT_LABEL_OR_VALUE_IS_EXISTS);
        }
        DictItem dictItem = JsonUtil.object2Object(dictItemDTO,DictItem.class);
        dictItemService.updateById(dictItem);
        return Result.ok("修改成功");
    }

    /**
     * 删除字典
     * @param id 主键id
     * @return Result
     * */
    @DeleteMapping("/{id}")
    public Result<?> remove(@PathVariable String id){
        DictItem dictItem = dictItemService.getById(id);
        if (dictItem ==null){
            throw new SystemException(SystemErrorCode.DICT_ITEM_NOT_FOUND,id);
        }
        dictItem.setIsDelete(SystemConstant.IS_DELETED);
        dictItemService.updateById(dictItem);
        return Result.ok("删除成功");
    }
}

