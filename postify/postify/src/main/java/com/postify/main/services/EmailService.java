package com.postify.main.services;

import com.postify.main.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserRepository userRepository;

//    private final static Logger logger= LoggerFactory.getLogger(EmailService.class);

    @Async
    public void  sendEmail(String to,String subject,String body){
//        logger.info("sending email process start to  {}....",to);
        log.info("sending email process start to  {}....",to);
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("testinginfinte1@gmail.com");
        javaMailSender.send(message);
//        logger.info("email send successfully to {} !!!!",to);
        log.info("email send successfully to {} !!!!",to);
    }

    @Async
//    @Scheduled(fixedRate = 1000*1000)
//    @Scheduled(cron ="0 0 16 * *?")
    public void sendPromotionalEmailToUser(){
//        logger.info("promotional sending process started ....");
        log.info("promotional sending process started ....");
        userRepository.findAll().forEach(user -> {
            String emailBody="dear "+user.getUsername()+" its a promotionalEmail";
            sendEmail(user.getEmail(),"promotional Email ",emailBody);
        });
//        logger.info("promotional email sent successfully!!!!");
        log.info("promotional email sent successfully!!!!");

    }


}
