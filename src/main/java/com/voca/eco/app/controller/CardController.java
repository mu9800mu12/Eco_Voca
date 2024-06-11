package com.voca.eco.app.controller;

import com.voca.eco.app.service.ICardService;
import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/card")
public class CardController {

//    @Autowired
    private ICardService cardOcrService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadCardImage(@RequestParam("file") MultipartFile file) {
        try {
            String cardInfo = cardOcrService.extractCardInfo(file);
            return ResponseEntity.ok(cardInfo);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to process image");
        }
    }
}