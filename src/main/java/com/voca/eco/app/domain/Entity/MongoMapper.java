package com.voca.eco.app.domain.Entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;

import com.voca.eco.app.domain.AbstractMongoDBComon;
import com.voca.eco.app.domain.IMongoMapper;
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
public class MongoMapper implements IMongoMapper {
    private final MongoTemplate mongodb;
    @Override
    public int insertData(ChatDTO pDTO, String colNm) throws Exception {
        log.info(this.getClass().getName() + "[몽고 매퍼] : insertDate 시작");

        int res = 0;

        // 데이터를 저장할 컬렉션 생성
        //super.createCollection(mongodb, colNm);

        // 저장할 컬렉션 객체 생성
        MongoCollection<Document> col = mongodb.getCollection(colNm);

        col.insertOne(new Document(new ObjectMapper().convertValue(pDTO, Map.class)));

        res = 1;

        log.info(this.getClass().getName() + "[몽고 매퍼] : insertDate 끝");

        return res;
    }
}