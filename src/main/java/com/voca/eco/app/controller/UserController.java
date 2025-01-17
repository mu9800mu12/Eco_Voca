package com.voca.eco.app.controller;

import com.voca.eco.app.dto.MsgDTO;
import com.voca.eco.app.dto.UserDTO;
import com.voca.eco.app.service.IMailService;
import com.voca.eco.app.service.INaverService;
import com.voca.eco.app.service.IUserService;
import com.voca.eco.common.util.CmmUtil;
import com.voca.eco.common.util.EncryptUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.concurrent.ThreadLocalRandom;
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

    private final IMailService mailService;

    private  final INaverService naverService;

    @GetMapping(value = "index")
    public String index() {

        log.info(this.getClass().getName() + "user/RegForm Start and End");

        return "user/index";

    }

    @GetMapping(value = "cardInsert")
    public String cardInsert() {

        log.info(this.getClass().getName() + ": cardInsert Start And End!");

        return "user/cardInsert";
    }

    @GetMapping(value = "cardInfo")
    public String cardInfo() {

        log.info(this.getClass().getName() + ": cardInsert Start And End!");

        return "user/cardInfo";
    }
    @GetMapping(value = "main")
    public String main1() {

        log.info(this.getClass().getName() + "main Start!");

        return "user/main";
    }


    @GetMapping(value = "userRegForm")
    public String userRegForm() {

        log.info(this.getClass().getName() + "user/RegForm Start and End");

        return "user/userRegForm";

    }

    @GetMapping(value = "findId")
    public String findUserId() {

        log.info(this.getClass().getName() + "user/UserId Start and End");

        return "user/findId";
    }

    @GetMapping(value = "findPassword")
    public String getUserPasswordpage() {

        log.info(this.getClass().getName() + "user/UserId Start and End");

        return "user/findPassword";
    }

    @GetMapping(value = "findUpdatePassword")
    public String getUserUpdatePassword() {

        log.info(this.getClass().getName() + "user/UserId Start and End");

        return "user/findUpdatePassword";
    }

    /*
     * 비밀번호 찾기 로직
     */
    @ResponseBody
    @PostMapping(value = "getUserPassword")
    public UserDTO getUserPassword(HttpServletRequest request, HttpSession session) throws Exception {

        String userId = CmmUtil.nvl(request.getParameter("userId"));
        String email = EncryptUtil.encAES128CBC(CmmUtil.nvl(request.getParameter("email")));
//        String email = (CmmUtil.nvl(request.getParameter("email")));
        String userName = CmmUtil.nvl(request.getParameter("userName"));

        session.setAttribute("SS_USER_ID", userId);


        log.info("userId :" + userId);
        log.info("email :" + email);
        log.info("userName :" + userName);

        UserDTO rDTO = userService.getUserPassword(userName, email, userId);

        log.info(this.getClass().getName() + "[컨트롤러] rDTO 내용을 담은 나는 : " + rDTO);

        session.setAttribute("NEW_PASSWORD", userId);

        // rDTO에 이메일과 이름으로 찾은 회원정보 전체가 담겨 있음
        return rDTO;
    }
    /**
     * 비밀번호 업데이트
     */
    @PostMapping(value = "updatePassword")
    @ResponseBody
    public MsgDTO updatePassword(HttpSession session, HttpServletRequest request) throws Exception {

        log.info(" 비밀번호 업데이트 시~~작");

        String userId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));
        String password = CmmUtil.nvl(EncryptUtil.encHashSHA256(request.getParameter("password")));


        int res = 0;
        String msg = "";

        //toDo if 문 사용해서 세션값이 있을 때만 비밀번호 재설정할 수 있게 만들어라~          - 이교수님
        //  그런데 데브툴 쓰고 있어서 세션값이 안날라 간거다? 아무튼 쓰자 4줄도 안된다고 하신다~
        if(session != null) {

            log.info("userId : " + userId);
            log.info("password : " + password);

            res = userService.userIdExists(userId);

            if (res == 1) {
                userService.updatePassword(userId, password);
                msg = "비밀번호가 변경이 되었습니다";

//                session.removeAttribute("NEW_PASSWORD");
            } else {
                msg = "사용자가 존재하지 않습니다.";

            }
        }


        MsgDTO dto = MsgDTO.builder()
                .result(res)
                .msg(msg)
                .build();


        return dto;

    }

    /**
     * 아이디 찾기
     */
    @ResponseBody
    @PostMapping(value = "getUserId")
    public UserDTO getUserId(HttpServletRequest request) throws Exception {

        String email = EncryptUtil.encAES128CBC(CmmUtil.nvl(request.getParameter("email")));
//        String email = (CmmUtil.nvl(request.getParameter("email")));
        String userName = CmmUtil.nvl(request.getParameter("userName"));

        log.info("email :" + email);
        log.info("userName :" + userName);

        UserDTO rDTO = userService.getUserId(userName, email);

        log.info(this.getClass().getName() + "rDTO 내용을 담은 나는 : " + rDTO);
        //json.userId

        // rDTO에 이메일과 이름으로 찾은 회원정보 전체가 담겨 있음
        return rDTO;
    }

    @ResponseBody
    @PostMapping(value = "UserIdExists")
    public int UserIdExists(HttpServletRequest request) throws Exception {

        String userId = CmmUtil.nvl(request.getParameter("userId"));
        log.info("userId" + userId);

        int existsYn = userService.userIdExists(userId);

        return existsYn;

    }

    @ResponseBody
    @PostMapping(value = "UserEmailExists")
    public int UserEmailExists(HttpServletRequest request) throws Exception {

//        String email = CmmUtil.nvl(request.getParameter("email"));
        String email = EncryptUtil.encAES128CBC(CmmUtil.nvl(request.getParameter("email")));

        log.info("email" + email);
        log.info(this.getClass().getName() + "이메일 컨트롤러임 ");

        //UserDTO pDTO = UserDTO.builder().userId(userId).build();
        int existsYn = userService.userEmailExists(email);

        return existsYn;

    }

    @ResponseBody
    @PostMapping(value = "NickNameExists")
    public int NickNameExists(HttpServletRequest request) throws Exception {

        String nickName = CmmUtil.nvl(request.getParameter("nickName"));
        log.info("nickName" + nickName);
        log.info(this.getClass().getName() + " 닉네임  컨트롤러임 ");

        int existsYn = userService.nickNameExists(nickName);
        log.info("닉네임 컨트롤러 Yn확인 하는 중입니다 값은 : " + existsYn);
        return existsYn;

    }

    @ResponseBody
    @PostMapping(value = "createUser")
    public MsgDTO createUser(HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName() + "createUser Start!");

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
//                .email(email)
                .userName(userName)
                .nickName(nickName)
                .birthday(birthday)
                .address(address)
                .build();
        log.info(this.getClass().getName() + " 서비스로 넘어가기 전에");
        int res = userService.createUser(pDTO);

        log.info("회원가입 결과(res) :" + res);

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

        log.info(this.getClass().getName() + "createUser End!");

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

        log.info("userId" + userId);
        log.info("password" + password);

        int res = userService.userLogin(userId, password);

        log.info("res :" + res);

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

    /**
     * 회원 가입 전 이메일 중복체크하기(Ajax를 통해 입력한 아이디 정보 받음) 유효한 이메일인 확인하기 위해 입력된 이메일에 인증번호 포함하여 메일 발송
     */
    @ResponseBody
    @PostMapping(value = "getEmailExists")
    public MsgDTO getEmailExists(HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName() + ".getEmailExists Start!");

        int res = 0;
        String msg = "";

        // 이메일을 암호화하여 데이터베이스에서 존재 여부를 확인
        String encryptedEmail = EncryptUtil.encAES128CBC(CmmUtil.nvl(request.getParameter("email")));

        log.info("암호화된 email : " + encryptedEmail);

        int existsYn = userService.userEmailExists(encryptedEmail);

        log.info("나는 1이면 이메일 중복인 Yn입니다 값은 : " + existsYn);

        // 이메일이 DB에 없어서 참
        int authNumber = 0;
        if (existsYn == 0) {
            log.info("인증번호 생성 시작");

            // 암호화된 이메일을 복호화
            String decryptedEmail = EncryptUtil.decAES128CBC(encryptedEmail);
            log.info("복호화된 email : " + decryptedEmail);

            // 6자리 랜덤 숫자 생성하기
            authNumber = ThreadLocalRandom.current().nextInt(100000, 1000000);

            log.info("authNumber : " + authNumber);

            String title = "이메일 인증번호 발송 메일";
            String contents = "인증번호는 " + authNumber + " " + "입니다.";

            res = mailService.doSendMail(decryptedEmail, title, contents);

            if (res == 0) {
                msg = "메일 발송에 실패하였습니다. 메일을 다시 확인해주세요";
            } else {
                msg = "사용 가능한 이메일입니다. 메일 발송하였습니다. 인증번호를 작성해주세요";
            }

            log.info("나는 1이어야 성공하는 res 입니다 값은 : " + res);

        } else {
            msg = "이메일이 존재합니다. 다른 이메일로 가입 바랍니다.";
        }

        log.info(this.getClass().getName() + ".getEmailExists End!");

        return MsgDTO.builder()
                .msg(msg)
                .result(res)
                .authNumber(authNumber)
                .build();
    }


    /*
     * 마이페이지 보기
     */
    @GetMapping(value = "myPageIndex")
    public String myPageIndex1() {

        log.info(this.getClass().getName() + "myPageIndex 시작");

        return "user/myPageIndex";

    }

    @GetMapping(value = "readMyPage")
    public String readMyPage() {

        log.info(this.getClass().getName() + "readMyPage");

        return "user/readMyPage";

    }

    @GetMapping(value = "updateMyPage")
    public String updateMyPage() {

        log.info(this.getClass().getName() + "updateMyPage 시작");

        return "user/updateMyPage";

    }

    @GetMapping(value = "deleteUser")
    public String deleteUser(){

        log.info(this.getClass().getName()+ "deleteUser 시작");

        return "user/deleteUser";
    }


    /*
     * 마이페이지 보여주기
     */
    @ResponseBody
    @PostMapping(value = "myPageIndex")
    public UserDTO myPageIndex(HttpSession session) throws Exception {

        log.info(this.getClass().getName()+ "내 정보 보여주기 페이지 실행");

        String userId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));

        return userService.myPageIndex(userId);
    }
    @ResponseBody
    @PostMapping(value = "updateMyPage")
    public MsgDTO updateMyPage(HttpSession session, HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName() + "내 정보 업데이트 컨트롤러 시작합니다~");

        String userId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));
        String nickName = CmmUtil.nvl(request.getParameter("nickName"));
        String address =CmmUtil.nvl(request.getParameter("address"));

        log.info(this.getClass().getName() + "여기는 내정보 업데이트 로그 찍기 userId :" + userId);
        log.info(this.getClass().getName() + "여기는 내정보 업데이트 로그 찍기 nickName : " + nickName);
        log.info(this.getClass().getName() + "여기는 내정보 업데이트 로그 찍기 address : " + address);


        int res = 0;
        String msg = "";
        try {
            userService.updateMyPage(userId, nickName, address);
            res = 1;
            msg = "수정되었습니다.";

        } catch (Exception e) {

            log.info(e.toString());
            e.printStackTrace();

            res = 0;
            msg = "정보 수정 실패."
                    + "\n다시 실행해 주세요.";

            return MsgDTO.builder()
                    .result(res)
                    .msg(msg)
                    .build();
        }


        log.info(this.getClass().getName() + "내 정보 업데이트 컨트롤러 끝났습니다~");

        return MsgDTO.builder()
                .msg(msg)
                .result(res)
                .build();
    }

    /*
     * 로그아웃
     */
    @ResponseBody
    @PostMapping(value = "logout")
    public MsgDTO logout(HttpSession session) {

        log.info(this.getClass().getName() + ".logout [controller] 로그아웃 실행");

        session.setAttribute("SS_USER_ID", "");

        session.removeAttribute("SS_USER_ID");

        MsgDTO msgDTO = MsgDTO.builder().result(1).msg("로그아웃 하였습니다.").build();

        log.info(this.getClass().getName() + ".logout [controller] 로그아웃 종료");

        return msgDTO;
    }


    /*
     * 유저 삭제하기
     */
    @ResponseBody
    @PostMapping(value = "removeUser")
    public void removeUser(HttpSession session) throws Exception {

        String userId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));

        log.info("나는 유저를 삭제시키는 컨트롤러에 세션 아이디 값 입니다" + userId);

        String token = CmmUtil.nvl((String) session.getAttribute("token"));

        if (userId.matches("naver_(.*)")) {
            naverService.deleteToken(token);

        }

        session.removeAttribute("SS_USER_ID");

        userService.userDelete(userId);

    }

}