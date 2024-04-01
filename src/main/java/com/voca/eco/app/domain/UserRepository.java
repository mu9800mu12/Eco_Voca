package com.voca.eco.app.domain;

import com.voca.eco.app.domain.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
