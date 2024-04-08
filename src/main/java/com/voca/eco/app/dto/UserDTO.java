package com.voca.eco.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails.Address;
import org.springframework.cglib.core.Local;

@Builder
@JsonInclude(Include.NON_DEFAULT)
public record UserDTO(String userId,
                      String password,
                      String email,
                      String userName,
                      String nickName,
                      String birthday,
                      String address,
                      LocalDate sinceDay,
                      String existsYn,
                      String regId, //등록자
                      String regDt,
                      String chgId, //수정자
                      String chgDt,

                      int authNumber
                      ) {

}
