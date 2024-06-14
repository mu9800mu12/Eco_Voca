package com.voca.eco.app.domain;

import com.voca.eco.app.domain.Entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    // 회원 존재 여부 체크
    Optional<UserEntity> findByUserId(String userId);

    // 이메일 존재 여부 체크
    Optional<UserEntity> findByEmail(String email);
    // 닉네임 존재 여부 체크
    Optional<UserEntity> findByNickName(String nickName);

    //로그인 시 아이디 비밀번호 조회
    Optional<UserEntity> findByUserIdAndPassword(String userId, String password);

    Optional<UserEntity> findAllByUserId(String userId);

    // 아이디 찾기
    Optional<UserEntity> findByEmailAndUserName(String email, String userName);
    // 비밀번호 찾기
    Optional<UserEntity> findByNickNameAndEmailAndUserId(String nickName, String email, String userId);

    // 비밀번호 업데이트
    @Modifying
    @Query("UPDATE UserEntity u SET u.password = :newPassword WHERE u.userId = :userId")
    void updatePassword(@Param("userId") String userId, @Param("newPassword") String newPassword);

    // 회원탈퇴하기
    void deleteByUserId(String userId);


}
