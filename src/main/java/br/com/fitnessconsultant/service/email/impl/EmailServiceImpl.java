package br.com.fitnessconsultant.service.email.impl;

import br.com.fitnessconsultant.domain.entities.User;
import br.com.fitnessconsultant.service.email.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class EmailServiceImpl implements EmailService {
    @Value("spring.mail.username")
    private String fromAddress;

    private final JavaMailSender javaMailSender;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendEmail(User user, String password)
            throws MessagingException, UnsupportedEncodingException {
        String senderName = "Consultoria Fitness";
        String subject = "Senha para acesso ao sistema Consultoria Fitness";

        String content = emailContent();

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper= new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(user.getEmail());
        helper.setSubject(subject);

        String fullName = user.getFirstName() + " " + user.getLastName();
        content = content.replace("[[name]]", fullName);
        content = content.replace("[[password]]", password);

        helper.setText(content, true);

        javaMailSender.send(message);
    }

    private String emailContent(){
        return "<h1>Olá, [[name]]!</h1>"
                + "<p>A sua senha para acesso a nossa plataforma</p>"
                + "<p>Senha: <h2>[[password]]</h2></p>"
                + "<p>Obrigado.</p>"
                + "<p>Consultoria Fitness</p>";
    }
}
