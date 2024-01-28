package com.xxx.jingdong.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * ClassName:UploadConfig
 * Package:com.example.jingdong.config
 * Description:
 *
 * @Author:Aaron
 * @Create:2023/8/17 - 14:31
 * @Version:v1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "file-path")
public class FileUploadConfig {
    private  String basePath;
    private  String uploadPath;
}
