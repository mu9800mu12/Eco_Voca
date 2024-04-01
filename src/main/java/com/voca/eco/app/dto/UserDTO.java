package com.voca.eco.app.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public record UserDTO( Long userSeq,
                       String userName,
                       String userPassword,
                       String userEmail,
                       String userAddress,
                       String userBirthday,
                       String userNickname) {

}
