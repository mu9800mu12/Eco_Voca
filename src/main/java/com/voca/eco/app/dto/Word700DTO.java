package com.voca.eco.app.dto;

import lombok.Builder;

@Builder
public record Word700DTO(

        Long wordSeq,
        String word,
        String contents

) {
}
