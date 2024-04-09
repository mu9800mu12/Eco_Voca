package com.voca.eco.app.service.impl;


import com.voca.eco.app.dto.MailDTO;
import com.voca.eco.app.service.IMailService;
import com.voca.eco.common.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;


@Slf4j
@RequiredArgsConstructor
@Service
public class MailService implements IMailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromMail;

    @Override
    public int doSendMail(final String toMail,
            final String title,
            final String contents) {

        log.info(this.getClass().getName() + ".doSendMail start!");

        // 메일 발송 성공여부(발송성공 : 1 / 발송실패 : 0)
        int res = 1;

        log.info("toMail : " + toMail);
        log.info("title : " + title);
        log.info("contents : " + contents);

        // 메일 발송 메시지 구조(파일 첨부 가능)
        MimeMessage message = mailSender.createMimeMessage();

        // 메일 발송 메시지 구조를 쉽게 생성하게 도와주는 객체
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, "UTF-8");

        try {

            messageHelper.setTo(toMail); // 받는 사람
            messageHelper.setFrom(fromMail); // 보내는 사람
            messageHelper.setSubject(title); // 메일 제목
            messageHelper.setText(contents); // 메일 내용

            mailSender.send(message);
        } catch (Exception e) {//모든 에러 다 잡기
            res = 0; // 메일 발송이 실패해기 때문에 0으로 변경
            log.info("[ERROR] " + this.getClass().getName() + ".doSendMail : " + e);
        }

        // 로그 찍기(추후 찍은 로그를 통해 이 함수 호출이 끝났는지 파악하기 용이하다.)
        log.info(this.getClass().getName() + ".doSendMail end!");
        return res;
    }
}