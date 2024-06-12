package com.voca.eco.app.controller;

import com.voca.eco.app.service.ICardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Slf4j
@Controller
//@RequestMapping("/api/cards")
@RequestMapping("/")
@RequiredArgsConstructor
public class CardController {

    private ICardService cardService;

//    @ResponseBody
    @PostMapping("/upload")
    public Map<String, Object> uploadCard(@RequestParam("file") MultipartFile file) {
        return cardService.processCardUpload(file);
    }


}