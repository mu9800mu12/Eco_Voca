package com.voca.eco.app.domain;

import com.voca.eco.app.domain.Entity.UserEntity;
import com.voca.eco.app.domain.Entity.WordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordRepository extends JpaRepository<WordEntity, Integer> {

}
