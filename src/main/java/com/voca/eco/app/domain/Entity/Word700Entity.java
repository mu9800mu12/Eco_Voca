package com.voca.eco.app.domain.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "WORD700")
@DynamicInsert
@DynamicUpdate
@Entity
public class Word700Entity {

    @Id
    @Column(name = "WORD_SEQ")
    private Long wordSeq;

    @Column(name = "WORD")
    private String word;

    @Column(name = "CONTENTS", length = 4000)
    private String contents;
}
