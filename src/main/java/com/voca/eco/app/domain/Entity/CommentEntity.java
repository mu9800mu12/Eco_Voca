package com.voca.eco.app.domain.Entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "COMMENTS")
@DynamicUpdate
@DynamicInsert
@Entity
@Builder
public class CommentEntity {

    @Id
    @Column(name = "COMMENT_SEQ")
    private Long commentSeq;

    @NonNull
    @Column(name = "USER_ID")
    private String userId;

    @NonNull
    @Column(name = "comment")
    private String comment;
    @NonNull
    @Column(name = "regDt")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime regDt;

    public CommentEntity updateComment(
            final String comment){
        this.comment = comment;
        return this;
    }


}
