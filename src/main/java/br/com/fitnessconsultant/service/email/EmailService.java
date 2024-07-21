package br.com.fitnessconsultant.service.email;

import br.com.fitnessconsultant.domain.entities.ConfirmationToken;
import br.com.fitnessconsultant.domain.entities.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;

import java.io.UnsupportedEncodingException;

public interface EmailService {

    void sendVerificationEmail(User user, ConfirmationToken confirmationToken, String siteUrl) throws MessagingException, UnsupportedEncodingException;

}
