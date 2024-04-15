package com.voca.eco.app.service;

import com.voca.eco.app.dto.UserDTO;

public interface IMyPageService {

    UserDTO myPageIndex(String userId) throws Exception;

}
