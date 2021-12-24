package com.funicorn.cloud.task.center.service.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.funicorn.basic.common.security.util.SecurityUtil;
import com.funicorn.cloud.task.center.constant.TaskConstant;
import com.funicorn.cloud.task.center.dto.DeploymentDTO;
import com.funicorn.cloud.task.center.exception.TaskErrorCode;
import com.funicorn.cloud.task.center.exception.TaskException;
import com.funicorn.cloud.task.center.service.ProcessDefinitionService;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.Process;
import org.flowable.editor.language.json.converter.BaseBpmnJsonConverter;
import org.flowable.editor.language.json.converter.BpmnJsonConverter;
import org.flowable.editor.language.json.converter.util.CollectionUtils;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.ui.common.util.XmlUtil;
import org.flowable.ui.modeler.domain.AbstractModel;
import org.flowable.ui.modeler.domain.Model;
import org.flowable.ui.modeler.serviceapi.ModelService;
import org.flowable.validation.ProcessValidator;
import org.flowable.validation.ProcessValidatorFactory;
import org.flowable.validation.ValidationError;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.List;

/**
 * @author Aimee
 * @since 2021/12/8 17:16
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ProcessDefinitionServiceImpl implements ProcessDefinitionService {

    @Resource
    private ModelService modelService;
    @Resource
    private RepositoryService repositoryService;

    @Override
    public void importBpmnModel(MultipartFile file) throws Exception{
        XMLInputFactory xmlInputFactory = XmlUtil.createSafeXmlInputFactory();
        InputStreamReader inputStreamReader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8);
        XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(inputStreamReader);
        BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xmlStreamReader);
        bpmnModel.setTargetNamespace(BaseBpmnJsonConverter.NAMESPACE);

        //模板验证
        ProcessValidator validator = new ProcessValidatorFactory().createDefaultProcessValidator();
        List<ValidationError> errors = validator.validate(bpmnModel);
        if (errors!=null && !errors.isEmpty()){
            StringBuffer stringBuffer = new StringBuffer();
            errors.forEach(error -> stringBuffer.append(error.toString()).append("\n"));
            throw new TaskException(TaskErrorCode.PROCESS_MODEL_INVALID,stringBuffer.toString());
        }

        if (CollectionUtils.isEmpty(bpmnModel.getProcesses())) {
            throw new TaskException(TaskErrorCode.PROCESS_DEFINITION_IS_INVALID);
        }
        if (bpmnModel.getLocationMap().size() == 0) {
            throw new TaskException(TaskErrorCode.PROCESS_DEFINITION_IS_INVALID);
        }

        ObjectNode modelNode = new BpmnJsonConverter().convertToJson(bpmnModel);
        Process process = bpmnModel.getMainProcess();
        Model newModel = new Model();
        newModel.setName(process.getName());
        newModel.setTenantId(SecurityUtil.getCurrentUser().getTenantId());
        newModel.setKey(process.getId());
        newModel.setModelType(AbstractModel.MODEL_TYPE_BPMN);
        newModel.setCreated(Calendar.getInstance().getTime());
        newModel.setDescription(process.getDocumentation());
        newModel.setModelEditorJson(modelNode.toString());
        modelService.createModel(newModel,SecurityUtil.getCurrentUser().getUsername());
    }

    @Override
    public Deployment deploy(DeploymentDTO deploymentDTO) {
        Model model = modelService.getModel(deploymentDTO.getModelId());
        if (model==null) {
            throw new TaskException(TaskErrorCode.MODEL_NOT_FOUND);
        }
        BpmnModel bpmnModel = modelService.getBpmnModel(model);
        //模板验证
        ProcessValidator validator = new ProcessValidatorFactory().createDefaultProcessValidator();
        List<ValidationError> errors = validator.validate(bpmnModel);
        if (errors!=null && !errors.isEmpty()){
            StringBuffer stringBuffer = new StringBuffer();
            errors.forEach(error -> stringBuffer.append(error.toString()).append("\n"));
            throw new TaskException(TaskErrorCode.PROCESS_MODEL_INVALID,stringBuffer.toString());
        }
        String name = model.getName();
        if (StringUtils.isNotBlank(deploymentDTO.getDeploymentName())) {
            name = deploymentDTO.getDeploymentName();
        }
        String category = StringUtils.isNotBlank(deploymentDTO.getDeploymentCategory()) ? deploymentDTO.getDeploymentCategory() : "default";
        return repositoryService.createDeployment()
                .name(name)
                .key(model.getKey())
                .category(category)
                .tenantId(model.getTenantId())
                .addBpmnModel(model.getKey() + TaskConstant.BPMN_EXTENSION, bpmnModel)
                .deploy();
    }

    @Override
    public Deployment publish(String modelId) {
        return null;
    }

    @Override
    public void suspendOrActiveProcessDefinitionById(String processDefinitionId, int suspensionState) {

    }
}
