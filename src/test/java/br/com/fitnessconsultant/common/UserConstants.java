package br.com.fitnessconsultant.common;

import br.com.fitnessconsultant.domain.enums.Role;
import br.com.fitnessconsultant.dto.user.RequestUserDTO;
import br.com.fitnessconsultant.dto.user.ResponseUserDTO;
import br.com.fitnessconsultant.dto.user.UpdateUserDTO;

import java.util.List;

public class UserConstants {
    public static final RequestUserDTO NEW_USER = new RequestUserDTO(
            "Nome Usuario",
            "Sobrenome Usuario",
            "email_usuario@email.com",
            "77999999999",
            0);

    public static final RequestUserDTO INVALID_USER = new RequestUserDTO(
            "",
            "Sobrenome Usuario",
            "email_usuario@email.com",
            "77999999999",
            0);

    public static final ResponseUserDTO USER = new ResponseUserDTO(
            1L,
            "Nome usuario",
            "Sobrenome usuario",
            "email_usuario@email.com",
            "77999999999",
            Role.ROLE_USER,
            "2025-09-16T15:42:30",
            "2025-09-16T15:42:30",
            true
    );

    public static final UpdateUserDTO UPDATE_USER = new UpdateUserDTO("Nome Atualizado", "Sobrenome Atualizado", "email@email.com", "77999999999");

    public static final List<ResponseUserDTO> USERS = List.of(
            new ResponseUserDTO(
                    1L,
                    "Nome usuario A",
                    "Sobrenome usuario A",
                    "email_usuario1@email.com",
                    "77999999991",
                    Role.ROLE_USER,
                    "2025-09-16T15:42:30",
                    "2025-09-16T15:42:30",
                    true
            ),
            new ResponseUserDTO(
                    2L,
                    "Nome usuario B",
                    "Sobrenome usuario B",
                    "email_usuario2@email.com",
                    "77999999992",
                    Role.ROLE_USER,
                    "2025-09-16T15:42:30",
                    "2025-09-16T15:42:30",
                    true
            ),
            new ResponseUserDTO(
                    3L,
                    "Nome usuario C",
                    "Sobrenome usuario C",
                    "email_usuario3@email.com",
                    "77999999993",
                    Role.ROLE_USER,
                    "2025-09-16T15:42:30",
                    "2025-09-16T15:42:30",
                    true
            )
    );
}
