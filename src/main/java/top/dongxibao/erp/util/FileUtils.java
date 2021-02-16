package top.dongxibao.erp.util;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.web.multipart.MultipartFile;
import top.dongxibao.erp.constant.FileUploadConstants;
import top.dongxibao.erp.entity.common.AttachAssociation;
import top.dongxibao.erp.enums.MimeTypeUtils;
import top.dongxibao.erp.exception.ServiceException;
import top.dongxibao.erp.mapper.common.AttachAssociationMapper;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;

/**
 * @ClassName FileUtils
 * @description 文件上传工具类
 * @author Dongxibao
 * @date 2020/7/5
 * @Version 1.0
 */
public class FileUtils {

    /**
     * 根据文件路径上传
     *
     * @param id 业务id
     * @param path 路径
     * @param file 上传的文件
     * @return 文件路径名称
     * @throws IOException
     */
    public static final String upload(Long id, Integer attachType, String path, MultipartFile file) throws IOException {
        try {
            return upload(id, attachType, path, file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }


    /**
     * 文件上传
     *
     * @param path 相对应用的基目录
     * @param file 上传的文件
     * @param allowedExtension 上传文件类型
     * @return 返回上传成功的路径和文件名
     * @throws IOException 比如读写文件出错时
     */
    public static final String upload(Long id, Integer attachType, String path, MultipartFile file,
                                      String[] allowedExtension) throws IOException {
        String realFileName = file.getOriginalFilename();
        // 文件大小、文件名长度、文件类型 校验
        assertAllowed(file, allowedExtension);
        // 后缀
        String extension = getExtension(file);
        // 生成文件名，防止重名
        String newFileName = IdUtil.fastSimpleUUID() + "." + extension;
        // 日期路径 即 年/月/日
        Date now = new Date();
        String datePath = DateFormatUtils.format(now, "yyyy" + File.separator + "MM" + File.separator + "dd");

        path = path + File.separator + datePath + File.separator;
        File desc = getAbsoluteFile(path, newFileName);
        file.transferTo(desc);
        // 记录到关联表
        AttachAssociation attachAssociation = new AttachAssociation();
        attachAssociation.setAssociationId(id);
        attachAssociation.setAttachPath(path);
        attachAssociation.setNewAttachName(newFileName);
        attachAssociation.setRealAttachName(realFileName);
        attachAssociation.setAttachType(attachType);
        SpringUtils.getBean(AttachAssociationMapper.class).insert(attachAssociation);
        return path + newFileName;
    }

    /**
     * 文件大小、文件名长度、文件类型 校验
     *
     * @param file 上传的文件
     * @return
     */
    public static final void assertAllowed(MultipartFile file, String[] allowedExtension) {
        int fileNamelength = file.getOriginalFilename().length();
        if (fileNamelength > FileUploadConstants.DEFAULT_FILE_NAME_LENGTH) {
            throw new ServiceException("文件名长度超出：" + FileUploadConstants.DEFAULT_FILE_NAME_LENGTH, 400);
        }

        long size = file.getSize();
        if (FileUploadConstants.DEFAULT_MAX_SIZE != -1 && size > FileUploadConstants.DEFAULT_MAX_SIZE) {
            throw new ServiceException("文件大小超出：" + FileUploadConstants.DEFAULT_MAX_SIZE / 1024 / 1024 + "MB", 400);
        }

        String fileName = file.getOriginalFilename();
        // 文件后缀
        String extension = getExtension(file);
        if (allowedExtension != null && !isAllowedExtension(extension, allowedExtension)) {
            throw new ServiceException("filename : [" + fileName + "], extension : [" + extension + "], allowed " +
                    "extension : [" + Arrays.toString(allowedExtension) + "]");
        }
    }

    /**
     * 获取文件名的后缀
     *
     * @param file 表单文件
     * @return 后缀名
     */
    public static final String getExtension(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (StrUtil.isEmpty(extension)) {
            extension = MimeTypeUtils.getExtension(file.getContentType());
        }
        return extension;
    }

    /**
     * 判断MIME类型是否是允许的MIME类型
     *
     * @param extension
     * @param allowedExtension
     * @return
     */
    public static final boolean isAllowedExtension(String extension, String[] allowedExtension) {
        for (String str : allowedExtension) {
            if (str.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据路径文件生成File，路径不存在则创建
     * @param uploadDir
     * @param fileName
     * @return
     * @throws IOException
     */
    private static final File getAbsoluteFile(String uploadDir, String fileName) throws IOException {
        File desc = new File(uploadDir + File.separator + fileName);
        if (!desc.getParentFile().exists()) {
            desc.getParentFile().mkdirs();
        }
        if (!desc.exists()) {
            desc.createNewFile();
        }
        return desc;
    }

    /**
     * 输出指定文件的byte数组
     *
     * @param filePath 文件路径
     * @param os 输出流
     * @return
     */
    public static void writeBytes(String filePath, OutputStream os) throws IOException {
        FileInputStream fis = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new FileNotFoundException(filePath);
            }
            fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            int length;
            while ((length = fis.read(b)) > 0) {
                os.write(b, 0, length);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 下载文件名重新编码
     *
     * @param request 请求对象
     * @param fileName 文件名
     * @return 编码后的文件名
     */
    public static String getFileNameByEncode(HttpServletRequest request, String fileName)
            throws UnsupportedEncodingException {
        final String agent = request.getHeader("USER-AGENT");
        String filename = fileName;
        if (agent.contains("MSIE")) {
            // IE浏览器
            filename = URLEncoder.encode(filename, "utf-8");
            filename = filename.replace("+", " ");
        } else if (agent.contains("Firefox")) {
            // 火狐浏览器
            filename = new String(fileName.getBytes(), "ISO8859-1");
        } else if (agent.contains("Chrome")) {
            // google浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        } else {
            // 其它浏览器
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }
}