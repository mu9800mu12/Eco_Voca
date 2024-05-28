package com.voca.eco.app.controller;

import com.voca.eco.app.dto.Word700DTO;
import com.voca.eco.app.service.IWord700Service;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/word")
@Controller
public class WordController {

    private final IWord700Service word700Service;


    /**
     * 단어 700선 리스트 가져오기
     */
    @ResponseBody
    @PostMapping(value = "getWord700List")
    public List<Word700DTO> getWord700List() throws Exception {

        log.info(this.getClass().getName() + " getWord700List");

        return word700Service.getWord700List();
    }

    @GetMapping(value = "word700List")
    public String word700List() {
        return "word/word700List";
    }


    @GetMapping(value = "wordNews")
    public String wordNews() {
        return "word/wordNews";
    }
}
