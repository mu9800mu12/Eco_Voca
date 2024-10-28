package com.voca.eco.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Builder;
import lombok.Data;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record NaverDTO(

        ResponseDTO response,
        LinkedHashMap<String, Object> additionalProperties

) {

    @Data
    public static class ResponseDTO {
        private String id;
        private String name;
        private String nickname;
        private String gender;
        private String email;
        private String mobile;
        private String mobile_e164;
        private String birthday;
        private String birthyear;

    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
