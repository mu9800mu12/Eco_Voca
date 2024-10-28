package com.voca.eco.app.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.voca.eco.app.domain.Entity.FileEntity;
import com.voca.eco.app.domain.FileRepository;
import com.voca.eco.app.dto.FileDTO;
import com.voca.eco.app.service.IFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileService implements IFileService {

    private final FileRepository fileRepository;
    /**
     * 파일 저장

     */
    @Override
    public void saveFiles(FileDTO pDTO) throws Exception {
        log.info(this.getClass().getName() +".saveFiles Start!");

        fileRepository.save(FileEntity.builder()
                .noticeSeq(pDTO.noticeSeq())
                .orgFileName(pDTO.orgFileName())
                .saveFilePath(pDTO.saveFilePath())
                .fileSize(pDTO.fileSize())
                .saveFileName(pDTO.saveFileName())
                .saveFileUrl(pDTO.saveFileUrl()).build());

        log.info(this.getClass().getName() +".saveFiles End!");
    }

    /**
     * 이미지 삭제
     */
    @Override
    public void deleteFiles(Long noticeSeq) throws Exception {
        log.info(this.getClass().getName() +".deleteFiles Start!");

        FileEntity fileDelete = fileRepository.findByNoticeSeq(noticeSeq)
                .orElse(null);

//        for (FileEntity file : filesDelete) {
//            fileRepository.delete(file); // 이미지가 여러개일 때 삭제
//        }

        if (fileDelete != null) {
            fileRepository.delete(fileDelete); // 단일 엔티티 삭제
        }

        log.info(this.getClass().getName() +".deleteFiles End!");
    }

    /**
     * 경로 가져오기
     */
    @Override
    public FileDTO getFilePath(Long noticeSeq) throws Exception {

        log.info(this.getClass().getName() +".getFilePath Start!");


        FileEntity pEntity = fileRepository.findByNoticeSeq(noticeSeq).orElse(null);
        log.info(this.getClass().getName() +".getFilePath End!");

        FileDTO rDTO = new ObjectMapper().convertValue(pEntity,
                new TypeReference<>() {
                });


        return rDTO;
    }
}