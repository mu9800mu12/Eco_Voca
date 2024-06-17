package com.voca.eco.app.service.impl;

import com.voca.eco.app.domain.Entity.CardInfoMongoMapper;
import com.voca.eco.app.domain.ICardInfoMongo;
import com.voca.eco.app.dto.CardInfoDTO;
import com.voca.eco.app.service.ICardInfoService;
import com.voca.eco.common.util.DateUtil;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class CardInfoService implements ICardInfoService {

    private final ICardInfoMongo cardInfoMongo;

    @Value("${naver.ocr.secret}")
    private String ocrSecretKey;

    @Value("${naver.ocr.url}")
    private String ocrInvokeUrl;

    private final RestTemplate restTemplate;
    /* 여기다간 네가 사용할 매퍼 적어주면 되고 */

    /* URL에서 이미지를 다운로드하고 Base64로 인코딩하는 메소드 */
    public static String encodeImageToBase64(BufferedImage image, String formatName) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, formatName, outputStream);
        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }

    /* Naver Clova OCR 사용하여 영수증 스캔 */
    @Override
    public List<CardInfoDTO> cardOcr(BufferedImage fileUrl) throws Exception {

        log.info(this.getClass().getName() + ".receiptOcr [service] Naver Clova OCR 사용하여 영수증 스캔 실행");

        List<CardInfoDTO> rList = new ArrayList<>();

        String base64Image = encodeImageToBase64(fileUrl, "png"); // 파일 경로를 전달하여 Base64로 인코딩

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-OCR-SECRET", ocrSecretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("version", "V2");
        jsonObject.put("requestId", "string");
        jsonObject.put("timestamp", 0);

        JSONObject data = new JSONObject();
        data.put("format", "png");
        data.put("data", base64Image);
        data.put("name", "CardOcr" + DateUtil.getDateTime("HHmmss")); //시간

        JSONArray img = new JSONArray();
        img.add(data);
        jsonObject.put("images", img);
        log.info("Request Headers: " + headers);

        HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);

        restTemplate.getInterceptors().add((request, body, execution) -> {
            ClientHttpResponse response = execution.execute(request, body);
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return response;
        });

        ResponseEntity<String> response = restTemplate.exchange(ocrInvokeUrl, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Failed to get OCR result: " + response.getStatusCode());
        }

        log.info("Response Body: " + response.getBody());

        /* 이부분에 이제 니가 원하는 데이터 끌어오면 됨 */

        log.info(this.getClass().getName() + ".receiptOcr [service] Naver Clova OCR 사용하여 카드 스캔 종료");

        return rList; // 이것도 네 코드에 맞춰서 수정
    }


    @Override
    public int mongoTest(CardInfoDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + "[service] : 카드정보 입력 시작!");

        String colNm = "CARDINFO";

        int res = cardInfoMongo.insertCard(pDTO, colNm);

        log.info(this.getClass().getName() + "[service] : 카드정보 입력 끝!");


        return res;
    }
}
