package com.voca.eco.app.controller;

import com.voca.eco.app.dto.CommentDTO;
import com.voca.eco.app.dto.FileDTO;
import com.voca.eco.app.dto.MsgDTO;
import com.voca.eco.app.dto.NoticeDTO;
import com.voca.eco.app.service.ICommentService;
import com.voca.eco.app.service.IFileService;
import com.voca.eco.app.service.INoticeService;
import com.voca.eco.app.service.IS3Service;
import com.voca.eco.common.util.CmmUtil;
import com.voca.eco.common.util.FileUtil;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequestMapping("/notice")
@RequiredArgsConstructor
@Controller
public class NoticeController {

    private final INoticeService noticeService;
    private final ICommentService commentService;
    private final IS3Service s3Service; // S3 파일 처리 서비스 추가
    private final IFileService fileService;

    /**
     * 게시판 전체보기
     */
    @GetMapping(value = "noticeList")
    public String noticeList(HttpSession session, ModelMap model)
            throws Exception {

        log.info(this.getClass().getName() + ".게시판 Start!");

        String userId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));

        List<NoticeDTO> rList = Optional.ofNullable(noticeService.getNoticeList())
                .orElseGet(ArrayList::new);

        // 조회된 리스트 결과값 넣어주기
        model.addAttribute("rList", rList);

        log.info("유저네임은 : " + userId);

        log.info(this.getClass().getName() + "게시판 End!");

        return "notice/noticeList";

    }

    /*
        게시판 작성 페이지
     */
    @GetMapping(value = "noticeReg")
    public String noticeReg() {

        log.info(this.getClass().getName() + ".게시판 작성 페이지 Start & 끝!");

        return "notice/noticeReg";
    }

    /**
     * 게시판 작성하기 (파일 업로드 포함)
     */
    @ResponseBody
    @PostMapping(value = "noticeInsert")
    public MsgDTO noticeInsert(HttpServletRequest request, HttpSession session,
            @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {

        log.info(this.getClass().getName() + ".noticeInsert Start!");

        log.info("사진이 들어 왔어요 : " + file);

        String msg = "";
        MsgDTO dto;

        try {
            String userId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));
            String title = CmmUtil.nvl(request.getParameter("title")); // 제목
            String noticeYn = CmmUtil.nvl(request.getParameter("noticeYn")); // 공지글 여부
            String contents = CmmUtil.nvl(request.getParameter("contents")); // 내용

            log.info("session user_id : " + userId);
            log.info("title : " + title);
            log.info("noticeYn : " + noticeYn);
            log.info("contents : " + contents);

            // 1. 게시글 정보 저장
            Long noticeSeq = noticeService.insertNoticeInfo(title, noticeYn, contents, userId);


            log.info("나 게시글 번호 : " + noticeSeq);

            log.info("파일 업로드 전 로그");

            // 2. 파일 업로드 처리
            if(file != null && !file.isEmpty()) {
                String saveFilePath = FileUtil.mkdirForData();      // 웹서버에 저장할 파일 경로 생성

                log.info("파일 업로드 하는 로그");

                String orgFileName = file.getOriginalFilename();      // 파일의 원본 명
                String fileSize = String.valueOf(file.getSize());     // 파일 크기
                String ext = orgFileName.substring(orgFileName.lastIndexOf(".") + 1).toLowerCase(); // 확장자

                if (ext.equals("jpeg") || ext.equals("jpg") || ext.equals("gif") || ext.equals("png")) {

                    log.info("orgFileName : " + orgFileName);
                    log.info("fileSize : " + fileSize);
                    log.info("ext : " + ext);
                    log.info("saveFilePath : " + saveFilePath);

                    // 파일을 S3에 업로드
                    FileDTO rDTO = s3Service.uploadFile(file, ext);

                    // 파일 정보 저장
                    FileDTO fileDTO = FileDTO.builder()
                            .noticeSeq(noticeSeq)
                            .orgFileName(orgFileName)
                            .saveFilePath(saveFilePath)
                            .fileSize(fileSize)
                            .saveFileName(rDTO.saveFileName())
                            .saveFileUrl(rDTO.saveFileUrl())
                            .build();

                    log.info("saveFileUrl : " + rDTO.saveFileUrl());

                    fileService.saveFiles(fileDTO);

                    fileDTO = null;
                }
            } else {
                log.info("파일이 실종됨");
            }

            msg = "등록되었습니다.";

        } catch (Exception e) {
            msg = "등록에 실패하였습니다. : " + e.getMessage();
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            dto = MsgDTO.builder().msg(msg).build();
            log.info(this.getClass().getName() + ".noticeInsert End!");
        }

        return dto;
    }


    /**
     * 게시판 상세보기
     */

    /**
     * 게시글 정보 & 게시글에 달린 댓글을 가져오는 기능
     * @param request 클라이언트에서 받아오는 값
     * @param model 클라이언트에게 보여줄 값을 담은 객체
     * @return 보여줄 페이지
     * @throws Exception
     */
    @GetMapping(value = "noticeInfo")
    public String noticeInfo(HttpSession session,HttpServletRequest request, ModelMap model) throws Exception {

        log.info(this.getClass().getName() + ".noticeInfo Start!");

        // 1. 클라이언트에게 받은 값
        String nSeq = CmmUtil.nvl(request.getParameter("nSeq"), "0"); // 공지글번호(PK)


        String userId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));
        log.info("nSeq : " + nSeq);

        // 2. 받은 값을 가지고 게시글 불러오기와 댓글 리스트 불러오기에 각각 보내기 위한 DTO 선언 (Notice, comment)
        NoticeDTO pDTO = NoticeDTO.builder()
                .noticeSeq(Long.parseLong(nSeq)).build();

        CommentDTO cDTO = CommentDTO.builder()
                .noticeSeq(Long.parseLong(nSeq)).build();

        //pDTO cDTO 로 주세요 요청
        //rDTO rList로 받을게요 값 받기

        // 3. 값을 보내기 위해 만든 DTO를 Service로 보내서 값을 받고 돌려줄 객체에 담기
        NoticeDTO rDTO = Optional.ofNullable(noticeService.getNoticeInfo(pDTO, true))
                .orElseGet(() -> NoticeDTO.builder().build());

        // 댓글 보기 호출
        List<CommentDTO> rList = Optional.ofNullable(commentService.getCommentList(cDTO))
                .orElseGet(ArrayList::new);

        // 이미지 호출
        FileDTO fDTO = fileService.getFilePath(Long.parseLong(nSeq));


        // 4. 클라이언트에게 보여주기 위해 model객체에 service의 return값을 담기
        model.addAttribute("rDTO", rDTO);
        model.addAttribute("rList", rList);
        model.addAttribute("fDTO", fDTO);

        log.info(this.getClass().getName() + ".noticeInfo End!");

        // 5. rDTO와 rList에 담긴 값을 타임리프나 js로 꺼내서 html에서 보여주기
        return "notice/noticeInfo";
    }

    /**
     * 게시판 수정 보기
     */
    @GetMapping(value = "noticeEditInfo")
    public String noticeEditInfo(HttpServletRequest request, ModelMap model) throws Exception {

        log.info(this.getClass().getName() + ".noticeEditInfo Start!");

        String nSeq = CmmUtil.nvl(request.getParameter("nSeq")); // 공지글번호(PK)

        log.info("nSeq : " + nSeq);

        NoticeDTO pDTO = NoticeDTO.builder().
                noticeSeq(Long.parseLong(nSeq)).build();

        NoticeDTO rDTO = Optional.ofNullable(noticeService.getNoticeInfo(pDTO, false))
                .orElseGet(() -> NoticeDTO.builder().build());

        model.addAttribute("rDTO", rDTO);

        log.info(this.getClass().getName() + ".noticeEditInfo End!");

        return "notice/noticeEditInfo";
    }

    /**
     * 게시판 글 수정 (파일 업로드 포함)
     */
    @ResponseBody
    @PostMapping(value = "noticeUpdate")
    public MsgDTO noticeUpdate(HttpSession session, HttpServletRequest request,
            @RequestParam(value = "file", required = false) List<MultipartFile> files) throws Exception {

        log.info(this.getClass().getName() + ".noticeUpdate Start!");

        String msg = "";
        MsgDTO dto = null;

        try {
            String userId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID")); // 아이디
            String nSeq = CmmUtil.nvl(request.getParameter("nSeq")); // 글번호(PK)
            String title = CmmUtil.nvl(request.getParameter("title")); // 제목
            String noticeYn = CmmUtil.nvl(request.getParameter("noticeYn")); // 공지글 여부
            String contents = CmmUtil.nvl(request.getParameter("contents")); // 내용

            log.info("userId : " + userId);
            log.info("nSeq : " + nSeq);
            log.info("title : " + title);
            log.info("noticeYn : " + noticeYn);
            log.info("contents : " + contents);

            // 1. 게시글 수정하기
            noticeService.updateNoticeInfo(Long.parseLong(nSeq), title, contents, userId, noticeYn);

            // 2. 파일 업로드 처리

            // 게시글 수정시 추가된 MultipartFile이 있을 경우 실행
            if (files != null) {

                // 현재 파일에 있는 이미지 저장 메서드 호출
                this.saveImg(files, Long.parseLong(nSeq));

            }

            msg = "수정되었습니다.";

        } catch (Exception e) {
            msg = "실패하였습니다. : " + e.getMessage();
            log.info(e.toString());
            e.printStackTrace();

        } finally {
            dto = MsgDTO.builder().msg(msg).build();
            log.info(this.getClass().getName() + ".noticeUpdate End!");
        }

        return dto;
    }

    /**
     * 게시판 삭제
     */
    @ResponseBody
    @PostMapping(value = "noticeDelete")
    public MsgDTO noticeDelete(HttpServletRequest request) {

        log.info(this.getClass().getName() + ".noticeDelete Start!");

        String msg = "";
        MsgDTO dto = null;

        try {
            String nSeq = CmmUtil.nvl(request.getParameter("nSeq")); // 글번호(PK)
            log.info("nSeq : " + nSeq);

            noticeService.deleteNoticeInfo(Long.parseLong(nSeq));

            msg = "삭제되었습니다.";

        } catch (Exception e) {
            msg = "실패하였습니다. : " + e.getMessage();
            log.info(e.toString());
            e.printStackTrace();

        } finally {
            dto = MsgDTO.builder().msg(msg).build();
            log.info(this.getClass().getName() + ".noticeDelete End!");
        }

        return dto;
    }

    /**
     * 이미지 저장을 위한 메서드
     */
    private void saveImg(List<MultipartFile> files, Long noticeSeq) throws Exception {

        log.info("controller saveImg");

        // 웹서버에 저장할 파일 경로 생성
        String saveFilePath = FileUtil.mkdirForData();

        for (MultipartFile mf : files) {

            log.info("mf : " + mf);

            String orgFileName = mf.getOriginalFilename();      // 파일의 원본 명
            String fileSize = String.valueOf(mf.getSize());     // 파일 크기
            String ext = orgFileName.substring(orgFileName.lastIndexOf(".") + 1,    // 확장자
                    orgFileName.length()).toLowerCase();

            // 이미지 파일만 실행되도록 함
            if (ext.equals("jpeg") || ext.equals("jpg") || ext.equals("gif") || ext.equals("png")) {

                log.info("orgFileName : " + orgFileName);
                log.info("fileSize : " + fileSize);
                log.info("ext : " + ext);
                log.info("saveFilePath : " + saveFilePath);

                //  Object Storage에 이미지 업로드를 위한 메서드 호출
                FileDTO rDTO = s3Service.uploadFile(mf, ext);

                FileDTO fileDTO = FileDTO.builder()
                        .noticeSeq(0L)
                        .orgFileName(orgFileName)
                        .saveFilePath(saveFilePath)
                        .saveFileName(rDTO.saveFileName())
                        .saveFileUrl(rDTO.saveFileUrl())
                        .build();

                log.info("sageFileUrl : " + rDTO.saveFileUrl());

                fileService.saveFiles(fileDTO);

                fileDTO = null;

            }
        }

}
}
