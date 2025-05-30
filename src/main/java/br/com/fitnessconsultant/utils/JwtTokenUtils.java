package br.com.fitnessconsultant.utils;

import br.com.fitnessconsultant.domain.entities.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class JwtTokenUtils {
    @Value("${api.jwt.secret}")
    private String SECRET_KEY;

    @Value("${api.jwt.jwtExpirationSeg}")
    private long expiry;

    public String generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

        System.out.println();
        Instant now = Instant.now();

        return JWT.create()
                .withIssuer("fitness-consultant-api")
                .withIssuedAt(now)
                .withExpiresAt(now.plusSeconds(expiry))
                .withSubject(user.getUsername())
                .sign(algorithm);
    }

    public String getSubjectFromToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        return JWT
                .require(algorithm)
                .withIssuer("fitness-consultant-api")
                .build()
                .verify(token)
                .getSubject();
    }

}
