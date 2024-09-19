package com.voca.eco.app.service;


import com.voca.eco.app.dto.NewsDTO;

public interface INewsService {

    /**
     * 뉴스 가져오기
     */
    NewsDTO getNewsList(String word) throws Exception;



}
