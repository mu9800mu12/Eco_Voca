package com.voca.eco.common.config;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import feign.Contract;
import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.Decoder;
import feign.form.FormEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import feign.codec.Encoder;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;

@Configuration
public class OpenFeignConfig {

    @Value("${naver.client_id}")
    private String clientId;

    @Value("${naver.client_secret}")
    private String clientSecret;

    // application/x-www-form-urlencoded 형식으로 인코딩을 위한 FormEncoder 설정 ( 네이버 로그인 API - 2024.10.05 )
    @Bean
    public Encoder feignFormEncoder() {
        return new FormEncoder(new SpringEncoder(() -> new HttpMessageConverters()));
    }

    // XML 응답 처리를 위한 SpringDecoder 설정 ( 네이버 로그인 API - 2024.10.05 )
    @Bean
    public Decoder feignDecoder() {
        return new SpringDecoder(() ->
                new HttpMessageConverters(
                        new MappingJackson2XmlHttpMessageConverter(new XmlMapper())));
    }


    /**
     * 네이버 API 호출시 사용할 기본 Feign 설정
     */
    @Bean
    public Contract feignContract() {
        return new Contract.Default();
    }

    /**
     * 네이버 API 인증 헤더 설정
     */
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("X-Naver-Client-Id", clientId);
            requestTemplate.header("X-Naver-Client-Secret", clientSecret);
        };
    }

    /**
     * 오픈페인 수신, 전송 모든 과정 로그 찍기
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }




}
