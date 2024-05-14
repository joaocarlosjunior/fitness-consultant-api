package br.com.personalgymapi.exception;

public class InfoAlreadyExistsException extends RuntimeException{
    public InfoAlreadyExistsException(String message){
        super(message);
    }
}
