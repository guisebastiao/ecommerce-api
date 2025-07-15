package com.guisebastiao.ecommerceapi.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {
    public static String BUCKET_CLIENT_PICTURES = "client-pictures";
    public static String BUCKET_PRODUCT_PICTURES = "product-pictures";

    @Value("${minio.url}")
    private String minioUrl;

    @Value("${minio.user}")
    private String minioUser;

    @Value("${minio.pass}")
    private String minioPass;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(minioUrl)
                .credentials(minioUser, minioPass)
                .build();
    }
}