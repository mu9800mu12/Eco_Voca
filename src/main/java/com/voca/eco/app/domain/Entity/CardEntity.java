package com.voca.eco.app.domain.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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
    @Column(name = "CARD_NUMBER", nullable = false, length = 16)
    private String cardNumber; //카드 번호

    @Column(name = "USER_ID")
    private String userId; //todo 세션에 있는 아이디 값 넣자

    @Column(name = "EXPIRATION_DATE", nullable = false, length = 5)
    private String expirationDate; //유효 기간

    @Column(name = "SECURITY_CODE", nullable = false, length = 4)
    private String securityCode; //CVC 보안 코드

    @Column(name = "CARDHOLDER_NAME", nullable = false, length = 100)
    //todo 세션에 있는 값에서 닉네임을 받아서 넣어야 겠다.
    private String cardholderName; // 카드 소유자 이름
}

//    @Column(name = "BILLING_ADDRESS", nullable = false, length = 255)
//    private String billingAddress; //청구지 주소
//    //카드 소유자의 신원을 확인하고,
//    // 주소 확인 시스템(Address Verification System, AVS)을 통해 추가적인 보안 검사를 수행하기 위해 필요합니다.
//    // 이는 특히 온라인 거래에서 사기 방지에 중요한 역할을 합니다.
//
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "CREATED_AT", nullable = false, updatable = false)
//    private Date createdAt; //생성 일자
//    //데이터가 처음 생성된 시점을 기록하기 위해 필요합니다.
//    // 이를 통해 데이터의 생성 시점을 추적할 수 있습니다.
//
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "UPDATED_AT", nullable = false)
//    private Date updatedAt; // 수정 일자
//
////    @Column(name = "IS_ACTIVE", nullable = false)
////    private boolean isActive; // 활성화 여부

//    @PrePersist
//    protected void onCreate() {
//        createdAt = new Date();
//        updatedAt = new Date();
//    }
//
//    @PreUpdate
//    protected void onUpdate() {
//        updatedAt = new Date();
//    }
//}
