package br.com.fitnessconsultant.service.security;

import br.com.fitnessconsultant.domain.entities.User;

public interface JwtService {
    String generateToken(User user);

    String getSubjectFromToken(String token);
}
