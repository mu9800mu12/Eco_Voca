package com.voca.eco.common.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class AWSConfig {

    @Value("${naver.access.key}")
    private String accessKey;

    @Value("${naver.secret.key}")
    private String secretKey;

    // Object Storage 리전
    private String region = "kr-standard";


    @Bean
    public AmazonS3Client amazonS3Client() {
        // AWS 자격 증명(accessKey, secretKey)을 사용하여 BasicAWSCredentials 객체를 생성
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey);

        // AmazonS3ClientBuilder를 사용하여 AmazonS3Client 객체를 생성하고 반환
        return (AmazonS3Client) AmazonS3ClientBuilder
                .standard()  // 표준 클라이언트 빌더를 사용
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))  // 자격 증명을 설정
                .withRegion(region)  // S3 클라이언트를 생성할 리전을 설정
                .build();
    }


}