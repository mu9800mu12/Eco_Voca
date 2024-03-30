package com.voca.eco.app.service.impl;

import com.voca.eco.app.domain.User;
import com.voca.eco.app.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class IUserService {
    private final UserRepository userRepository;


}
