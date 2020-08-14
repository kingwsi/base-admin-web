package com.kingwsi.bs.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.kingwsi.bs.common.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

/**
 * description: UploadFileServie <br>
 * date: 2020/8/13 15:05 <br>
 * author: ws <br>
 * version: 1.0 <br>
 */
@Slf4j
@Service
public class OssService {
    private static String endpoint = "oss-cn-hangzhou.aliyuncs.com";
    private static String accessKeyId = "LTAI4G4ku6stJdU9aen7fCru";
    private static String accessKeySecret = "E5pUwN4muhDItHinYzxiHbBVyButBy";

    private static String bucketName = "vant-admin";

    public String uploadImage(MultipartFile multipartFile) {
//        String suffix = Objects.requireNonNull(multipartFile.getOriginalFilename()).substring(multipartFile.getOriginalFilename().lastIndexOf(".")).toLowerCase();
//        if (!suffix.equals(".png") && !suffix.equals(".jpg") && !suffix.equals(".gif")) {
//            throw new CustomException("文件格式错误");
//        }
//        String fullPath = "";
//        OSS client = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//        String key = UUID.randomUUID().toString() + suffix;
//        try {
//            File file = File.createTempFile("oss-avatar-", suffix);
//            file.deleteOnExit();
//            multipartFile.transferTo(file);
//            PutObjectResult result = client.putObject(new PutObjectRequest(bucketName, key, file));
//            fullPath = "https://" + bucketName + "." + endpoint + "/" + key;
//            log.info("oss上传文件成功，格式->{}，文件名->{}", suffix, key);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return fullPath;
        return "https://vant-admin.oss-cn-hangzhou.aliyuncs.com/c749e499-ef95-49b1-9336-c2d1c2267552.png";
    }
}


