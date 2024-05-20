package br.com.personalgymapi.dto.training;

import lombok.Data;

@Data
public class RegisterTrainingDTO {
    private Long id_user;
    private String training_type;
}
