package com.voca.eco.app.service;

import com.voca.eco.app.dto.MailDTO;

public interface IMailService {


    int doSendMail(final String toMail,
            final String title,
            final String contents);

}
