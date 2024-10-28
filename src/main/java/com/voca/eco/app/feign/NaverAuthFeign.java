package com.voca.eco.app.feign;

import com.voca.eco.app.dto.TokenDTO;
import com.voca.eco.common.config.OpenFeignConfig;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "naverAuthClient", url = "https://nid.naver.com", configuration = OpenFeignConfig.class)
public interface NaverAuthFeign {


    @RequestLine("POST /oauth2.0/token")
    @Headers("Content-Type: application/x-www-form-urlencoded;charset=utf-8")
    TokenDTO deleteToken(
            @Param("grant_type") String grantType,
            @Param("client_id") String clientId,
            @Param("client_secret") String clientSecret,
            @Param("access_token") String accessToken,
            @Param("service_provider") String serviceProvider
    );

}
