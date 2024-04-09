package com.voca.eco.app.domain.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Entity
@Table(name = "USER_INFO")
@NoArgsConstructor
        //(access = AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
public class UserEntity {

    @Id
    @Column(name = "USER_ID")
    private String userId;

    @NonNull
    @Column(name = "PASSWORD", length = 128, nullable = false)
    private String password;

    @NonNull
    @Column(name = "EMAIL", nullable = false)
    private String email;

    @NonNull
    @Column(name = "USER_NAME", length = 500, nullable = false)
    private String userName;

    @NonNull
    @Column(name = "NICKNAME", length = 500, nullable = false)
    private String nickName;

    @NonNull
    @Column(name = "BIRTHDAY", nullable = false)
    private String birthday;

    @NonNull
    @Column(name = "ADDRESS", nullable = false)
    private String address;

    @NonNull
    @Column(name = "SINCE_DAY")
    private LocalDate sinceDay;


    @Builder
    public UserEntity(String userId, String password, String email, String userName,
            String nickName, String birthday, String address) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.userName = userName;
        this.nickName = nickName;
        this.birthday = birthday;
        this.address = address;
        this.sinceDay = LocalDate.now();

    }


    public void updateUserAddress(final String address) {
        this.address = address;
    }
}
