//package com.voca.eco.app.service.impl;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.voca.eco.app.dto.NewsDTO;
//import com.voca.eco.app.service.INewsService;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.util.HashMap;
//import java.util.Map;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//// Lombok 어노테이션을 사용하여 로그 인스턴스를 자동으로 생성
//@Slf4j
//// Lombok 어노테이션을 사용하여 필요한 생성자를 자동으로 생성
//@RequiredArgsConstructor
//// Spring 어노테이션을 사용하여 이 클래스를 서비스 컴포넌트로 표시
//@Service
//public class NewsService implements INewsService {
//
//    // 애플리케이션 프로퍼티에서 값을 주입받음
//    @Value("${naver.client_id}")
//    private String naverClientId;
//    @Value("${naver.client_secret}")
//    private String naverClientSecret;
//
//    // INewsService 인터페이스의 getNewsList 메서드를 구현
//    @Override
//    public NewsDTO getNewsList(String word) throws Exception {
//
//        // 현재 클래스의 getNewsList 메서드 호출을 로그로 남김
//        log.info(this.getClass().getName() + " getNewsList");
//
//        // 네이버 뉴스 검색 API URL을 생성
//        String apiUrl = "https://openapi.naver.com/v1/search/news.json?" +
//                "query=" + URLEncoder.encode(word, "UTF-8") +
//                "&display=" + 10 +
//                "&sort=date";
//
//        // API 요청 헤더를 설정
//        Map<String, String> requestHeaders = new HashMap<>();
//        requestHeaders.put("X-Naver-Client-Id", naverClientId);
//        requestHeaders.put("X-Naver-Client-Secret", naverClientSecret);
//
//        // API 요청을 보내고 응답을 받아옴
//        String responseBody = get(apiUrl, requestHeaders);
//
//        // 응답 내용을 로그로 남김
//        log.info(responseBody);
//
//        // JSON 응답을 객체로 변환하기 위해 ObjectMapper 사용
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        // JSON 응답을 NewsDTO 객체로 변환하여 반환
//        return objectMapper.readValue(responseBody, NewsDTO.class);
//    }
//
//    // HTTP GET 요청을 보내고 응답을 받아오는 메서드
//    private static String get(String apiUrl, Map<String, String> requestHeaders){
//
//        // API URL로 연결을 설정
//        HttpURLConnection con = connect(apiUrl);
//
//        try {
//            // GET 요청으로 설정
//            con.setRequestMethod("GET");
//
//            // 요청 헤더를 설정
//            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
//                con.setRequestProperty(header.getKey(), header.getValue());
//            }
//
//            // 응답 코드를 확인
//            int responseCode = con.getResponseCode();
//
//            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
//                return readBody(con.getInputStream());
//            } else { // 오류 발생
//                return readBody(con.getErrorStream());
//            }
//
//        } catch (IOException e) {
//            throw new RuntimeException("API 요청과 응답 실패", e);
//        } finally {
//            // 연결 종료
//            con.disconnect();
//        }
//    }
//
//    // 주어진 URL로 연결을 생성하는 메서드
//    private static HttpURLConnection connect(String apiUrl){
//        try {
//            URL url = new URL(apiUrl);
//            return (HttpURLConnection) url.openConnection();
//        } catch (MalformedURLException e) {
//            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
//        } catch (IOException e) {
//            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
//        }
//    }
//
//    // 응답 바디를 읽어오는 메서드
//    private static String readBody(InputStream body){
//
//        InputStreamReader streamReader = new InputStreamReader(body);
//
//        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
//            StringBuilder responseBody = new StringBuilder();
//
//            String line;
//            // 한 줄씩 읽어와서 StringBuilder에 추가
//            while ((line = lineReader.readLine()) != null) {
//                responseBody.append(line);
//            }
//
//            return responseBody.toString();
//
//        } catch (IOException e) {
//            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
//        }
//    }
//}
