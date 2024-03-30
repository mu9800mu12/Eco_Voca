package com.voca.eco.app.domain.repository;

import com.voca.eco.app.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
