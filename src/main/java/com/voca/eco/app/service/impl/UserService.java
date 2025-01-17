package com.voca.eco.app.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.voca.eco.app.domain.Entity.UserEntity;
import com.voca.eco.app.domain.UserRepository;
import com.voca.eco.app.dto.UserDTO;
import com.voca.eco.app.service.IUserService;
import com.voca.eco.common.util.CmmUtil;
import com.voca.eco.common.util.EncryptUtil;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service("UserService")
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;


    /*
     * 닉네임 중복확인
     */
    @Override
    public int nickNameExists(String nickName) throws Exception {

        log.info("입력받은 닉네임 값은 : " + nickName );

        Optional<UserEntity> rEntity = userRepository.findByNickName(nickName);
        log.info("엔터티 값은 : " + rEntity );

        int existsYn = rEntity.isPresent() ? 1 : 0;
        log.info(this.getClass().getName() + "닉네임이 중복이면 1 아니면 0인 나는 : " + existsYn );
        log.info(this.getClass().getName() + "NickNameExists End!");
        return existsYn;
    }

    /*
     * 아이디 중복확인
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public int userIdExists(String userId) throws Exception {
        Optional<UserEntity> rEntity = userRepository.findByUserId(userId);
        log.info("userId : " + userId);
        int existsYn = rEntity.isPresent() ? 1 : 0;
        log.info(this.getClass().getName() + "userIdExists End!");
        return existsYn;
    }
    /*
     * 이메일 중복확인
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public int userEmailExists(String email) throws Exception {
        Optional<UserEntity> rEntity = userRepository.findByEmail(email);
        log.info("email :" + email);
        /*
        여기에 이메일은 암호화 되어 있으니 암호화된 이메일 확인 절차가 필요함
        암호화 되어 있으니 중복 클릭을 눌러도 값이 없다고 값이 반환됨
         */
        int existsYn = rEntity.isPresent() ? 1 : 0;
        log.info(this.getClass().getName() + "userEmailExists End!");

        return existsYn;
    }

    /*
     * 아이디 찾기
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public UserDTO getUserId(
            String userName,
            String email) throws Exception {

        UserDTO rDTO = null;

        log.info(this.getClass().getName() + "아이디 찾기 서비스 시작");

        Optional<UserEntity> rEntity = userRepository.findByEmailAndUserName(email, userName);
        log.info("rEntity : " + rEntity);

        if (rEntity.isPresent()){
        rDTO = new ObjectMapper().convertValue(rEntity.get(),
                UserDTO.class);

            log.info(this.getClass().getName() + "나는 날아가는 r dTO 서비스 입니다" + rDTO);

        } else {
            rDTO = UserDTO.builder().build();

            log.info(this.getClass().getName() + "나는 날아가는 r dTO 서비스 입니다" + rDTO);
        }

        return rDTO;
    }
    /*
     * 비밀번호 찾기
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public UserDTO getUserPassword(
            String nickName,
            String email,
            String userId) throws Exception {

        UserDTO rDTO = null;

        log.info(this.getClass().getName() + "비밀번호 찾기 서비스 시작");


        log.info(nickName);
        log.info(email);
        log.info(userId);


        Optional<UserEntity> rEntity = userRepository.findByNickNameAndEmailAndUserId(nickName, email, userId);

        log.info("rEntity : " + rEntity);

        if (rEntity.isPresent()){
            rDTO = new ObjectMapper().convertValue(rEntity.get(),
                    UserDTO.class);

            log.info(this.getClass().getName() + "나는 잠시동안만 현생에 머무는 r dTO 서비스 입니다" + rDTO);

        } else {
            rDTO = UserDTO.builder().build();

            log.info(this.getClass().getName() + "나는 잠시동안만 현생에 머무는 r dTO 서비스 입니다" + rDTO);
        }

        return rDTO;
    }
    /*
     * 로그인 로직
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public int userLogin(String userId, String password)throws Exception {
        int res =0;
        Optional<UserEntity> rEntity = userRepository.findByUserIdAndPassword(CmmUtil.nvl(userId), CmmUtil.nvl(password));
        if (rEntity.isPresent()) {
            res=1;
        }
        log.info(this.getClass().getName() + "userLogin");
        return res;
    }
    /*
     * 비밀번호 찾기 및 수정 로직
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public void updatePassword(String userId,
                                String password) throws Exception {
        log.info(this.getClass().getName() + ".newPasswordProc Start! 시~작 합니다~~!");

            userRepository.updatePassword(userId, password);
//
//        Optional<UserEntity> pEntity = userRepository.findByUserId(userId);
//
//        UserEntity rEntity = UserEntity.builder()
//                .userId(userId)
//                .password(password)
//                .build();
//
//        userRepository.save(rEntity);

        log.info(this.getClass().getName() + ".newPasswordProc End! 끝입니다.");

    }
    /*
     * 회원가입 로직
     */
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
                .email(CmmUtil.nvl(EncryptUtil.encAES128CBC(pDTO.email())))
//                .email(CmmUtil.nvl((pDTO.email())))
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

    // 내 정보 수정하기
    @Override
    public void updateMyPage(String userId, String nickName, String address)
            throws Exception {

        log.info(this.getClass().getName() + "내 정보 수정 시작!");

        Optional<UserEntity> pEntity = userRepository.findByUserId(userId);

        userRepository.save(UserEntity.builder()
                .userId(userId)
                .email(pEntity.get().getEmail())
                .nickName(nickName)
                .address(address)
                .userName(pEntity.get().getUserName())
                .password(pEntity.get().getPassword())
                .birthday(pEntity.get().getBirthday())
                .build());

        log.info(this.getClass().getName() + "내 정보 수정 끝!");

    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public UserDTO myPageIndex(String userId) throws Exception {

        log.info(" 마이페이지 보고 시이이이이이잉작입니다~~~!!!");

        Optional<UserEntity> rEntity = userRepository.findByUserId(userId);

        UserDTO rDTO = UserDTO.builder()
                .email(EncryptUtil.decAES128CBC(rEntity.get().getEmail()))
                .userId(userId)
                .nickName(rEntity.get().getNickName())
                .userName(rEntity.get().getUserName())
                .address(rEntity.get().getAddress())
                .sinceDay(rEntity.get().getSinceDay())
                .birthday(rEntity.get().getBirthday())
                .password(rEntity.get().getPassword())
                .build();



//       UserDTO rDTO = new ObjectMapper().convertValue(rEntity.get(),
//                UserDTO.class);
//
        log.info(this.getClass().getName() + " rDTO 값이 잘 들어오는 확인하는 여기는 마이페이지 인덱스 보여주기" + rDTO);

        return rDTO;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void userDelete(String userId) throws Exception {

        log.info(" 나는 회원을 탈퇴하는 서비스 입니다");

        userRepository.deleteByUserId(userId);

        log.info(" 나는 회원을 탈퇴하는 서비스 입니다 끝입니다 !!!!!!!!!!!");

    }
}

 

