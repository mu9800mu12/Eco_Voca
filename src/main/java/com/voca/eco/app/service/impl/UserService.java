package com.voca.eco.app.service.impl;

import com.voca.eco.app.domain.UserRepository;
import com.voca.eco.app.dto.UserDTO;
import com.voca.eco.app.service.IUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;


    @Transactional(rollbackOn = Exception.class)
    public void createUser(final Long userSeq,
            final String userId,
            final String userName,
            final String userPassword,
            final String userEmail,
            final String userAddress,
            final String userBirthday,
            final String userNickname
    ) throws Exception{
        UserDTO.builder()
                .userSeq(1L).build();


    }

 
}
