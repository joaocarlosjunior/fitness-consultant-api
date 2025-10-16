package br.com.fitnessconsultant.factories;

import br.com.fitnessconsultant.domain.entities.User;
import br.com.fitnessconsultant.domain.enums.Role;
import br.com.fitnessconsultant.dto.auth.RequestLoginUserDTO;
import br.com.fitnessconsultant.dto.user.RequestUserDTO;
import br.com.fitnessconsultant.dto.user.ResponseUserDTO;
import br.com.fitnessconsultant.dto.user.UpdateUserDTO;

import java.time.LocalDateTime;
import java.util.List;

public class UserFactory {

    public static User createUser() {
        return new User(1L,
                "Nome usuario",
                "Sobrenome usuario",
                "email_usuario@email.com",
                "12345", "77999999999",
                Role.ROLE_USER, LocalDateTime.now(),
                LocalDateTime.now(),
                true
        );
    }

    public static RequestUserDTO createUserRequest() {
        return new RequestUserDTO(
                "Nome Usuario",
                "Sobrenome Usuario",
                "email_usuario@email.com",
                "77999999999",
                0
        );
    }

    public static ResponseUserDTO createUserResponse() {
        return new ResponseUserDTO(
                1L,
                "Nome usuario",
                "Sobrenome usuario",
                "email_usuario@email.com",
                "77999999999",
                Role.ROLE_USER,
                LocalDateTime.now().toString(),
                LocalDateTime.now().toString(),
                true
        );
    }

    public static UpdateUserDTO createUpdateUserRequest() {
        return new UpdateUserDTO(
                "Nome usuario atualizado",
                "Sobrenome usuario atualizado",
                "email_usuarioatualizado@email.com",
                "77888888888"
        );
    }

    public static List<ResponseUserDTO> createUserListResponse() {
        return List.of(
                new ResponseUserDTO(
                        1L,
                        "Nome usuario A",
                        "Sobrenome usuario A",
                        "email_usuario1@email.com",
                        "77999999991",
                        Role.ROLE_USER,
                        LocalDateTime.now().toString(),
                        LocalDateTime.now().toString(),
                        true
                ),
                new ResponseUserDTO(
                        2L,
                        "Nome usuario B",
                        "Sobrenome usuario B",
                        "email_usuario2@email.com",
                        "77999999992",
                        Role.ROLE_USER,
                        LocalDateTime.now().toString(),
                        LocalDateTime.now().toString(),
                        true
                ),
                new ResponseUserDTO(
                        3L,
                        "Nome usuario C",
                        "Sobrenome usuario C",
                        "email_usuario3@email.com",
                        "77999999993",
                        Role.ROLE_USER,
                        LocalDateTime.now().toString(),
                        LocalDateTime.now().toString(),
                        true
                )
        );
    }

    public static RequestLoginUserDTO createLoginUserRequest() {
        return new RequestLoginUserDTO(
                "email_usuario@email.com",
                "12345"
        );
    }

    public static RequestLoginUserDTO createLoginUserResponse() {
        return new RequestLoginUserDTO(
                "email_usuario@email.com",
                "12345"
        );
    }
}
