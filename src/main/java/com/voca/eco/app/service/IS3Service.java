package com.voca.eco.app.service;

import com.voca.eco.app.dto.FileDTO;
import org.springframework.web.multipart.MultipartFile;

public interface IS3Service {

    /**
     * 파일 업로드
     * @param mf
     * @param ext
     * @return
     * @throws Exception
     */
    FileDTO uploadFile(MultipartFile mf, String ext) throws Exception;

}

