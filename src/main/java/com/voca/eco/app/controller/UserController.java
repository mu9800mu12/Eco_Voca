package com.voca.eco.app.controller;

import com.voca.eco.app.dto.MsgDTO;
import com.voca.eco.app.dto.UserDTO;
import com.voca.eco.app.service.IUserService;
import com.voca.eco.common.util.CmmUtil;
import com.voca.eco.common.util.EncryptUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@RequestMapping(value = "/user")
@RequiredArgsConstructor
@Controller
public class UserController {

    private final IUserService userService;

    @GetMapping(value = "index")
    public String index() {

        log.info(this.getClass().getName() + "index Start And End!");

        return "html/index";
    }

    @GetMapping(value = "userRegForm")
    public String userRegForm() {

        log.info(this.getClass().getName() + "user/RegForm Start and End");

        return "user/userRegForm";

    }

    @ResponseBody
    @PostMapping(value = "UserIdExists")
    public int UserIdExists(HttpServletRequest request) throws Exception {

        String userId = CmmUtil.nvl(request.getParameter("userId"));
        log.debug("userId" + userId);

        //UserDTO pDTO = UserDTO.builder().userId(userId).build();

        int existsYn = Optional.ofNullable(userService.UserIdExists(userId))
                .orElse(2);

        return existsYn;

    }
    @ResponseBody
    @PostMapping(value = "UserEmailExists")
    public int UserEmailExists(HttpServletRequest request) throws Exception {

        String email = CmmUtil.nvl(request.getParameter("email"));
        log.debug("email" + email);

        //UserDTO pDTO = UserDTO.builder().userId(userId).build();

        int existsYn = Optional.ofNullable(userService.UserEmailExists(email))
                .orElse(2);

        return existsYn;

    }

    @ResponseBody
    @PostMapping(value = "createUser")
    public MsgDTO createUser(HttpServletRequest request) throws Exception {

        log.debug(this.getClass().getName() + "createUser Start!");

        String msg;

        String userId = CmmUtil.nvl(request.getParameter("userId"));
        String userName = CmmUtil.nvl(request.getParameter("userName"));
        String password = CmmUtil.nvl(request.getParameter("password"));
        String email = CmmUtil.nvl(request.getParameter("email"));
        String nickName = CmmUtil.nvl(request.getParameter("nickName"));
        String birthday = CmmUtil.nvl(request.getParameter("birthday"));
        String address = CmmUtil.nvl(request.getParameter("address"));

        log.info(" userId :" + userId);
        log.info(" userName:" + userName);
        log.info(" password:" + password);
        log.info(" email:" + email);
        log.info(" userName:" + userName);
        log.info(" nickName:" + nickName);
        log.info(" birthday:" + birthday);
        log.info(" address:" + address);

        UserDTO pDTO = UserDTO.builder()
                .userId(userId)
                .userName(userName)
                .password(EncryptUtil.encHashSHA256(password))
                .email(EncryptUtil.encAES128CBC(email))
                .userName(userName)
                .nickName(nickName)
                .birthday(birthday)
                .build();
        log.debug(this.getClass().getName()+ " 서비스로 넘어가기 전에");
        int res = userService.createUser(pDTO);

        log.debug("회원가입 결과(res) :" + res);

        if (res == 1) {
            msg = "회원가입 되었습니다.";

            //추후 회원가입 입력화면에서 ajax를 활용해서 아이디 중복, 이메일 중복을 체크하길 바람

        } else if (res == 2) {
            msg = "이미 가입된 아이디입니다.";

        } else {
            msg = "오류로 인해 회원가입이 실패하였습니다";

        }

        //결과 메시지 전달하기
        MsgDTO dto = MsgDTO.builder().result(res).msg(msg).build();

        log.info(this.getClass().getName() + "insertUserInfo End!");

        return dto;
    }


    @GetMapping(value = "login")
    public String login() {

        log.info(this.getClass().getName() + "user/login Start And End!");

        return "user/login";
    }

    @ResponseBody

    @PostMapping(value = "loginProc")
    public MsgDTO loginProc(HttpServletRequest request, HttpSession session) throws Exception {

        log.info(this.getClass().getName() + "loginProc Start!");

        String resultMessage; // 로그인 결과에 대한 메시지를 전달할 변수
        final String userId = CmmUtil.nvl(request.getParameter("userId"));
        final String password = EncryptUtil.encHashSHA256(
                CmmUtil.nvl(request.getParameter("password")));

        log.debug("userId" + userId);
        log.debug("password" + password);

        int res = userService.UserLogin(userId, password);

        log.debug("res :" + res);

        if (res == 1) { //로그인 성공

            resultMessage = "로그인이 성공했습니다.";
            session.setAttribute("SS_USER_ID", userId);

        } else {
            resultMessage = "아이디와 비밀번호가 올바르지 않습니다.";

        }

        MsgDTO dto = MsgDTO.builder()
                .result(res)
                .msg(resultMessage)
                .build();

        log.info(this.getClass().getName() + "loginProc End!");

        return dto;

    }


    @GetMapping(value = "loginSuccess")
    public String loginSuccess() {

        log.info(this.getClass().getName() + "user/loginSuccess Start And End!");

        return "user/loginSuccess";
    }


}