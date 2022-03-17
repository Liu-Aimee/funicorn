package com.funicorn.cloud.system.center.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.funicorn.basic.common.base.model.Result;
import com.funicorn.basic.common.base.util.JsonUtil;
import com.funicorn.basic.common.base.valid.Insert;
import com.funicorn.basic.common.base.valid.Update;
import com.funicorn.basic.common.security.util.SecurityUtil;
import com.funicorn.cloud.system.center.constant.SystemConstant;
import com.funicorn.cloud.system.center.dto.DictTypeDTO;
import com.funicorn.cloud.system.center.dto.DictTypeQueryPageDTO;
import com.funicorn.cloud.system.center.entity.DictItem;
import com.funicorn.cloud.system.center.entity.DictType;
import com.funicorn.cloud.system.center.exception.SystemErrorCode;
import com.funicorn.cloud.system.center.exception.SystemException;
import com.funicorn.cloud.system.center.service.DictItemService;
import com.funicorn.cloud.system.center.service.DictTypeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 字典管理接口
 *
 * @author Aimee
 * @since 2021-10-15
 */
@RestController
@RequestMapping("/DictType")
public class DictTypeController {

    @Resource
    private DictTypeService dictTypeService;
    @Resource
    private DictItemService dictItemService;

    /**
     * 查询所有字典
     * @param dictTypeQueryPageDTO 入参
     * @return Result
     * */
    @GetMapping("/page")
    public Result<IPage<DictType>> page(DictTypeQueryPageDTO dictTypeQueryPageDTO){
        LambdaQueryWrapper<DictType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DictType::getIsDelete, SystemConstant.NOT_DELETED);
        queryWrapper.eq(DictType::getTenantId, SecurityUtil.getCurrentUser().getTenantId());
        if (StringUtils.isNotBlank(dictTypeQueryPageDTO.getType())){
            queryWrapper.like(DictType::getType, dictTypeQueryPageDTO.getType());
        }
        if (StringUtils.isNotBlank(dictTypeQueryPageDTO.getName())){
            queryWrapper.like(DictType::getName, dictTypeQueryPageDTO.getName());
        }
        queryWrapper.orderByDesc(DictType::getCreatedTime);
        return Result.ok(dictTypeService.page(new Page<>(dictTypeQueryPageDTO.getCurrent(),dictTypeQueryPageDTO.getSize()),queryWrapper));
    }

    /**
     * 新增字典
     * @param dictTypeDTO 入参
     * @return Result
     * */
    @PostMapping("/add")
    public Result<?> add(@RequestBody @Validated(Insert.class) DictTypeDTO dictTypeDTO) {
        int count = dictTypeService.count(Wrappers.<DictType>lambdaQuery()
                .eq(DictType::getType,dictTypeDTO.getType())
                .eq(DictType::getTenantId,SecurityUtil.getCurrentUser().getTenantId())
                .eq(DictType::getIsDelete,SystemConstant.NOT_DELETED));
        if (count>0) {
            throw new SystemException(SystemErrorCode.DICT_TYPE_EXISTS,dictTypeDTO.getType());
        }
        dictTypeService.add(dictTypeDTO);
        return Result.ok("新增成功");
    }

    /**
     * 修改字典
     * @param dictTypeDTO 入参
     * @return Result
     * */
    @PostMapping("/update")
    public Result<?> update(@RequestBody @Validated(Update.class) DictTypeDTO dictTypeDTO) {
        int count = dictTypeService.count(Wrappers.<DictType>lambdaQuery()
                .eq(DictType::getType,dictTypeDTO.getType())
                .eq(DictType::getTenantId,SecurityUtil.getCurrentUser().getTenantId())
                .eq(DictType::getIsDelete,SystemConstant.NOT_DELETED)
                .ne(DictType::getId,dictTypeDTO.getId()));
        if (count>0) {
            throw new SystemException(SystemErrorCode.DICT_TYPE_EXISTS,dictTypeDTO.getType());
        }
        dictTypeService.updateById(JsonUtil.object2Object(dictTypeDTO,DictType.class));
        return Result.ok("修改成功");
    }

    /**
     * 删除字典
     * @param id 主键id
     * @return Result
     * */
    @DeleteMapping("/{id}")
    public Result<?> remove(@PathVariable String id) {
        DictType dictType = dictTypeService.getById(id);
        if (dictType ==null){
            throw new SystemException(SystemErrorCode.DICT_TYPE_NOT_FOUND,id);
        }
        int count = dictItemService.count(Wrappers.<DictItem>lambdaQuery()
                .eq(DictItem::getDictType,dictType.getType())
                .eq(DictItem::getTenantId,SecurityUtil.getCurrentUser().getTenantId())
                .eq(DictItem::getIsDelete,SystemConstant.NOT_DELETED));
        if (count>0) {
            throw new SystemException(SystemErrorCode.DICT_TYPE_BIND_ITEMS);
        }
        dictType.setIsDelete(SystemConstant.IS_DELETED);
        dictTypeService.updateById(dictType);
        return Result.ok("新增成功");
    }
}

