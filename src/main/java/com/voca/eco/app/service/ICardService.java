package com.voca.eco.app.service;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface ICardService {

    String extractCardInfo(MultipartFile file) throws IOException;

}
