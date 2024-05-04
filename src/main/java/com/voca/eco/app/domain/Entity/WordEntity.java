package com.voca.eco.app.domain.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ECO_WORD")
@DynamicInsert
@DynamicUpdate
@Entity
public class WordEntity {

    @Id
    @Column(name = "WORD_SEQ")
    private String wordSeq;

    @NonNull
    @Column(name = "WORD_TITLE")
    private String wordTitle;

    @NonNull
    @Column(name = "WORD_CONTENTS")
    private String wordContents;
}