package com.voca.eco.app.domain.Entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "COMMENT")
@DynamicUpdate
@DynamicInsert
@Entity
@Builder
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_SEQ")
    private Long commentSeq;

    @NonNull
    @Column(name = "NOTICE_SEQ")
    private Long noticeSeq;

    @NonNull
    @Column(name = "USER_ID")
    private String userId;


    @NonNull
    @Column(name = "COMMENT")
    private String comment;

    @NonNull
    @Column(name = "REG_DT")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class) //직렬화 역직렬화
    private LocalDateTime regDt;

    public CommentEntity updateComment(
            final String comment) {
        this.comment = comment;
        return this;
    }

    public CommentEntity insertRegDt(
            final String regDt) {
        this.regDt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        return this;
    }
}
