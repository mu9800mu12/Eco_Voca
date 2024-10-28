package com.voca.eco.app.dto;

import lombok.Builder;

@Builder
public record FileDTO(
        Long fileSeq,           // 저장 순번

//        Long boardSeq,        // 커뮤 번호

        Long noticeSeq,         // 게시글 번호
        String saveFileName,    // 저장된 파일명
        String saveFilePath,    // 저장된 파일 경로
        String orgFileName,     // 원본 파일명
        String regDt,           // 작성일
        String fileSize,        // 파일 크기
        int page,   // 이미지 장수
        String saveFileUrl     // 저장 경로


) {

}
