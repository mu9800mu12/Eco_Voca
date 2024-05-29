package com.voca.eco.app.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.voca.eco.app.dto.NewsDTO;
import com.voca.eco.app.service.INewsService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class NewsService implements INewsService {

    @Value("${naver.client_id}")
    private String naverClientId;
    @Value("${naver.client_secret}")
    private String naverClientSecret;


    @Override
    public NewsDTO getNewsList(String word) throws Exception {

        log.info(this.getClass().getName() + " getNewsList");

        String apiUrl = "https://openapi.naver.com/v1/search/news.json?" +
                "query=" + URLEncoder.encode(word, "UTF-8") +
                "&display=" + 10 +
                "&sort=date";

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", naverClientId);
        requestHeaders.put("X-Naver-Client-Secret", naverClientSecret);
        String responseBody = get(apiUrl, requestHeaders);

        log.info(responseBody);

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(responseBody, NewsDTO.class);
    }

    private static String get(String apiUrl, Map<String, String> requestHeaders){

        HttpURLConnection con = connect(apiUrl);

        try {
            con.setRequestMethod("GET");

            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());

            }

            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());

            } else { // 오류 발생
                return readBody(con.getErrorStream());

            }

        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);

        } finally {
            con.disconnect();

        }
    }


    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);

            return (HttpURLConnection)url.openConnection();

        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);

        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);

        }
    }



    private static String readBody(InputStream body){

        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;

            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();

        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }
}
