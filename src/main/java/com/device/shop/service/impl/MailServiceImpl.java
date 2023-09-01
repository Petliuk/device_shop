package com.device.shop.service.impl;

import java.io.File;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.device.shop.email.MailDetail;
import com.device.shop.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service

public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public String sendMail(MailDetail mailDetail) {
        try {

            SimpleMailMessage emailMessage = new SimpleMailMessage();

            emailMessage.setFrom(sender);
            emailMessage.setTo(mailDetail.getRecipient());
            emailMessage.setSubject(mailDetail.getSubject());
            emailMessage.setText(mailDetail.getMsgBody());

            mailSender.send(emailMessage);
            return "Email has been sent successfully...";
        }
        catch (Exception e) {
            return "Error while Sending email!!!";
        }
    }

    @Override
    public String sendMailWithAttachment(MailDetail mailDetail) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(mailDetail.getRecipient());
            mimeMessageHelper.setSubject(mailDetail.getSubject());
            mimeMessageHelper.setText(mailDetail.getMsgBody());

            FileSystemResource file = new FileSystemResource(new File(mailDetail.getAttachment()));

            mimeMessageHelper.addAttachment(file.getFilename(), file);

            mailSender.send(mimeMessage);
            return "Email has been sent successfully...";
        }
        catch (MessagingException e) {
            return "Error while sending email!!!";
        }
    }

}