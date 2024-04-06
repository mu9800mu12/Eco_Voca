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
    public int NickNameExists(String nickName) throws Exception {
        Optional<UserEntity> rEntity = userRepository.findByNickName(nickName);

        log.info("nickName 이 잘 들어오는지 보는중이죠: " + nickName);

        //아이디가 있으면 1, 없으면 0
        int existsYn = rEntity.isPresent() ? 1 : 0;

        log.info("existsYn 이 잘 들어오는지 보는중이죠: " + existsYn);

        log.info(this.getClass().getName() + "NickNameExists End!");
        return existsYn;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public int UserIdExists(String userId) throws Exception {

        Optional<UserEntity> rEntity = userRepository.findByUserId(userId);

        log.info("userId : " + userId);

        //아이디가 있으면 1, 없으면 0
        int existsYn = rEntity.isPresent() ? 1 : 0;

        log.info(this.getClass().getName() + "userIdExists End!");
        return existsYn;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public int UserEmailExists(String email) throws Exception {
        Optional<UserEntity> rEntity = userRepository.findByEmail(email);

        log.info("email :" + email);

        /*
        여기에 이메일은 암호화 되어 있으니 암호화된 이메일 확인 절차가 필요함
        암호화 되어 있으니 중복 클릭을 눌러도 값이 없다고 값이 반환됨
         */

        //이메일이 있으면 1, 없으면 0
        int existsYn = rEntity.isPresent() ? 1 : 0;
        log.info(this.getClass().getName() + "userEmailExists End!");
        log.info(this.getClass().getName() + "이메일 서비스 yb 값임 " + existsYn);

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
        log.info(this.getClass().getName() + "userLogin");

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
        String userId = CmmUtil.nvl(pDTO.userId());

        log.info("pDTO" + pDTO);
        log.info("userId : " + userId);

        // 회원가입 중복 방지를 위해 DB에서 데이터 조회
        Optional<UserEntity> rEntity = userRepository.findByUserId(userId);
        log.info("rEntity가 잘들어오는지 보는 중입니다 :: " + rEntity);
        if(rEntity.isPresent()){
            res =2;
            log.info("유저 아이디가 존재하는 나는 res값이 2입니다");
        }

        log.info("유저엔터티 세이브 값을 엔터티로 저장합니다" );
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
            log.info("유저 엔터티 세이브 끝");


        if(save != null) { // 성공
            res = 1;
            log.info("res는 1임");
        } else {
            res= 0;
            log.info("res는 0임");
        }
        log.info(this.getClass().getName() + "회원가입 성공!");

        return res;
    }


    @Override
    @Transactional(rollbackOn = Exception.class)
    public int UserMailCheck(String Email) throws Exception {
        return 0;
    }



    }

 

