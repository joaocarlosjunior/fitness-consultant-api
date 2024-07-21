package br.com.fitnessconsultant.dto.auth;

import lombok.Builder;

@Builder
public record ResponseJwtTokenDTO(
        String token
) {
}
