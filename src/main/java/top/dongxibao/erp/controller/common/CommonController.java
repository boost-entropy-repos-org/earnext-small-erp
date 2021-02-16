package top.dongxibao.erp.controller.common;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.dongxibao.erp.common.Result;
import top.dongxibao.erp.entity.common.AttachAssociation;
import top.dongxibao.erp.properties.SmallConfig;
import top.dongxibao.erp.service.common.AttachAssociationService;
import top.dongxibao.erp.util.FileUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CommonController
 * @description 文件上传下载
 * @author Dongxibao
 * @date 2020/7/5
 * @Version 1.0
 */
@RestController
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private AttachAssociationService attachAssociationService;

    /**
     * 通用上传请求
     * @param id
     * @param file
     * @return
     * @throws Exception
     */
    @ApiOperation("通用上传")
    @PostMapping("/upload")
    public Result uploadFile(@RequestParam(value = "id") Long id,
                             @RequestParam(value = "attachType", required = false, defaultValue = "0") Integer attachType,
                             @RequestParam(value = "file") MultipartFile file) throws Exception {
        String realFileName = file.getOriginalFilename();
        // 上传文件路径
        String filePath = SmallConfig.getUploadPath();
        // 上传并返回新文件名称
        String pathName = FileUtils.upload(id, attachType, filePath, file);
        Map<String, String> map = new HashMap<>(3);
        map.put("fileName", realFileName);
        map.put("url", pathName);
        return Result.ok(map);
    }

    @ApiOperation("通用批量上传")
    @PostMapping("/upload_files")
    public Result uploadFiles(@RequestParam("id") Long id,
                              @RequestParam(value = "attachType", required = false, defaultValue = "0") Integer attachType,
                              @RequestParam("files") MultipartFile... files) throws Exception {
        List<Map<String, String>> resultList = new ArrayList<>();
        // 上传文件路径
        String filePath = SmallConfig.getUploadPath();
        for (MultipartFile file : files) {
            String realFileName = file.getOriginalFilename();
            // 上传并返回新文件路径名称
            String pathName = FileUtils.upload(id, attachType, filePath, file);
            Map<String, String> map = new HashMap<>(3);
            map.put("url", pathName);
            map.put("fileName", realFileName);
            resultList.add(map);
        }
        return Result.ok(resultList);
    }

    /**
     * 通用下载
     * @param id 附件关联表id
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiOperation("通用下载")
    @GetMapping("/download")
    public void downloadFile(@RequestParam("id") Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        AttachAssociation attachAssociation = attachAssociationService.getById(id);
        String downloadPath = attachAssociation.getAttachPath() + attachAssociation.getNewAttachName();
        String realAttachName = attachAssociation.getRealAttachName();
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + FileUtils.getFileNameByEncode(request, realAttachName));
        FileUtils.writeBytes(downloadPath, response.getOutputStream());
    }

    @ApiOperation("通用删除")
    @DeleteMapping("/file_delete/{id}")
    public Result deleteFile(@PathVariable("id") Long id) {
        return Result.of(attachAssociationService.removeById(id), null);
    }
}
