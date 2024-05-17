package com.voca.eco.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Getter
@Setter
public class ChatDTO {
    private String name; // 채팅 보내는 사람
    private String msg; // 채팅 메시지
    private String date; // 발송날짜
    private String roomName;
    private String roomHash;
}
