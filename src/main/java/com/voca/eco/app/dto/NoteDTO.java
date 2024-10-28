package com.voca.eco.app.dto;

import lombok.Builder;

@Builder
public record NoteDTO(
        Long noteSeq,
        String userId,
        String word,
        String contents,
        String date
) {
}
