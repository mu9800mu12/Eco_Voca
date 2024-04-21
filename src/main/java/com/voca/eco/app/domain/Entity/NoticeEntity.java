package com.voca.eco.app.domain.Entity;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
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
@Table(name = "NOTICE")
@DynamicInsert
@DynamicUpdate
@Cacheable
@Entity
public class NoticeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_seq", updatable = false, insertable = false, unique = true)
    private Long noticeSeq;

    @NonNull
    @Column(name = "title", length = 500, nullable = false)
    private String title;

    @NonNull
    @Column(name = "notice_yn", length = 1, nullable = false)
    private String noticeYn;

    @NonNull
    @Column(name = "contents", nullable = false)
    private String contents;

    @NonNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "read_cnt", nullable = false)
    private Long readCnt;

    @Column(name = "reg_id", updatable = false)
    private String regId;

    @Column(name = "reg_dt", updatable = false)
    private LocalDateTime regDt;

    @Column(name = "chg_id")
    private String chgId;

    @Column(name = "chg_dt")
    private LocalDateTime chgDt;

    @Builder
    public NoticeEntity(@NonNull String title, @NonNull String noticeYn, @NonNull String contents,
            @NonNull String userId,String regId, String chgId) {
        this.title = title;
        this.noticeYn = noticeYn;
        this.contents = contents;
        this.userId = userId;
        this.readCnt = 0L;
        this.regId = regId;
        this.chgId = chgId;
        this.chgDt = LocalDateTime.now();
        this.regDt = LocalDateTime.now();
    }

    public NoticeEntity updateNoticeInfo(
            final String title,
            final String contents,
            final String noticeYn) {
        this.title = title;
        this.contents = contents;
        this.noticeYn = noticeYn;
        this.chgDt = LocalDateTime.now();
        return this;
    }

    public NoticeEntity addReadCount() {
        this.readCnt++;

        return this;
    }
}