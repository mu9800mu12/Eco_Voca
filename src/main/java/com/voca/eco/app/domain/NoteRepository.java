package com.voca.eco.app.domain;

import com.voca.eco.app.domain.Entity.NoteEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, Long> {

    // 저장할 데이터 중복확인
    Optional<NoteEntity> findByUserIdAndWord(String userId, String word);

    List<NoteEntity> findByUserIdAndDate(String userId, String date);

    @Query("SELECT n FROM NoteEntity n WHERE n.userId = :userId GROUP BY DATE(n.date) ORDER BY n.date DESC")
    List<NoteEntity> findLatestByUserId(@Param("userId") String userId);

    List<NoteEntity> findByUserId(String userId);
}

