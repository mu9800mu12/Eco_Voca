package com.voca.eco.app.domain.Entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;

import com.voca.eco.app.domain.AbstractMongoDBComon;
import com.voca.eco.app.domain.ICardInfoMongo;
import com.voca.eco.app.domain.IMongoMapper;
import com.voca.eco.app.dto.CardInfoDTO;
import com.voca.eco.app.dto.ChatDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class CardInfoMongoMapper extends AbstractMongoDBComon implements ICardInfoMongo {

    private final MongoTemplate mongodb;

    @Override
    public int insertCard(CardInfoDTO pDTO, String colNm) throws Exception {

        log.info(this.getClass().getName() + "[Mapper] : 카드정보 입력 시작");

        int res = 0;

        // 데이터 저장 컬렉션 생성
        super.createCollection(mongodb, colNm);

        // 저장할 컬렉션 객체 생성
        MongoCollection<Document> col = mongodb.getCollection(colNm);

        // DTO를 map 데이터 타입 변경, map을 Document로 변경
        col.insertOne(new Document(new ObjectMapper().convertValue(pDTO, Map.class)));

        res = 1;

        log.info(this.getClass().getName() + "[Mapper] : 카드정보 입력 끝");

        return res;
    }
}