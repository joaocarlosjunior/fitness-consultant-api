package br.com.fitnessconsultant.exception;

public class RoleInvalidException extends RuntimeException {
    public RoleInvalidException(String message) {
        super(message);
    }
}