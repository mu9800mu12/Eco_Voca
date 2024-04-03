package com.voca.eco.app.domain;

import com.voca.eco.app.domain.Entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    // 회원 존재 여부 체크
    Optional<UserEntity> findByUserId(String userId);

    //로그인 시 아이디 비밀번호 조회
    Optional<UserEntity> findByUserIdAndPassword(String userId, String password);


}
