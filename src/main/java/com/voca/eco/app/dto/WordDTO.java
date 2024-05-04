package com.voca.eco.app.dto;

import lombok.Builder;

@Builder
public record WordDTO(
        int wordSeq,
        String wordTitle,
        String wordContents


) {

}
