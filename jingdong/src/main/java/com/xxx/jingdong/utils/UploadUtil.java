package com.xxx.jingdong.utils;

import com.xxx.jingdong.config.FileUploadConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Slf4j
public class UploadUtil {
    public static String fileUpload(MultipartFile uploadFile, FileUploadConfig fileUploadConfig){
        if(uploadFile.getSize()==0){
            return "未更新文件";
        }else{
        log.info("【文件大小】{}",uploadFile.getSize());
        SimpleDateFormat sd=new SimpleDateFormat("yyy/MM/dd");
        //获取文件名
        String fileName=uploadFile.getOriginalFilename();
        //获取文件后缀名
        String suffixName=fileName.substring(fileName.lastIndexOf("."));
        //重新生成文件名
        fileName= UUID.randomUUID().toString().replace("-","")+suffixName;
        //添加日期目录
        String format=sd.format(new Date());
        //指定本地文件夹储存图片
        String filePath= fileUploadConfig.getUploadPath()+"/ImgUpload"+"/"+format+"/";
        File file=new File(filePath,fileName);
        if (!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        try {
            log.info("【返回路径11】{}",filePath+fileName);
            FileUtils.copyInputStreamToFile(uploadFile.getInputStream(),file);
            log.info("【返回路径11】{}",fileUploadConfig.getBasePath()+"/ImgUpload/"+format+"/"+fileName);
            return fileUploadConfig.getBasePath()+"/ImgUpload/"+format+"/"+fileName;

        } catch (IOException e) {
            log.info("【异常2】{}",e);
            return null;
        }
    }
  }
}
