package top.dongxibao.erp.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.BucketInfo;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import top.dongxibao.erp.exception.ServiceException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;

/**
 * 官方文档：https://help.aliyun.com/document_detail/114894.html?spm=a2c4g.11186623.6.541.426a2c4cD0QCX2
 *
 * @ClassName AliOSSUtil
 * @description 阿里oss工具类
 * @author Dongxibao
 * @date 2020/11/22
 * @Version 1.0
 */
@Slf4j
public class AliOSSUtil {
    //设置URL过期时间为10年
    private final static Date OSS_URL_EXPIRATION = DateUtils.addDays(new Date(), 365 * 10);
    @Autowired
    private OSS ossClient;
    @Value("${aliyun.oss.bucketName}")
    private String aliyunOssBucketName;
    @Value("${aliyun.oss.endpoint}")
    private String aliyunOssEndpoint;

    /**
     * 当Bucket不存在时创建Bucket
     */
    public void createBucket() {
        try {
            // 判断Bucket是否存在。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176
            // .docoss/sdk/java-sdk/init
            if (ossClient.doesBucketExist(aliyunOssBucketName)) {
                log.info("您已经创建Bucket：" + aliyunOssBucketName + "。");
            } else {
                log.info("您的Bucket不存在，创建Bucket：" + aliyunOssBucketName + "。");
                // 创建Bucket。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
                // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176
                // .docoss/sdk/java-sdk/init
                ossClient.createBucket(aliyunOssBucketName);
            }
            // 查看Bucket信息。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176
            // .docoss/sdk/java-sdk/init
            BucketInfo info = ossClient.getBucketInfo(aliyunOssBucketName);
            System.out.println("Bucket " + aliyunOssBucketName + "的信息如下：");
            System.out.println("\t数据中心：" + info.getBucket().getLocation());
            System.out.println("\t创建时间：" + info.getBucket().getCreationDate());
            System.out.println("\t用户标志：" + info.getBucket().getOwner());
        } catch (Exception e) {
            log.error("{}", "创建Bucket失败,请核对Bucket名称(规则：只能包含小写字母、数字和短横线，必须以小写字母和数字开头和结尾，长度在3-63之间)");
            throw new ServiceException("创建Bucket失败,请核对Bucket名称(规则：只能包含小写字母、数字和短横线，必须以小写字母和数字开头和结尾，长度在3-63之间)");
        }
    }

    /**
     * 上传到OSS服务器  如果同名文件不会覆盖服务器上的
     * @param inputStream 文件流
     * @param fileName  上传到OSS上文件名，带后缀
     * @return 文件的访问地址
     */
    public String uploadFile(InputStream inputStream, String fileName) {
        try {
            log.info("文件上传的名字为：{}",fileName);
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String newFileName = uuid + fileName;
            //获取当前日期,然后以日期和新的文件名组成全路径，使得oss中的文件按照日期进行分类存储
            String date = new DateTime().toString("yyyy/MM/dd");
            String fullFileName = date + "/" + newFileName;
            log.info("文件保存在oss的全路径为：{}",fullFileName);
            // 创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(inputStream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getContentType(FilenameUtils.getExtension("." + fullFileName)));
            objectMetadata.setContentDisposition("inline;filename=" + fullFileName);
            // 上传文件
            PutObjectResult putResult = ossClient.putObject(aliyunOssBucketName, fullFileName,
                    inputStream, objectMetadata);
            return fullFileName;
        } catch (Exception e) {
            log.error("{}", "上传文件失败");
            throw new ServiceException("上传文件失败");
        } finally {
            ossClient.shutdown();
        }
    }

    /**
     * 上传到OSS服务器  如果同名文件会覆盖服务器上的
     * @param file 图片文件
     * @return 文件的访问地址
     */
    public String uploadImageFile(MultipartFile file) {
        try(InputStream inputStream = file.getInputStream()) {
            // 为了使得文件可以重复上传，每次上传的时候需要将文件名进行修改
            String fileName = file.getOriginalFilename();
            log.info("图片上传的名字为：{}",fileName);
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String newFileName = uuid + fileName;

            //获取当前日期,然后以日期和新的文件名组成全路径，使得oss中的文件按照日期进行分类存储
            String date = new DateTime().toString("yyyy/MM/dd");
            String fullFileName = date + "/" + newFileName;
            log.info("图片保存在oss的全路径为：{}",fullFileName);
            //第一个参数Bucket名称 第二个参数 上传到oss文件路径和文件名称
            ossClient.putObject(aliyunOssBucketName, fullFileName, inputStream);
            return "https://" + aliyunOssBucketName + "." + aliyunOssEndpoint + "/" + fullFileName;
        } catch (Exception e) {
            log.error("{}", "上传文件失败");
            throw new ServiceException("上传文件失败");
        } finally {
            ossClient.shutdown();
        }
    }

    /**
     * 下载oss文件
     * @param objectName
     * @throws IOException
     */
    public void downloadFile(String objectName) {
        try {
            // ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
            OSSObject ossObject = ossClient.getObject(aliyunOssBucketName, objectName);
            // 读取文件内容。
            log.info("Object content:");
            BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
            while (true) {
                String line = reader.readLine();
                if (line == null) break;
                // TODO：业务处理
                System.out.println("\n" + line);
            }
            // 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
            reader.close();
        } catch (Exception e) {
            log.error("{}", "上传文件失败");
            throw new ServiceException("上传文件失败");
        } finally {
            ossClient.shutdown();
        }
    }

    /** todo ：测试
     * 获得文件路径
     * @param fileUrl  文件的URL
     * @param fileDir  文件在OSS上的路径
     * @return 文件的路径
     */
    private String getImgUrl(String fileUrl, String fileDir) {
        if (StringUtils.isEmpty(fileUrl)) {
            log.error("{}", "文件地址为空");
            throw new RuntimeException("文件地址为空");
        }
        String[] split = fileUrl.split("/");

        //获取oss图片URL失败
        URL url = ossClient.generatePresignedUrl(aliyunOssBucketName, fileDir + split[split.length - 1],
                OSS_URL_EXPIRATION);
        if (url == null) {
            log.error("{}", "获取oss文件URL失败");
            throw new ServiceException("获取oss文件URL失败");
        }
        return url.toString();
    }

        /**
         * 判断OSS服务文件上传时文件的contentType
         *
         * @param FilenameExtension 文件后缀
         * @return 后缀
         */
    private static String getContentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase("bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase("gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase("jpeg") ||
                FilenameExtension.equalsIgnoreCase("jpg") ||
                FilenameExtension.equalsIgnoreCase("png")) {
            return "image/jpeg";
        }
        if (FilenameExtension.equalsIgnoreCase("html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase("txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase("vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase("pptx") ||
                FilenameExtension.equalsIgnoreCase("ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase("docx") ||
                FilenameExtension.equalsIgnoreCase("doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase("xml")) {
            return "text/xml";
        }
        return "image/jpeg";
    }
}
