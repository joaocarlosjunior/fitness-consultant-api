package br.com.fitnessconsultant.service.email;

import br.com.fitnessconsultant.domain.entities.User;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface EmailService {

    void sendEmail(User user, String password) throws MessagingException, UnsupportedEncodingException;

}
