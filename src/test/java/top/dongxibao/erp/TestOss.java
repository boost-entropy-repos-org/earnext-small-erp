package top.dongxibao.erp;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import top.dongxibao.erp.util.AliOSSUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @ClassName TestOss
 * @description 1
 * @author Dongxibao
 * @date 2020/11/22
 * @Version 1.0
 */
public class TestOss {
    public static void main(String[] args) {
        String picPath ="F:\\xxx.jpg";
        MultipartFile file = getMulFileByPath(picPath);
        AliOSSUtil aliOSSUtil = new AliOSSUtil();
//        String imageFile = aliOSSUtil.uploadImageFile(file);
//        System.out.println(imageFile);
        aliOSSUtil.downloadFile("2020/11/22/0384e326ffae4c19bacb4efc2262b426MyFileName.jpg");
//        try {
//            byte[] bytes = file.getBytes();
//            for(byte b : bytes){
//                System.out.print(b);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        //图片上传
//        picUpload(file);
    }

    private static void picUpload(MultipartFile file) {
        //图片上传
        if (file!=null) {// 判断上传的文件是否为空
            String path=null;// 文件路径
            String type=null;// 文件类型
            String fileName=file.getOriginalFilename();// 文件原名称
            System.out.println("上传的文件原名称:"+fileName);
            // 判断文件类型
            type=fileName.indexOf(".")!=-1?fileName.substring(fileName.lastIndexOf(".")+1, fileName.length()):null;
            if (type!=null) {// 判断文件类型是否为空
                if ("GIF".equals(type.toUpperCase())||"PNG".equals(type.toUpperCase())||"JPG".equals(type.toUpperCase())) {
                    // 项目在容器中实际发布运行的根路径
//	                    String realPath=request.getSession().getServletContext().getRealPath("/");
                    //哥哥自己写的路径
                    String realPath="F://pic//";
                    // 自定义的文件名称
                    String trueFileName=fileName;
                    // 设置存放图片文件的路径
                    path=realPath+trueFileName;
                    System.out.println("存放图片文件的路径:"+path);
                    // 转存文件到指定的路径
                    try {
                        file.transferTo(new File(path));
                    }  catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("文件成功上传到指定目录下");
                }else {
                    System.out.println("不是我们想要的文件类型,请按要求重新上传");
                }
            }else {
                System.out.println("文件类型为空");
            }
        }
        System.out.println("没有找到相对应的文件");
    }

    private static MultipartFile getMulFileByPath(String picPath) {
        FileItem fileItem = createFileItem(picPath);
        MultipartFile mfile = new CommonsMultipartFile(fileItem);
        return mfile;
    }

    private static FileItem createFileItem(String filePath)
    {
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        String textFieldName = "textField";
        int num = filePath.lastIndexOf(".");
        String extFile = filePath.substring(num);
        FileItem item = factory.createItem(textFieldName, "text/plain", true,
                "MyFileName" + extFile);
        File newfile = new File(filePath);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        try
        {
            FileInputStream fis = new FileInputStream(newfile);
            OutputStream os = item.getOutputStream();
            while ((bytesRead = fis.read(buffer, 0, 8192))
                    != -1)
            {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            fis.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return item;
    }

}
