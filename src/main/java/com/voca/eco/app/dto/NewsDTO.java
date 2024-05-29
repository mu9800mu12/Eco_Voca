package com.voca.eco.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.joda.time.DateTime;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record NewsDTO(

        String lastBuildDate,   // 검색 결과를 생성한 시간
        String total,           // 총 검색 결과 개수
        String start,           // 검색 시작 위치
        String display,         // 한 번에 표시할 검색 결과 개수
        List<item> items

) {

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class item {

        private String title;           // 뉴스 기사의 제목
        private String originallink;    // 뉴스 기사 원문 URL
        private String link;            // 네이버 뉴스 URL
        private String description;     // 뉴스 기사 내용 요약한 패시지 정보
        private DateTime pubData;       // 뉴스 기사가 네이버에 제공된 시간
    }

}
