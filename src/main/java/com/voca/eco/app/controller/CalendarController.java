package com.voca.eco.app.controller;

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
@RequiredArgsConstructor
@RequestMapping(value = "/calendar")
@Controller
public class CalendarController {

    private final INoteService noteService;

    @GetMapping(value = "")
    public String calendar(
            HttpSession session,
            ModelMap map) throws Exception {

        log.info(this.getClass().getName() + ".calendar Start! ");

        String userId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));

        List<NoteDTO> rList = Optional.ofNullable(
                noteService.getNote(userId)
                ).orElseGet(() -> new ArrayList<>());

        log.info(rList.toString());

        map.addAttribute("rList", rList);

        return "calendar/calendar";
    }

    @ResponseBody
    @PostMapping(value = "getList")
    public List<NoteDTO> noteList(
            HttpSession session,
            HttpServletRequest request) throws Exception {

        log.info(this.getClass().getName() + ".calendar Start! ");

        String userId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));
        String date = CmmUtil.nvl(request.getParameter("date"));

        log.info("date : " + date);

        List<NoteDTO> rList = noteService.getNoteList(userId, date);



        return rList;
    }
}
