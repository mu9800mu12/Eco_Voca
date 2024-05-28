package com.voca.eco.app.domain;

import com.voca.eco.app.domain.Entity.Word700Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Word700Repository extends JpaRepository<Word700Entity, Long> {
}
