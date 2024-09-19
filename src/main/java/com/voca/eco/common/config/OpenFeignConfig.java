package com.voca.eco.common.config;

import feign.Contract;
import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenFeignConfig {

    @Value("${naver.client_id}")
    private String clientId;

    @Value("${naver.client_secret}")
    private String clientSecret;

    /**
     *  API 접속을 위해 접속 방법은 기본 값으로 설정 ( 반드시 필요한 설정)
     *  설정 값에 따라 @FeignClient에서 사용할 하위 어노테이션이 변경 됨
     * @return
     */

    /**
     * 네이버 뉴스검색 API 호출시 사용 설정
     * @return
     */
    @Bean
    public Contract feignContract() {
        return new Contract.Default();
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("X-Naver-Client-Id", clientId);
            requestTemplate.header("X-Naver-Client-Secret", clientSecret);
        };
    }

    /**
     * 오픈페인 수신, 전송 모든 과정 로그 찍기
     *
     * NONE: 로깅하지 않음(기본 값)
     * BASIC: 요청 메소드와 URI와 응답 상태와 실행시간 로깅
     * HEADERS 요청과 응답 헤더와 함께 기본 정보를 남김
     * FULL: 요청과 응답에 대한 헤더와 바디, 메타 데이터를 남김
     *
     * @return
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
