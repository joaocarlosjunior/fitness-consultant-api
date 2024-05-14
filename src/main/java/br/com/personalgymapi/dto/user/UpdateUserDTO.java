package br.com.personalgymapi.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Optional;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateUserDTO {
    @JsonProperty("id")
    private Optional<Long> id;

    @JsonProperty("first_name")
    private Optional<String> firstName;

    @JsonProperty("last_name")
    private Optional<String> lastName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone")
    private String phone;
}
