package top.dongxibao.erp.service.system.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import top.dongxibao.erp.enums.ActivitiState;
import top.dongxibao.erp.enums.FileExtension;
import top.dongxibao.erp.exception.ServiceException;
import top.dongxibao.erp.entity.vo.ProcessDefinitionVO;
import top.dongxibao.erp.service.system.ProcessDefinitionService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipInputStream;

/**
 *@ClassName ProcessDefinitionServiceImpl
 *@Description 流程定义
 *@Author Dongxibao
 *@Date 2021/1/21
 *@Version 1.0
 */
@Slf4j
@Service
public class ProcessDefinitionServiceImpl implements ProcessDefinitionService {
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;

    @Override
    public List<ProcessDefinitionVO> listProcessDefinition(ProcessDefinitionVO processDefinitionVo) {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery()
                .orderByProcessDefinitionId().orderByProcessDefinitionVersion().desc();
        if (StrUtil.isNotBlank(processDefinitionVo.getKey())) {
            processDefinitionQuery.processDefinitionKeyLike(processDefinitionVo.getKey());
        }
        if (StrUtil.isNotBlank(processDefinitionVo.getName())) {
            processDefinitionQuery.processDefinitionNameLike(processDefinitionVo.getName());
        }
        if (StrUtil.isNotBlank(processDefinitionVo.getCategory())) {
            processDefinitionQuery.processDefinitionCategoryLike(processDefinitionVo.getCategory());
        }
        List<ProcessDefinition> list = processDefinitionQuery.latestVersion().list();
        if (CollUtil.isEmpty(list)) {
            return new ArrayList<>();
        }
        List<ProcessDefinitionVO> collect = list.stream().map(processDefinition -> {
            ProcessDefinitionVO definitionVO = new ProcessDefinitionVO();
            ProcessDefinitionEntityImpl entityImpl = (ProcessDefinitionEntityImpl) processDefinition;
            definitionVO.setProcessDefinitionId(processDefinition.getId());
            definitionVO.setKey(processDefinition.getKey());
            definitionVO.setName(processDefinition.getName());
            definitionVO.setCategory(processDefinition.getCategory());
            definitionVO.setVersion(processDefinition.getVersion());
            definitionVO.setDescription(processDefinition.getDescription());
            definitionVO.setDeploymentId(processDefinition.getDeploymentId());
            Deployment deployment = repositoryService.createDeploymentQuery()
                    .deploymentId(processDefinition.getDeploymentId())
                    .singleResult();
            definitionVO.setDeploymentTime(deployment.getDeploymentTime());
            definitionVO.setDiagramResourceName(processDefinition.getDiagramResourceName());
            definitionVO.setResourceName(processDefinition.getResourceName());
            definitionVO.setSuspendState(entityImpl.getSuspensionState() + "");
            if (entityImpl.getSuspensionState() == 1) {
                definitionVO.setSuspendStateName("已激活");
            } else {
                definitionVO.setSuspendStateName("已挂起");
            }
            return definitionVO;
        }).collect(Collectors.toList());
        return collect;
    }


    @Override
    public void suspendOrActiveApply(String processDefinitionId, ActivitiState activitiState) {
        if (ActivitiState.SUSPEND.equals(activitiState)) {
            // 当流程定义被挂起时，已经发起的该流程定义的流程实例不受影响（如果选择级联挂起则流程实例也会被挂起）。
            // 当流程定义被挂起时，无法发起新的该流程定义的流程实例。
            // 直观变化：act_re_procdef 的 SUSPENSION_STATE_ 为 2
            repositoryService.suspendProcessDefinitionById(processDefinitionId);
        } else if (ActivitiState.ACTIVE.equals(activitiState)) {
            repositoryService.activateProcessDefinitionById(processDefinitionId);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteProcessDeploymentByIds(List<String> deploymentIds) {
        int counter = 0;
        if (CollUtil.isNotEmpty(deploymentIds)) {
            for (String deploymentId : deploymentIds) {
                List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery()
                        .deploymentId(deploymentId).list();
                if (CollUtil.isNotEmpty(processInstances)) {
                    String join = CollUtil.join(processInstances, ",");
                    throw new ServiceException("删除流程定义失败,存在运行中的流程实例processInstances：" + join);
                }
                // true：存在运行中的流程实例会级联删除，比如 act_ru_execution 数据
                repositoryService.deleteDeployment(deploymentId, true);
                counter++;
            }
        }
        return counter;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String deployUploadProcessDefinition(String category, MultipartFile file) throws IOException {
        String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1);
        if (!FileExtension.BPMN.ext.equalsIgnoreCase(extension)
                && !FileExtension.ZIP.ext.equalsIgnoreCase(extension)
                && !FileExtension.BAR.ext.equalsIgnoreCase(extension)) {
            log.error("流程定义文件仅支持 bpmn, zip 和 bar 格式！");
            throw new ServiceException("流程定义文件仅支持 bpmn, zip 和 bar 格式！");
        }
        String message = "";
        String fileName = file.getOriginalFilename();
        InputStream fileInputStream = file.getInputStream();
        Deployment deployment = null;
        if (FileExtension.ZIP.ext.equals(extension) || FileExtension.BAR.ext.equals(extension)) {
            ZipInputStream zip = new ZipInputStream(fileInputStream);
            deployment = repositoryService.createDeployment().addZipInputStream(zip).deploy();
        } else if (FileExtension.PNG.ext.equals(extension)) {
            deployment = repositoryService.createDeployment().addInputStream(fileName, fileInputStream).deploy();
        } else if (fileName.indexOf(FileExtension.BPMN20XML.ext) != -1) {
            deployment = repositoryService.createDeployment().addInputStream(fileName, fileInputStream).deploy();
        } else if (FileExtension.BPMN.ext.equals(extension)) {
            // bpmn扩展名特殊处理，转换为bpmn20.xml
            String baseName = FilenameUtils.getBaseName(fileName);
            deployment = repositoryService.createDeployment().addInputStream(baseName + "." + FileExtension.BPMN20XML.ext, fileInputStream).deploy();
        }
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).list();
        // 设置流程分类
        String ids = "";
        if (CollUtil.isNotEmpty(list)) {
            for (ProcessDefinition processDefinition : list) {
                repositoryService.setProcessDefinitionCategory(processDefinition.getId(), category);
                ids += processDefinition.getId() + ",";
            }
        }
        if (ids.length() > 0) {
            ids = ids.substring(0, ids.length() - 1);
            message = "部署成功，流程定义ID为：" + ids;
        }
        return message;
    }
}
