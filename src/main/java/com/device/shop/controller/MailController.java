package com.device.shop.controller;

import com.device.shop.email.MailDetail;
import com.device.shop.service.MailService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class MailController {

    private MailService mailService;

    @PostMapping("/send-mail")
    public String sendMail(@RequestBody MailDetail mailDetail) {
        return mailService.sendMail(mailDetail);
    }
    //Sending email with attachment
    @PostMapping("/send-mail-attachment")
    public String sendMailWithAttachment(@RequestBody MailDetail mailDetail)
    {
        return mailService.sendMailWithAttachment(mailDetail);
    }
}