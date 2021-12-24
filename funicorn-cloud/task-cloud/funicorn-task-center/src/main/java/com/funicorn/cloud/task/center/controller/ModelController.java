package com.funicorn.cloud.task.center.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.funicorn.basic.common.base.model.Result;
import com.funicorn.basic.common.datasource.util.ConvertUtil;
import com.funicorn.basic.common.security.util.SecurityUtil;
import com.funicorn.cloud.task.center.constant.TaskConstant;
import com.funicorn.cloud.task.center.dto.ModelPageDTO;
import com.funicorn.cloud.task.center.entity.ActDeModel;
import com.funicorn.cloud.task.center.exception.TaskErrorCode;
import com.funicorn.cloud.task.center.exception.TaskException;
import com.funicorn.cloud.task.center.service.BpmnModelService;
import com.funicorn.cloud.task.center.service.ProcessDefinitionService;
import com.funicorn.cloud.task.center.vo.ModelVO;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.ui.modeler.domain.Model;
import org.flowable.ui.modeler.serviceapi.ModelService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 流程模型管理
 * @author Aimee
 * @since 2021/12/10 14:36
 */
@RestController
@RequestMapping("/model")
public class ModelController {

    @Resource
    private ProcessDefinitionService processDefinitionService;
    @Resource
    private BpmnModelService bpmnModelService;
    @Resource
    private ModelService modelService;

    /**
     * 模板分页查询
     * @param modelPageDTO 入参
     * @return Result
     * */
    @GetMapping("/page")
    public Result<IPage<ModelVO>> page(ModelPageDTO modelPageDTO) {
        LambdaQueryWrapper<ActDeModel> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ActDeModel::getTenantId,SecurityUtil.getCurrentUser().getTenantId());
        queryWrapper.eq(ActDeModel::getCreatedBy,SecurityUtil.getCurrentUser().getUsername());
        if (StringUtils.isNotBlank(modelPageDTO.getModelId())) {
            queryWrapper.eq(ActDeModel::getId,modelPageDTO.getModelId());
        }
        if (StringUtils.isNotBlank(modelPageDTO.getModelKey())) {
            queryWrapper.eq(ActDeModel::getModelKey,modelPageDTO.getModelKey());
        }
        if (StringUtils.isNotBlank(modelPageDTO.getModelName())) {
            queryWrapper.eq(ActDeModel::getName,modelPageDTO.getModelName());
        }
        queryWrapper.orderByDesc(ActDeModel::getCreated);
        IPage<ActDeModel> pages = bpmnModelService.modelPage(new Page<>(modelPageDTO.getCurrent(),modelPageDTO.getSize()),queryWrapper);
        return Result.ok(ConvertUtil.page2Page(pages,ModelVO.class));
    }

    /**
     * 导入流程定义模板
     * */
    @PostMapping("/importBpmnModel")
    public Result<?> importBpmnModel(@RequestParam(value = "file") MultipartFile file) throws Exception {
        if (file.isEmpty()){
            throw new TaskException(TaskErrorCode.UPLOAD_FILE_IS_INVALID);
        }
        if (!Objects.requireNonNull(file.getOriginalFilename()).endsWith(TaskConstant.BPMN_EXTENSION)
                && !Objects.requireNonNull(file.getOriginalFilename()).endsWith(TaskConstant.BPMN20_XML_EXTENSION)) {
            throw new TaskException(TaskErrorCode.NOT_SUPPORT_MODEL_SUFFIX);
        }
        processDefinitionService.importBpmnModel(file);
        return Result.ok();
    }

    /**
     * 查看模型xml
     * @param modelId 模型id
     */
    @SneakyThrows
    @GetMapping(value = "/loadXml")
    public void loadXmlByModelId(@RequestParam String modelId, HttpServletResponse response) {
        Model model = modelService.getModel(modelId);
        if (model==null) {
            throw new TaskException(TaskErrorCode.MODEL_NOT_FOUND);
        }
        BpmnModel bpmnModel = modelService.getBpmnModel(model);
        byte[] b = new BpmnXMLConverter().convertToXML(bpmnModel);
        response.setHeader("Content-type", "text/xml;charset=UTF-8");
        response.getOutputStream().write(b);
    }
}
