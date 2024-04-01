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
import jakarta.validation.constraints.Null;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@Entity
@Table(name = "ECO_VOCA_USER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserEntity {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(updatable = false, insertable = false, unique = true)
    @Column(name = "USER_SEQ")
    private Long userSeq;

    @NonNull
    @Column
    private String userId;

    @NonNull
    @Column
    private String Password;

    @NonNull
    @Email
    @Column
    private String Email;

    @NonNull
    @Column
    private String userName;

    @NonNull
    @Column
    private String Nickname;

    @NonNull
    @Column
    private String Birthday;

    @NonNull
    @Column
    private String Address;

    @NonNull
    @Column
    private LocalDate createdAt;


    @Builder
    public UserEntity(String userId, String Password, String Email, String userName, String Nickname, String Birthday, String Address) {
        this.userSeq = userSeq;
        this.userId = userId;
        this.Password = Password;
        this.Email = Email;
        this.userName = userName;
        this.Nickname = Nickname;
        this.Birthday = Birthday;
        this.Address = Address;
        this.createdAt = LocalDate.now();
    }

    public void updateUserAddress(final String userAddress) {
        this.Address = Address;
    }
}
