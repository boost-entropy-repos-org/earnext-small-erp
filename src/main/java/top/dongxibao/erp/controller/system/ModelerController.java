package top.dongxibao.erp.controller.system;

import cn.hutool.core.collection.CollUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import top.dongxibao.erp.annotation.Log;
import top.dongxibao.erp.common.BaseController;
import top.dongxibao.erp.common.Page;
import top.dongxibao.erp.common.Result;
import top.dongxibao.erp.enums.BusinessType;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import static org.activiti.editor.constants.ModelDataJsonConstants.*;

/**
 *@ClassName ModelerController
 *@Description 模型
 *@Author Dongxibao
 *@Date 2021/1/19
 *@Version 1.0
 */
@Slf4j
@Api(tags = "流程-模型")
@Controller
public class ModelerController extends BaseController {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ObjectMapper objectMapper;

    @ApiOperation("static/modeler/stencilset.json")
    @GetMapping(value = "/modeler/editor/stencilset")
    @ResponseBody
    public JsonNode getStencilset() {
        InputStream stencilsetStream = this.getClass().getClassLoader().getResourceAsStream("static/modeler/stencilset.json");
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(IOUtils.toString(stencilsetStream, "utf-8"));
            return jsonNode;
        } catch (Exception e) {
            log.error("Error while loading stencil set：{}", e);
            throw new ActivitiException("Error while loading stencil set", e);
        }
    }

    @ApiOperation("列表")
    @GetMapping("/modeler/list")
    @ResponseBody
    public Result list(@RequestParam(value = "key", required = false) String key,
                       @RequestParam(value = "name", required = false) String name,
                       @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize) {
        ModelQuery modelQuery = repositoryService.createModelQuery();
        modelQuery.orderByLastUpdateTime().desc();
        // 条件过滤
        if (StringUtils.isNotBlank(key)) {
            modelQuery.modelKey(key);
        }
        if (StringUtils.isNotBlank(name)) {
            modelQuery.modelNameLike("%" + name + "%");
        }

        List<Model> resultList = modelQuery.list();
        return Result.ok(new Page<>(resultList, pageNum, pageSize));
    }

    /**
     * 导出model的xml文件
     */
    @Log(title = "流程模型", businessType = BusinessType.EXPORT)
    @ApiOperation("导出model的xml文件")
    @GetMapping(value = "/modeler/export/{modelId}")
    public void export(@PathVariable("modelId") String modelId, HttpServletResponse response) {
        try {
            Model modelData = repositoryService.getModel(modelId);
            BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
            JsonNode editorNode = new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
            BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);

            // 流程非空判断
            if (!CollectionUtils.isEmpty(bpmnModel.getProcesses())) {
                BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
                byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);

                ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
                String filename = bpmnModel.getMainProcess().getId() + ".bpmn";
                response.setHeader("Content-Disposition", "attachment; filename=" + filename);
//                response.setContentType("application/octet-stream; charset=utf-8");
                IOUtils.copy(in, response.getOutputStream());
                response.flushBuffer();
            }
        } catch (Exception e) {
            log.error("导出model的xml文件失败：modelId={} | {}", modelId, e);
        }
    }

    @Log(title = "流程模型", businessType = BusinessType.INSERT)
    @ApiOperation("创建模型")
    @PostMapping("/modeler/create")
    @ResponseBody
    public Result create(@RequestParam("name") String name,
                         @RequestParam("key") String key,
                         @RequestParam(value = "category", required = false) String category,
                         @RequestParam(value = "description", required = false) String description) {
        try {
            ObjectNode editorNode = objectMapper.createObjectNode();
            editorNode.put("id", "canvas");
            editorNode.put("resourceId", "canvas");
            ObjectNode stencilSetNode = objectMapper.createObjectNode();
            stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
            editorNode.put("stencilset", stencilSetNode);

            ObjectNode modelObjectNode = objectMapper.createObjectNode();
            modelObjectNode.put(MODEL_NAME, name);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
            description = StringUtils.defaultString(description);
            modelObjectNode.put(MODEL_DESCRIPTION, description);

            Model newModel = repositoryService.newModel();
            newModel.setMetaInfo(modelObjectNode.toString());
            newModel.setName(name);
            newModel.setKey(StringUtils.defaultString(key));
            newModel.setCategory(category);
            repositoryService.saveModel(newModel);
            repositoryService.addModelEditorSource(newModel.getId(), editorNode.toString().getBytes("utf-8"));

            return Result.ok("创建模型成功", newModel.getId());
        } catch (Exception e) {
            log.error("创建模型失败：{}", e);
            return Result.fail("创建模型失败" + e);
        }
    }

    @ApiOperation("getEditorJson")
    @GetMapping(value = "/modeler/model/{modelId}/json")
    @ResponseBody
    public ObjectNode getEditorJson(@PathVariable String modelId) {
        ObjectNode modelNode = null;

        Model model = repositoryService.getModel(modelId);

        if (model != null) {
            try {
                if (StringUtils.isNotEmpty(model.getMetaInfo())) {
                    modelNode = (ObjectNode) objectMapper.readTree(model.getMetaInfo());
                } else {
                    modelNode = objectMapper.createObjectNode();
                    modelNode.put(MODEL_NAME, model.getName());
                }
                modelNode.put(MODEL_ID, model.getId());
                ObjectNode editorJsonNode = (ObjectNode) objectMapper.readTree(
                        new String(repositoryService.getModelEditorSource(model.getId()), "utf-8"));
                modelNode.put("model", editorJsonNode);

            } catch (Exception e) {
                log.error("Error creating model JSON：{}", e);
                throw new ActivitiException("Error creating model JSON", e);
            }
        }
        return modelNode;
    }

    @Log(title = "流程模型", businessType = BusinessType.UPDATE)
    @ApiOperation("修改模型")
    @RequestMapping(value = "/modeler/model/{modelId}/save", method = RequestMethod.POST)
    @ResponseBody
    public Result saveModel(@PathVariable String modelId,
                            @RequestParam MultiValueMap<String, String> values) {
        try {
            Model model = repositoryService.getModel(modelId);
            ObjectNode modelJson = (ObjectNode) objectMapper.readTree(model.getMetaInfo());

            modelJson.put(MODEL_NAME, values.getFirst("name"));
            modelJson.put(MODEL_DESCRIPTION, values.getFirst("description"));
            model.setMetaInfo(modelJson.toString());
            model.setName(values.getFirst("name"));

            repositoryService.saveModel(model);

            repositoryService.addModelEditorSource(model.getId(), values.getFirst("json_xml").getBytes("utf-8"));

            InputStream svgStream = new ByteArrayInputStream(values.getFirst("svg_xml").getBytes("utf-8"));
            TranscoderInput input = new TranscoderInput(svgStream);

            PNGTranscoder transcoder = new PNGTranscoder();
            // Setup output
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            TranscoderOutput output = new TranscoderOutput(outStream);

            // Do the transformation
            transcoder.transcode(input, output);
            final byte[] result = outStream.toByteArray();
            repositoryService.addModelEditorSourceExtra(model.getId(), result);
            outStream.close();
            return Result.ok("模型修改成功");
        } catch (Exception e) {
            log.error("Error saving model：{}", e);
            throw new ActivitiException("Error saving model", e);
        }
    }

    @Log(title = "流程模型", businessType = BusinessType.UPDATE)
    @ApiOperation("根据Model部署流程")
    @PostMapping(value = "/modeler/deploy/{modelId}")
    @ResponseBody
    public Result deploy(@PathVariable("modelId") String modelId) {
        try {
            Model modelData = repositoryService.getModel(modelId);
            String category = modelData.getCategory();
            ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
            byte[] bpmnBytes = null;

            BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
            bpmnBytes = new BpmnXMLConverter().convertToXML(model);

            String processName = modelData.getName() + ".bpmn20.xml";
            Deployment deployment = repositoryService.createDeployment().name(modelData.getName()).addString(processName, new String(bpmnBytes, "UTF-8")).deploy();
            List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).list();
            if (CollUtil.isNotEmpty(processDefinitions)) {
                processDefinitions.forEach(processDefinition -> {
                    repositoryService.setProcessDefinitionCategory(processDefinition.getId(), category);
                });
            }
            log.info("部署成功，部署ID=" + deployment.getId());
            return Result.ok("部署成功，部署ID=" + deployment.getId());
        } catch (Exception e) {
            log.error("根据模型部署流程失败：modelId={}; 异常[{}]", modelId, e);
            return Result.fail("根据模型部署流程失败：modelId=" + modelId);
        }
    }

    @Log(title = "流程模型", businessType = BusinessType.DELETE)
    @ApiOperation("删除流程模型")
    @DeleteMapping("/modeler/delete/{modelId}")
    @ResponseBody
    public Result remove(@PathVariable("modelId") String modelId) {
        try {
            repositoryService.deleteModel(modelId);
            return Result.ok();
        } catch (Exception e) {
            log.error("流程模型-删除error：{}|{}", e, e.getMessage());
            return Result.fail(e.getMessage());
        }
    }
}
