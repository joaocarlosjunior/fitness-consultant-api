package br.com.fitnessconsultant.domain.enums;

import br.com.fitnessconsultant.exception.RoleInvalidException;
import lombok.Getter;

@Getter
public enum Role {
    ROLE_USER(0),
    ROLE_ADMIN(1);

    private final int value;

    Role(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Role fromValue(int value) {
        for (Role role : Role.values()) {
            if (role.getValue() == value) {
                return role;
            }
        }
        throw new RoleInvalidException("Valor de Role inválido: " + value);
    }
}
