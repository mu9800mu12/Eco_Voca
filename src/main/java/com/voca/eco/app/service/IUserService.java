package com.voca.eco.app.service;


import com.voca.eco.app.dto.UserDTO;

public interface IUserService {


    // 회원가입
    int createUser (UserDTO pDTO) throws Exception;

    // 아이디 중복 확인
    int UserIdExists(final String userId) throws Exception;

    // 이메일 중복 확인
    int UserEmailExists(final String email) throws Exception;

    // 닉네임 중복 확인
    int NickNameExists(final String nickName) throws Exception;


    // 아이디 찾기
    UserDTO FindByUserId(final String userId,
                         final String userName) throws Exception;

    // 비밀번호 찾기
    UserDTO FindByPassword(final String userId,
                           final String password,
                           final String userName) throws Exception;

    // 이메일 인증

    int UserMailCheck (final String Email) throws Exception;

    // 로그인
    int UserLogin(final String userId,
            final String password) throws Exception;


    // 비밀번호 변경
    int updatePassword(final String userId,
                        final String password) throws Exception;

    // 회원 삭제



}
