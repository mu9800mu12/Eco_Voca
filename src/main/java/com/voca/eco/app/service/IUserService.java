package com.voca.eco.app.service;


import com.voca.eco.app.dto.UserDTO;

public interface IUserService {


    // 회원가입
    int createUser (UserDTO pDTO) throws Exception;

    // 아이디 중복 확인
    int userIdExists(final String userId) throws Exception;

    // 이메일 중복 확인
    int userEmailExists(final String email) throws Exception;

    // 닉네임 중복 확인
    int nickNameExists(final String nickName) throws Exception;


    // 아이디 찾기
    UserDTO getUserId(final String userName,
                         final String email) throws Exception;

    // 비밀번호 찾기
    UserDTO getUserPassword(final String userName,
                            final String email,
                            final String userId) throws Exception;

    // 이메일 인증


    // 로그인
    int userLogin(final String userId,
            final String password) throws Exception;


    // 비밀번호 변경
    void updatePassword(final String password) throws Exception;

    // 회원 삭제



}
