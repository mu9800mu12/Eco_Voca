package com.voca.eco.app.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.voca.eco.app.domain.CardRepository;
import com.voca.eco.app.dto.CardDTO;
import com.voca.eco.app.service.ICardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CardService implements ICardService {

    // 의존성 주입을 통해 CardRepository 객체를 주입받음
    private final CardRepository cardRepository;

    // application.properties 파일에서 클로바 OCR API URL을 가져옴
    @Value("${naver.ocr.url}")
    private String apiURL;

    // application.properties 파일에서 클로바 OCR API Secret Key를 가져옴
    @Value("${naver.ocr.secret}")
    private String secretKey;

    // 파일을 업로드하고 OCR 결과를 처리하는 메서드
    @Override
    public Map<String, Object> processCardUpload(MultipartFile file) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 클로바 OCR API를 호출하여 OCR 결과를 얻음
            String ocrResult = callOcrApi(file);

            // OCR 결과를 파싱하여 CardDTO 객체로 변환
            CardDTO cardDTO = parseOcrResult(ocrResult);

            // 카드 정보를 저장하거나 추가적인 처리 (주석 처리됨)
            // cardRepository.save(new CardEntity(cardDTO));

            // 성공 응답 생성
            response.put("success", true);
            response.put("cardInfo", cardDTO);
        } catch (Exception e) {
            // 예외 발생 시 실패 응답 생성
            response.put("success", false);
            response.put("message", e.getMessage());
        }
        return response;
    }

    // 클로바 OCR API를 호출하여 OCR 결과를 얻는 메서드
    private String callOcrApi(MultipartFile file) throws IOException {
        // API URL로 연결을 설정
        URL url = new URL(apiURL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("X-OCR-SECRET", secretKey);
        con.setDoOutput(true);

        // 파일을 API에 전송하는 로직
        try (OutputStream os = con.getOutputStream()) {
            byte[] buffer = new byte[4096];
            InputStream is = file.getInputStream();
            int bytesRead;
            // 파일의 데이터를 읽어 API에 전송
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        }

        // API 응답 처리
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // 응답이 성공인 경우, 응답 본문을 읽음
            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                // 응답 데이터를 한 줄씩 읽어서 StringBuilder에 추가
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
                // OCR 결과를 문자열로 반환
                return response.toString();
            }
        } else {
            // 응답이 실패인 경우, 예외를 발생시킴
            throw new IOException("Failed to call OCR API: " + responseCode);
        }
    }

    // OCR 결과를 파싱하여 CardDTO 객체로 변환하는 메서드
    private CardDTO parseOcrResult(String ocrResult) throws IOException {
        // JSON 파싱을 위한 ObjectMapper 객체 생성
        ObjectMapper mapper = new ObjectMapper();
        // OCR 결과(JSON 형식)를 JsonNode로 파싱
        JsonNode root = mapper.readTree(ocrResult);

        // 빌더 패턴을 사용하여 CardDTO 객체 생성 및 값 설정
        return CardDTO.builder()
                .cardNumber(root.path("cardNumber").asText())  // 카드 번호 설정
                .cardholderName(root.path("cardholderName").asText())  // 카드 소유자 이름 설정
                .expirationDate(root.path("expirationDate").asText())  // 카드 만료일 설정
                .cvc(root.path("cvc").asText())                // 카드 CVC 코드 설정
                .build();                                      // CardDTO 객체 생성 및 반환
    }
}
