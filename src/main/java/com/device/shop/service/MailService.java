package com.device.shop.service;

import com.device.shop.email.MailDetail;

public interface MailService {
    String sendMail(MailDetail mailDetail);
    String sendMailWithAttachment(MailDetail mailDetail);

}