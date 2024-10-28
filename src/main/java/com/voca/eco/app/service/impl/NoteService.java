package com.voca.eco.app.service.impl;

import com.voca.eco.app.domain.Entity.NoteEntity;
import com.voca.eco.app.domain.NoteRepository;
import com.voca.eco.app.dto.NoteDTO;
import com.voca.eco.app.service.INoteService;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class NoteService implements INoteService {

    private final NoteRepository noteRepository;

    public List<NoteDTO> getNoteList(String userId, String date) {

        List<NoteEntity> noteEntities = noteRepository.findByUserIdAndDate(userId, date);

        return noteEntities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public List<NoteDTO> getNoteAll(String userId) {

        List<NoteEntity> noteEntities = noteRepository.findByUserId(userId);

        return noteEntities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public List<NoteDTO> getNote(String userId) {

        List<NoteEntity> noteEntities = noteRepository.findLatestByUserId(userId);
        return noteEntities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertWord(NoteDTO pDTO) throws Exception {
        // 중복 확인: userId와 word가 모두 일치하는 데이터가 있는지 확인
        Optional<NoteEntity> existingNote = noteRepository.findByUserIdAndWord(pDTO.userId(), pDTO.word());

        // 중복된 데이터가 없다면 새로 저장
        if (existingNote.isEmpty()) {
            noteRepository.save(
                    NoteEntity.builder()
                            .userId(pDTO.userId())
                            .word(pDTO.word())
                            .contents(pDTO.contents())
                            .date(String.valueOf(LocalDate.now()))  // 현재 날짜 삽입
                            .build()
            );
        } else {
            // 중복된 경우 처리 (필요 시 로그나 예외 처리)
            System.out.println("중복된 데이터: 저장하지 않음");
        }
    }
    private NoteDTO convertToDTO(NoteEntity entity) {
        NoteDTO dto = NoteDTO.builder(
        ).userId(entity.getUserId()
        ).word(entity.getWord()
        ).date(String.valueOf(entity.getDate())
        ).contents(entity.getContents()
        ).noteSeq(entity.getNoteSeq()).build();

        return dto;
    }
}
