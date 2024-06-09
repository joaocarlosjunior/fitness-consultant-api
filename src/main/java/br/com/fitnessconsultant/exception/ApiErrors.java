package br.com.fitnessconsultant.exception;

import lombok.Getter;


import java.util.Collections;
import java.util.List;

@Getter
public class ApiErrors {

    private List<String> errors;

    private Integer code;

    public ApiErrors(String message, Integer code) {
        this.errors = Collections.singletonList(message);
        this.code = code;
    }

    public ApiErrors(List<String> errors, Integer code) {
        this.errors = errors;
        this.code = code;
    }
}
