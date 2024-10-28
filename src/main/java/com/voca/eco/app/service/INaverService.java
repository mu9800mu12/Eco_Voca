package com.voca.eco.app.service;


import com.voca.eco.app.dto.NaverDTO;
import com.voca.eco.app.dto.TokenDTO;

public interface INaverService {

    /**
     * 토큰 가져오기
     */
    TokenDTO getAccessToken(String code) throws Exception;

    /**
     * 네이버에서 정보 가져오기
     */
    NaverDTO getNaverUserInfo(TokenDTO pDTO) throws Exception;

    void deleteToken(String accessToken) throws Exception;

}
