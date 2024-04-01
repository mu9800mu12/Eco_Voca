package com.voca.eco.app.service;


import com.voca.eco.app.dto.UserDTO;

public interface IUserService {

    //UserDTO getUserIDExists(UserDTO pDTO) throws Exception;

    void createUser (Long userSeq,
            final String userId,
            final String userName,
            final String userPassword,
            final String userEmail,
            final String userAddress,
            final String userBirthday,
            final String userNickname) throws Exception;

}
