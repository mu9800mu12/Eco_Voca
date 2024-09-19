package com.voca.eco.app.service;

import com.voca.eco.app.dto.NewsDTO;
import com.voca.eco.app.dto.NewsDTO.item;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import java.net.URLEncoder;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "NaverNewsAPI", url = "https://openapi.naver.com")
public interface INaverNewsService {

    @RequestLine("GET /v1/search/news.json?query={query}&sort={sort}&display={display}")
    NewsDTO getNewsList(
            @Param("query") String query,
            @Param("sort") String sort,
            @Param("display") String display
    );
}


//
//String lastBuildDate,   // 검색 결과를 생성한 시간
//String total,           // 총 검색 결과 개수
//String start,           // 검색 시작 위치
//String display,         // 한 번에 표시할 검색 결과 개수
//List<item> items






    //    @RequestLine("GET /v1/search/news?query={query}")
//    default NewsDTO getNewsList(
//            @Param("query") String word) throws Exception {
//        String encodedQuery = URLEncoder.encode(word, "UTF-8");
//        return getNewsListInternal(encodedQuery); //네이버가 검색어를 utf-8로 받기 떄문에 utf8로 인코딩 하여 전송
//    }
//
//    // 인코딩 된 word로 api 호출
//    @RequestLine("GET /v1/search/news?query={query}")
//    NewsDTO getNewsListInternal(@Param("query") String encodedQuery);
//}