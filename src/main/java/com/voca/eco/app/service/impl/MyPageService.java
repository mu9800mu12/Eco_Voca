package com.voca.eco.app.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.voca.eco.app.domain.Entity.UserEntity;
import com.voca.eco.app.domain.UserRepository;
import com.voca.eco.app.dto.UserDTO;
import com.voca.eco.app.service.IMyPageService;
import com.voca.eco.common.util.CmmUtil;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("MyPageService")
@RequiredArgsConstructor
public class MyPageService implements IMyPageService {

    private final UserRepository userRepository;

    @Override
    public UserDTO myPageIndex(String userId) throws Exception {

    UserDTO rDTO = null;

    Optional<UserEntity> rEntity = userRepository.findByUserId(userId);

        rDTO = new ObjectMapper().convertValue(rEntity.get(),
                UserDTO.class);

        log.info(this.getClass().getName() + " rDTO 값이 잘 들어오는 확인하는 여기는 마이페이지 인덱스 보여주기" + rDTO);

        return rDTO;
    }
}
