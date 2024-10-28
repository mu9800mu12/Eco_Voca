package com.voca.eco.app.controller;

import com.voca.eco.app.dto.MsgDTO;
import com.voca.eco.app.dto.NoteDTO;
import com.voca.eco.app.service.INoteService;
import com.voca.eco.common.util.CmmUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@RequestMapping(value = "/learningList")
@RequiredArgsConstructor
@Controller
public class LearningController {

    private final INoteService noteService;

    @GetMapping(value = "")
    public String learningList(HttpSession session, ModelMap model) throws Exception {

        log.info(this.getClass().getName() + "learningList Start!");

        String userId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));

        List<NoteDTO> rList = Optional.ofNullable(
                noteService.getNoteAll(userId)).orElseGet(ArrayList::new);

        model.addAttribute("rList",rList);

        return "learning/learningList";
    }
    @ResponseBody
    @PostMapping(value = "insertWord")
    public MsgDTO insertWord(HttpServletRequest request, HttpSession session) {

        log.info(this.getClass().getName() + ".insertWord Start!");

        String msg = "";

        MsgDTO dto;

        try {
            String userId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));
            String word = CmmUtil.nvl(request.getParameter("word")); // 단어
            String content = CmmUtil.nvl(request.getParameter("content")); // 내용

            log.info("session user_id : " + userId);
            log.info("word : " + word);
            log.info("content : " + content);

            NoteDTO pDTO = NoteDTO.builder(
            ).word(word
            ).userId(userId
            ).contents(content
            ).build();

            noteService.insertWord(pDTO);

            msg = "등록되었습니다.";

        } catch (Exception e) {

            msg = "등록에 실패하였습니다. : " + e.getMessage();
            log.info(e.toString());
            e.printStackTrace();

        } finally {

            dto = MsgDTO.builder().msg(msg).build();

            log.info(this.getClass().getName() + ".insertWord End!");
        }

        return dto;
    }

}