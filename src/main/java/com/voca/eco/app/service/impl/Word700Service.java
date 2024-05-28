package com.voca.eco.app.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.voca.eco.app.domain.Entity.Word700Entity;
import com.voca.eco.app.domain.Word700Repository;
import com.voca.eco.app.dto.Word700DTO;
import com.voca.eco.app.service.IWord700Service;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class Word700Service implements IWord700Service {

    private final Word700Repository word700Repository;
    
    @Override
    public List<Word700DTO> getWord700List() throws Exception {
        log.info(this.getClass().getName() + " getWordList");

        List<Word700Entity> pList = word700Repository.findAll();
//        Collections.shuffle(pList);

        return new ObjectMapper().convertValue(pList,
                new TypeReference<>() {
                });

    }
}
