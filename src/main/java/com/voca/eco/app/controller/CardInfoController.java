//package com.voca.eco.app.controller;
//
//import com.voca.eco.app.dto.CardInfoDTO;
//import com.voca.eco.app.service.impl.CardInfoService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/card-info")
//@RequiredArgsConstructor
//public class CardInfoController {
//
//    private final CardInfoService cardInfoService;
//
//    @PostMapping("/upload")
//    public ResponseEntity<CardInfoDTO> uploadCardInfo(@RequestBody CardInfoDTO cardInfoDTO) {
//        CardInfoDTO savedCardInfo = cardInfoService.saveCardInfo(cardInfoDTO);
//        return ResponseEntity.status(HttpStatus.CREATED).body(savedCardInfo);
//    }
//}
