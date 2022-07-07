package com.vfa.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailHelper {

    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

    public EmailHelper(JavaMailSender sender) {
        this.mailSender = mailSender;
    }


    @Async
    public void sendSimpleMessage(String email, String firstname, String verificationCode) {
        String subject = "Account Confirmation";

        String emailContent = "\r\n" +
                "<html>\n" +
                "  <head>\n" +
                "  <meta http-equiv='Content-Type'; content='text/html'; charset=UTF-8>\n" +
                "  <style>\n" +
                "    h4 {color: grey}" +
                "    a { color: white;\n" +
                "    background-color: #FF7D64;\n" +
                "    padding: 13px 20px 10px 20px;\n" +
                "    border-radius: 9px;\n" +
                "    font-weight: 600;\n" +
                "    letter-spacing: 1px;" +
                "    text-decoration: none;}\n" +
                "   </style>\n" +
                "  </head>" +
                "  <body> \n" +
                "    <div align='center'>\n" +
                "        <div style='text-align:center;'>\n" +
                "           <IMG src='https://icon-library.com/images/verified-icon-png/verified-icon-png-1.jpg' height='155' width='150'>\n" +
                "        </div>\n" +
                "        <h3>Hello " + firstname + "!</h3><h4>Your verification code is: </h4><h2>" + verificationCode + "</h2>\n" +
                "        <h4>Please click on the button to verify your account and change your password.</h4>\n" +
                "       <a href='www.google.com'>VERIFY ACCOUNT</a>\n" +
                "    </div>\n" +
                "  </body>\n" +
                "</html>\n";

        try {
            MimeMessage mail = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mail, true);

            helper.setFrom(this.username);
            helper.setTo(email)
            ;
            helper.setSubject(subject);
            helper.setText(emailContent, true);
            mailSender.send(mail);

        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
    }
}
