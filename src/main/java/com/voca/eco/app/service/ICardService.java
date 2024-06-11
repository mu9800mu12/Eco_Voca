package com.voca.eco.app.service;

import java.io.IOException;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

public interface ICardService {


    Map<String, Object> processCardUpload(MultipartFile file);
}
