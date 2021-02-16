package top.dongxibao.erp.service.system;

import org.springframework.web.multipart.MultipartFile;
import top.dongxibao.erp.enums.ActivitiState;
import top.dongxibao.erp.entity.vo.ProcessDefinitionVO;

import java.io.IOException;
import java.util.List;

/**
 *@ClassName ProcessDefinitionService
 *@Description 流程定义
 *@Author Dongxibao
 *@Date 2021/1/21
 *@Version 1.0
 */
public interface ProcessDefinitionService {

    /**
     * 查询流程定义
     * @param processDefinitionVo
     * @return
     */
    List<ProcessDefinitionVO> listProcessDefinition(ProcessDefinitionVO processDefinitionVo);

    /**
     * 挂起或激活流程定义
     * @param processDefinitionId
     * @param activitiState
     */
    void suspendOrActiveApply(String processDefinitionId, ActivitiState activitiState);

    /**
     * 根据流程部署id删除流程部署（存在运行中的流程实例会级联删除）
     * @param deploymentIds
     * @return
     */
    int deleteProcessDeploymentByIds(List<String> deploymentIds);

    /**
     * 上次流程定义
     * @param category
     * @param file
     * @return
     * @throws IOException
     */
    String deployUploadProcessDefinition(String category, MultipartFile file) throws IOException;
}
