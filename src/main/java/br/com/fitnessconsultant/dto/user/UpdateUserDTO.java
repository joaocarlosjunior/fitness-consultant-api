package br.com.fitnessconsultant.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UpdateUserDTO(
    String firstName,
    String lastName,
    String email,
    String phone
){}
