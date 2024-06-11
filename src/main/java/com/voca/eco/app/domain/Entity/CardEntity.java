package com.voca.eco.app.domain.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;

@Getter
@Entity
@Table(name = "CARD_PAYMENT_INFO")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@DynamicInsert
@DynamicUpdate
public class CardEntity {

    @Id
    @Column(name = "CARD_NUMBER", nullable = false)
    private String cardNumber; // 카드 번호

    @Column(name = "USER_ID")
    private String userId; // todo 세션에 있는 아이디 값 넣자

    @Column(name = "EXPIRATION_DATE", nullable = false, length = 5)
    private String expirationDate; // 유효 기간

    @Column(name = "CVC", nullable = false, length = 4)
    private String cvc; // CVC 보안 코드

    @Column(name = "CARDHOLDER_NAME", nullable = false, length = 100)
    // todo 세션에 있는 값에서 닉네임을 받아서 넣어야 겠다.
    private String cardholderName; // 카드 소유자 이름

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private Date createdAt; // 생성 일자

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_AT", nullable = false)
    private Date updatedAt; // 수정 일자

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

    // 사용자 ID와 이름 설정 메서드
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    // 기타 필요한 메서드 추가 가능
}
