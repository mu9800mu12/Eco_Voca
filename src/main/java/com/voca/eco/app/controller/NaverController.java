package com.voca.eco.app.controller;

import com.voca.eco.app.dto.NaverDTO;
import com.voca.eco.app.dto.TokenDTO;
import com.voca.eco.app.dto.UserDTO;
import com.voca.eco.app.service.INaverService;
import com.voca.eco.app.service.IUserService;
import com.voca.eco.common.util.EncryptUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class NaverController {

    private final INaverService naverService;

    private final IUserService userService;

    @GetMapping(value = "/auth/naver/callback")
    public String naverCallback(String code, HttpSession session) throws Exception {

        log.info(".controller 네이버 회원가입 및 로그인 실행");

        int res; // 회원 가입 결과 /// 1 성공, 2 이미 가입

        TokenDTO tokenDTO = naverService.getAccessToken(code);

        log.info("네이버 엑세스 토큰 : " + tokenDTO.access_token());

        // 탈퇴할 때 피룡한 토큰 세션에 저장
        session.setAttribute("token", tokenDTO.access_token());

        NaverDTO naverDTO = naverService.getNaverUserInfo(tokenDTO);

        String userId = "naver_" + naverDTO.response().getId();
        String userName = naverDTO.response().getName();
        String nickname = naverDTO.response().getNickname();
        String birthday = naverDTO.response().getBirthday();
        String birthYear = naverDTO.response().getBirthyear(); // 태어난 연도 추가

        log.info("네이버 아이디 : " + naverDTO.response().getId());

        // 생일과 태어난 연도를 합쳐서 저장 (예: 1990-05-15)
        String fullBirthday = birthYear + "-" + birthday;

        // 첫 로그인시 회원가입 로직 실행
        if (userService.userIdExists(userId) == 0) {

            String email = naverDTO.response().getEmail();

            log.info("네이버 아이디(문자) : " + userId);
            log.info("네이버 이름 : " + userName);
            log.info("네이버 닉네임 : " + nickname);
            log.info("네이버 이메일 : " + email);
            log.info("합쳐진 생일 : " + fullBirthday);

            UserDTO pDTO = UserDTO.builder()
                    .userId(userId)
                    .userName(userName)
                    .nickName(nickname)
                    .password(EncryptUtil.encHashSHA256(userId))
                    .email(email)
                    .birthday(fullBirthday) // 합쳐진 생일 저장
                    .build();

            res = userService.createUser(pDTO);

            if (res == 1) {
                log.info("회원가입 성공");

                session.setAttribute("SS_USER_ID", userId);

            } else {
                log.info("회원가입 실패");

                return "user/login";

            }

        } else {
            log.info("계정 보유로 로그인 실행");

            session.setAttribute("SS_USER_ID", userId);

        }

        log.info(".controller 네이버 회원가입 및 로그인 종료");

        return "redirect:/main";
    }
}
