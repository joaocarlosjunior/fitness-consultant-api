package br.com.fitnessconsultant.exception;

import lombok.Data;

@Data
public class ValidationError {
    private String field;
    private String message;
    private int code;
    public ValidationError(String field, String message, int code) {
        this.field = field;
        this.message = message;
        this.code = code;
    }
}