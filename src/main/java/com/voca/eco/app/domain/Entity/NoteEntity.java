package com.voca.eco.app.domain.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;


@Getter
@Entity
@Table(name = "NOTE")
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOTE_SEQ", updatable = false, insertable = false, unique = true)
    private Long noteSeq;

    @Column(name = "USER_ID")
    private String userId;


    @Column(name = "WORD")
    private String word;

    @Column(name = "CONTENTS", length = 2500)
    private String contents;

    @Column(name = "DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String date;
}
