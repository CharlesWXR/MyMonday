package edu.njnu.jdxy.bootserver.utils;

import io.minio.MinioClient;
import jdk.internal.util.xml.impl.Input;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Slf4j
@Component
public class MinioUtil {
    @Value("${minio.access-key}")
    private String accessKey;

    @Value("${minio.secret-key}")
    private String secretKey;

    @Value("${minio.server}")
    private String endPoint;

    @Value("${minio.bucket}")
    private String bucketName;

    public String uploadFile(InputStream is, String filename, String contentType) {
        try {
            MinioClient minioClient = new MinioClient(endPoint, accessKey, secretKey);
            boolean exist = minioClient.bucketExists(bucketName);
            if (!exist) {
                minioClient.makeBucket(bucketName);
            }
            minioClient.putObject(bucketName, filename, is, contentType);
            is.close();
            log.info("MinIO: {} in {} upload success", filename, bucketName);
            return getFileUrl(bucketName, filename);
        } catch (Exception e) {
            log.error("MinIO: {} in {} upload fail: {}", filename, bucketName, e.getMessage());
            return null;
        }
    }

    public InputStream downloadFile(String filename) {
        try {
            MinioClient minioClient = new MinioClient(endPoint, accessKey, secretKey);
            InputStream is = minioClient.getObject(bucketName, filename);
            log.info("MinIO: {} in {} downloaded success", filename, bucketName);
            return is;
        } catch (Exception e) {
            log.error("MinIO: {} in {} downloaded fail: {}", filename, bucketName, e.getMessage());
            return null;
        }
    }

    public String getFileUrl(String bucketName, String filename) {
        try {
            MinioClient minioClient = new MinioClient(endPoint, accessKey, secretKey);
            log.info("MinIO: {} in {} generate url success", filename, bucketName);
            return minioClient.presignedGetObject(bucketName, filename);
        } catch (Exception e) {
            log.error("MinIO: {} in {} generate url fail: {}", filename, bucketName, e.getMessage());
            return null;
        }
    }
}
