package com.voca.eco.app.controller;

import com.voca.eco.app.controller.response.CommonResponse;
import com.voca.eco.app.dto.CardInfoDTO;
import com.voca.eco.app.dto.MsgDTO;
import com.voca.eco.app.service.ICardInfoService;
import com.voca.eco.app.service.impl.CardInfoService;
import com.voca.eco.common.util.CmmUtil;
import jakarta.validation.Valid;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/api/card-info")
@RequiredArgsConstructor
@Slf4j
public class CardInfoController {

    private final ICardInfoService cardInfoService;

    @PostMapping(value = "/upload")
    @ResponseBody
    public String upload(@RequestParam(value = "cardImage") MultipartFile cardImage, ModelMap model)
            throws Exception {

        String fileUrl = "https://kr.object.ncloudstorage.com/fridge/2024/06/17/240617071652284.jpg";
        try {

            log.info("fileUrl : " + fileUrl);
            String fileSeq = "1";

            BufferedImage originalImage = loadImageFromUrl(fileUrl);

            BufferedImage highResolutionImage = resizeImageWithHigherResolution(originalImage, 1200,
                    1800); // 예시 해상도: 1200x1800

            List<CardInfoDTO> rList = Optional.ofNullable(
                            cardInfoService.cardOcr(highResolutionImage))
                    .orElseGet(ArrayList::new);

            model.addAttribute("rList", rList);

        } catch (Exception e) {
            e.printStackTrace();
            return "/main";
        }

        return "/user/cardInfo";

    }

    public static BufferedImage resizeImageWithHigherResolution(BufferedImage originalImage,
            int targetWidth, int targetHeight) throws IOException {

        // 이미지의 현재 해상도 정보 가져오기
        int currentWidth = originalImage.getWidth();
        int currentHeight = originalImage.getHeight();

        // 이미지를 더 높은 해상도로 확대
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(originalImage, 0, 0, targetWidth, targetHeight, 0, 0, currentWidth,
                currentHeight, null);
        g.dispose();

        return resizedImage;
    }

    public static BufferedImage loadImageFromUrl(String fileUrl) throws IOException {
        URL url = new URL(fileUrl);
        BufferedImage originalImage = ImageIO.read(url);
        return originalImage;
    }

    @PostMapping(value = "basic") //api/card-info/basic으로 호출해야 함
    public ResponseEntity basic(@Valid @RequestBody CardInfoDTO pDTO, BindingResult bindingResult)
            throws Exception {

        log.info(this.getClass().getName() + "[Controller] : 카드정보 저장 시작!");

        if (bindingResult.hasErrors()) {
            return CommonResponse.getErrors(bindingResult);
        }


        String msg = "";

        log.info("pDTO :" + pDTO);

        int res = cardInfoService.mongoTest(pDTO);

        if (res == 1) {
            msg = "카드정보 저장 성공";
        } else {
            msg = "저장 실패하였습니다.";
        }

        MsgDTO dto = MsgDTO.builder().result(res).msg(msg).build();

        log.info(this.getClass().getName() + "[Controller] : 카드정보 저장 끝!");

        return ResponseEntity.ok(
                CommonResponse.of(HttpStatus.OK, HttpStatus.OK.series().name(), dto));

    }


}
