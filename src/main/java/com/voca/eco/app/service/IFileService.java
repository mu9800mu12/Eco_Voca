package com.voca.eco.app.service;


import com.voca.eco.app.dto.FileDTO;
import java.util.List;

public interface IFileService {

    /**
     * 파일 저장
     */
    void saveFiles(FileDTO pDTO) throws Exception;

    /**
     * 이미지 삭제
     */
    void deleteFiles(Long nSeq) throws Exception;


    /**
     * 경로 가져오기
     */
    FileDTO getFilePath(final Long nSeq) throws Exception;

}