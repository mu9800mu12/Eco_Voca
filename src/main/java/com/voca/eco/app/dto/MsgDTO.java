package com.voca.eco.app.dto;

import lombok.Builder;

@Builder
public record MsgDTO(
        int result, // 성공 : 1 / 실패 : 그 외
        String msg, // 메시지
        int authNumber //인증번호

) {
}
