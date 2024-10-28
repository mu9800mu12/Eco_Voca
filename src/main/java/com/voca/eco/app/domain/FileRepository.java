package com.voca.eco.app.domain;



import com.voca.eco.app.domain.Entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {

    Optional<FileEntity> findByNoticeSeq(Long noticeSeq);
}