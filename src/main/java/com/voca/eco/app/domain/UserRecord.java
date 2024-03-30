package com.voca.eco.app.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "ECO_VOCA_USER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserRecord {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(updatable = false, insertable = false, unique = true)
    private Long userSeq;

    @Column
    private String userId;

    @Column
    private String userPassword;

    @Email
    @Column
    private String userEmail;

    @Column
    private String userName;

    @Column
    private String userNickname;

    @Column
    private String userBirthday;

    @Column
    private String userAddress;

    @Column
    private LocalDate createdAt;


    @Builder
    public UserRecord(String userId, String userPassword, String userEmail, String userName, String userNickname, String userBirthday, String userAddress) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userNickname = userNickname;
        this.userBirthday = userBirthday;
        this.userAddress = userAddress;
        this.createdAt = LocalDate.now();
    }

    public void updateUserAddress(final String userAddress) {
        this.userAddress = userAddress;
    }
}
