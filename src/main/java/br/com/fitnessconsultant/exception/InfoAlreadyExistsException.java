package br.com.fitnessconsultant.exception;

public class InfoAlreadyExistsException extends RuntimeException{
    public InfoAlreadyExistsException(String message){
        super(message);
    }
}
