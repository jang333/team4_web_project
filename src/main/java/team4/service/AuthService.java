package team4.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void send(String toeamil, String subject, String content){
        try{
            MimeMessage message = javaMailSender.createMimeMessage();

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,true,"utf-8");
            mimeMessageHelper.setFrom("upteam4@naver.com");
            mimeMessageHelper.setTo(toeamil);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(content);

            javaMailSender.send(message);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
