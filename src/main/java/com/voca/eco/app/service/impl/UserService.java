package com.voca.eco.app.service.impl;

import com.voca.eco.app.domain.Entity.UserEntity;
import com.voca.eco.app.domain.UserRepository;
import com.voca.eco.app.dto.UserDTO;
import com.voca.eco.app.service.IUserService;
import com.voca.eco.common.util.CmmUtil;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

@Slf4j
@Service("UserService")
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;



    @Override
    @Transactional(rollbackOn = Exception.class)
    public int UserIdExists(String userId) throws Exception {

        Optional<UserEntity> rEntity = userRepository.findByUserId(userId);

        log.info("userId" + userId);

        //아이디가 있으면 1, 없으면 0
        int existsYn = rEntity.isPresent() ? 1 : 0;

        log.info(this.getClass().getName() + "userIdExists End!");
        return existsYn;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public int UserEmailExists(String email) throws Exception {
        Optional<UserEntity> rEntity = userRepository.findByEmail(email);

        log.info("email" + email);

        //이메일이 있으면 1, 없으면 0
        int existsYn = rEntity.isPresent() ? 1 : 0;

        log.info(this.getClass().getName() + "userEmailExists End!");

        return existsYn;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public UserDTO FindByUserId(
            String userId,
            String userName)
            throws Exception {

        return null;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public UserDTO FindByPassword(
            String userId,
            String password,
            String userName)
            throws Exception {

        return null;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public int UserLogin(String userId, String password) throws Exception {

        int res =0;
        Optional<UserEntity> rEntity = userRepository.findByUserIdAndPassword(CmmUtil.nvl(userId), CmmUtil.nvl(password));

        if (rEntity.isPresent()) {
            res=1;
        }
        log.debug(this.getClass().getName() + "userLogin");

        return res;
    }


    @Override
    @Transactional(rollbackOn = Exception.class)
    public int updatePassword(String userId, String password) throws Exception {

        return 0;
    }
    @Override
    @Transactional(rollbackOn = Exception.class)
    public int createUser(UserDTO pDTO) throws Exception{
        // 회원 가입 성공 : 1, 중복으로 인한 취소 : 2, 기타 에러 : 0
        int res = 0;
        log.debug("pDTO" +pDTO);
        String userId = CmmUtil.nvl(pDTO.userId());
        log.debug("userId" + userId);
        //DTO로 받아서 int로 형변환 해야 함

        // 회원가입 중복 방지를 위해 DB에서 데이터 조회
        Optional<UserEntity> rEntity = userRepository.findByUserId(userId);
        log.debug("rEntity1" + rEntity);
        if(rEntity.isPresent()){
            res =2;
            log.debug("res값은 2이입니다");
        }

        log.debug("유저엔터티 세이브 값을 엔터티로 저장합니다" );
        UserEntity save = userRepository.save(UserEntity.builder()
                .userId(CmmUtil.nvl(pDTO.userId()))
                .userName(CmmUtil.nvl(pDTO.userName()))
                .password(CmmUtil.nvl(pDTO.password()))
                .email(CmmUtil.nvl(pDTO.email()))
                .userName(CmmUtil.nvl(pDTO.userName()))
                .nickName(CmmUtil.nvl(pDTO.nickName()))
                .birthday(CmmUtil.nvl(pDTO.birthday()))
                .address(CmmUtil.nvl(pDTO.address()))
                .build());
            log.debug("유저 엔터티 세이브 끝");


        if(save != null) { // 성공
            res = 1;
            log.debug("res는 1임");
        } else {
            res= 0;
            log.debug("res는 0임");
        }
        log.debug(this.getClass().getName() + "회원가입 성공!");

        return res;
    }


    @Override
    @Transactional(rollbackOn = Exception.class)
    public int UserMailCheck(String Email) throws Exception {
        return 0;
    }



    }

 

