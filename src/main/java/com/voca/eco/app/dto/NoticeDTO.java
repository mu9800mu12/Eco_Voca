package com.voca.eco.app.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record NoticeDTO(

        Long noticeSeq, // 기본키, 순번
        String title, // 제목
        String noticeYn, // 공지글 여부
        String contents, // 글 내용
        String userId, // 작성자
        Long readCnt, // 조회수
        String regId, // 등록자 아이디
//        @JsonSerialize(using = LocalDateSerializer.class)
//        @JsonDeserialize(using = LocalDateDeserializer.class)
        String regDt, // 등록일
        String chgId, // 수정자 아이디
//        @JsonSerialize(using = LocalDateSerializer.class)
//        @JsonDeserialize(using = LocalDateDeserializer.class)
        String chgDt, // 수정일
        String userName // 등록자명
) {

}