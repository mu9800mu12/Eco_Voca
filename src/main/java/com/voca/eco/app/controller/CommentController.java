package com.voca.eco.app.controller;

import com.voca.eco.app.dto.CommentDTO;
import com.voca.eco.app.dto.MsgDTO;
import com.voca.eco.app.dto.NoticeDTO;
import com.voca.eco.app.service.ICommentService;
import com.voca.eco.common.util.CmmUtil;
import com.voca.eco.common.util.DateUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@RequestMapping("comment")
@RequiredArgsConstructor
@Controller
public class CommentController {

    private final ICommentService commentService;


    /**
     * 댓글 insert
     */
    @ResponseBody
    @PostMapping(value = "insertComment")
    public MsgDTO insertComment(HttpServletRequest request, HttpSession session) throws Exception {
        log.info("[ 컨트롤러 ] : 댓글달기 시작!");

        String msg = "작성되었습니다.";

        //성공 : 1, 실패 :0
        int res = 1;
        try {
            // 1. requeset로 값을 받기
            String nSeq = CmmUtil.nvl(request.getParameter("nSeq"), "0");
            String userId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));
            String comment = CmmUtil.nvl(request.getParameter("comment"));

            Long noticeSeq = Long.parseLong(nSeq);

            log.info("nSeq : " + nSeq);
            log.info("userId : " + userId);
            log.info("comment : " + comment);

            // 2. insertComment 호출하여 데이터 삽입 시작
            commentService.insertComment(noticeSeq, userId, comment);

        // catch는 뭐냐
        } catch (Exception e) {
            log.info(e.toString());

            msg = "댓글작성에 실패하였습니다.\n다시 실행 해주세요";
            res = 0;
        }

        log.info("[ 컨트롤러 ] : 댓글달기 끝!");

            // 성공여부 res와 msg로 메세지 뷰로 전달
        return MsgDTO.builder()
                .msg(msg)
                .result(res)
                .build();
    }

    /*
     * 댓글 update
     */
    @ResponseBody
    @PostMapping(value = "/updateComment")
    public MsgDTO updateComment(HttpServletRequest request, HttpSession session) throws Exception {
        log.info("[ 컨트롤러 ] :  댓글수정 시작!");

        // 성공이면 1, 실패면 0
        int res = 0;
        String msg = "";
        MsgDTO dto = null;

        CommentDTO pDTO = null;

        try {
            String userId = CmmUtil.nvl((String) session.getAttribute("SS_USER"));
            String comment = CmmUtil.nvl(request.getParameter("comment"));
            Long commentSeq = Long.valueOf(request.getParameter("commentSeq"));
            Long noticeSeq = Long.valueOf(request.getParameter("noticeSeq"));

            log.info("userId : " + userId);
            log.info("noticeSeq : " + noticeSeq);
            log.info("commentSeq : " + commentSeq);
            log.info("comment : " + comment);


            // DTO로 담아서 보낼 거면 이렇게 빌더로 pDTO에 담아서
//            pDTO = CommentDTO.builder()
//                    .userId(userId)
//                    .commentSeq(commentSeq)
//                    .comment(comment)
//                    .noticeSeq(noticeSeq).build();


            // 이런식으로 DTO에 담아서 보내면 된다
//            commentService.updateComment(pDTO);

            commentService.updateComment(userId, comment, commentSeq, noticeSeq);

            msg = "수정되었습니다";
            res = 1;

        } catch (Exception e) {
            msg = "실패하였습니다 : " + e;
            log.info(e.toString());
        } finally {
            log.info("[ 컨트롤러 ] :  댓글수정 시작!");
        }
        return MsgDTO.builder().msg(msg).result(res).build();
    }

    /**
     * 댓글 삭제
     */
    @PostMapping(value = "deleteComment")
    public MsgDTO deleteComment(HttpServletRequest request, HttpSession session) throws Exception {
        log.info("[ 컨트롤러 ] :  댓글삭제 시작!");

        Long commentSeq = Long.valueOf(CmmUtil.nvl(request.getParameter("commentSeq")));
        Long noticeSeq = Long.valueOf(CmmUtil.nvl(request.getParameter("noticeSeq")));
        String userId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));

        String msg = "삭제 되었습니다.";
        int res = 1;

        try {
            commentService.deleteComment(commentSeq, userId, noticeSeq);

        } catch (Exception e) {
            log.info(e.toString());

            msg = "오류로 인해 실패하였습니다. \n다시 시도해 주세요";
            res = 0;

        }

        log.info("[ 컨트롤러 ] :  댓글삭제 끝!");

        return MsgDTO.builder()
                .msg(msg)
                .result(res)
                .build();
    }


}



