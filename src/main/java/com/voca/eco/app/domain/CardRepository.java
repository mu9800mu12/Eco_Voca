package com.voca.eco.app.domain;

import com.voca.eco.app.domain.Entity.CardEntity;
import com.voca.eco.app.domain.Entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<CardEntity, String> {

}
