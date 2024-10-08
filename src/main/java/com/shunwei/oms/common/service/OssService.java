package com.shunwei.oms.common.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import com.aliyun.oss.OSSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class OssService {

    private OSSClient ossClient;

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;

    @Value("${aliyun.oss.bucketName}")
    private String bucketName;

    @Value("${aliyun.oss.directory}")
    private String directory;

    @PostConstruct
    public void init() {
        this.ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }

    public String uploadFile(String filename, InputStream fileInputStream) {
        // 返回文件在 OSS 中的 URL
        ossClient.putObject(bucketName, directory + filename, fileInputStream);
        //String fileUrl = String.format("%s/%s%s", endpoint, directory, filename);

        // 生成文件在 OSS 中的 URL
        String fileUrl = String.format("https://%s.%s/%s%s", bucketName, endpoint, directory, filename);
        return fileUrl;
    }

    @PreDestroy
    public void shutdown() {
        if (ossClient != null) {
            ossClient.shutdown();
        }
    }
}
