package br.com.fitnessconsultant.exception;

public class SendEmailException extends RuntimeException{
    public SendEmailException(String message) {
        super(message);
    }
}
