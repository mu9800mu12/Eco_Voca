package com.voca.eco.app.service.impl;

// 필요한 라이브러리 및 클래스 임포트
import com.fasterxml.jackson.databind.ObjectMapper;
import com.voca.eco.app.dto.NewsDTO;
import com.voca.eco.app.service.INaverNewsService;
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

@Slf4j // 로그를 사용하기 위한 Lombok 어노테이션
@RequiredArgsConstructor // final 필드 및 @NonNull 필드를 초기화하는 생성자를 생성하는 Lombok 어노테이션
@Service // Spring의 서비스 계층 컴포넌트를 나타냄
public class NewsService implements INewsService {

    private final INaverNewsService naverNewsService;


//    // application.properties 파일에서 값을 주입받음
//    @Value("${naver.client_id}")
//    private String naverClientId;
//

    //
    @Override
    public NewsDTO getNewsList(String word) throws Exception {


        log.info(getClass().getName() + "오픈페인으로 API 호출 시도");

        String query = URLEncoder.encode(word, "UTF-8");
        String sort = "date";
        String display = "10";

        // OpenFeign 정의된 API 인터페이스 가져오기
        NewsDTO rDTO = naverNewsService.getNewsList(query, sort, display);

        log.info(getClass().getName() + "오픈페인으로 API 호출 성공");
        log.info(getClass().getName() + "그 값인 rDTO : " + rDTO);


        return rDTO;
    }
//    @Value("${naver.client_secret}")
//    private String naverClientSecret;
//
//
//
//    // 뉴스 목록을 가져오는 메서드
//    @Override
//    public NewsDTO getNewsList(String word) throws Exception {
//
//        log.info(this.getClass().getName() + " getNewsList"); // 로그 출력
//
//        // API 호출을 위한 URL 생성
//        String apiUrl = "https://openapi.naver.com/v1/search/news.json?" +
//                "query=" + URLEncoder.encode(word, "UTF-8") + // 검색어를 URL 인코딩
//                "&display=" + 10 + // 한번에 표시할 검색 결과 수
//                "&sort=date"; // 결과 정렬 방식
//
//
//        //@@@@@@@@@@@@@@@@@@@@@@ 이 부분을 인터페이스 쪽으로 옮겨야할 것 같은데
//
//        // 요청 헤더 설정
//        Map<String, String> requestHeaders = new HashMap<>();
//        requestHeaders.put("X-Naver-Client-Id", naverClientId);
//        requestHeaders.put("X-Naver-Client-Secret", naverClientSecret);
//
//        // API 요청 및 응답 받기
//        String responseBody = get(apiUrl, requestHeaders);
//
//        log.info(responseBody); // 응답 로그 출력
//
//        // JSON 응답을 NewsDTO로 담아서 반환 함
//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.readValue(responseBody, NewsDTO.class);
//    }
//
//    // HTTP GET 요청을 보내고 응답을 읽어 반환하는 메서드
//    private static String get(String apiUrl, Map<String, String> requestHeaders) {
//        HttpURLConnection con = connect(apiUrl); // URL 연결
//
//        try {
//            con.setRequestMethod("GET"); // HTTP GET 메서드 설정
//
//            // 요청 헤더 설정
//            for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
//                con.setRequestProperty(header.getKey(), header.getValue());
//            }
//
//            int responseCode = con.getResponseCode(); // 응답 코드 확인
//
//            // 응답 코드에 따른 처리
//            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
//                return readBody(con.getInputStream());
//            } else { // 오류 발생
//                return readBody(con.getErrorStream());
//            }
//
//        } catch (IOException e) {
//            throw new RuntimeException("API 요청과 응답 실패", e); // 예외 처리
//        } finally {
//            con.disconnect(); // 연결 종료
//        }
//    }
//
//    // 주어진 URL로 HTTP 연결을 설정하는 메서드
//    private static HttpURLConnection connect(String apiUrl) {
//        try {
//            URL url = new URL(apiUrl);
//            return (HttpURLConnection) url.openConnection(); // HTTP 연결 객체 반환
//        } catch (MalformedURLException e) {
//            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e); // 잘못된 URL 예외 처리
//        } catch (IOException e) {
//            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e); // 연결 실패 예외 처리
//        }
//    }
//
//    // InputStream을 읽어 문자열로 반환하는 메서드
//    private static String readBody(InputStream body) {
//        InputStreamReader streamReader = new InputStreamReader(body);
//
//        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
//            StringBuilder responseBody = new StringBuilder();
//            String line;
//
//            // 줄 단위로 읽어서 문자열로 변환
//            while ((line = lineReader.readLine()) != null) {
//                responseBody.append(line);
//            }
//
//            return responseBody.toString(); // 최종 문자열 반환
//        } catch (IOException e) {
//            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e); // 읽기 실패 예외 처리
//        }
//    }
}
