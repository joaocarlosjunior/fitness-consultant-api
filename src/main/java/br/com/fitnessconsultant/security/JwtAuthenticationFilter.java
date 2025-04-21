package br.com.fitnessconsultant.security;

import br.com.fitnessconsultant.domain.entities.User;
import br.com.fitnessconsultant.domain.repository.UserRepository;
import br.com.fitnessconsultant.utils.JwtTokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final JwtTokenUtils jwtTokenUtils;
    private final HandlerExceptionResolver handlerExceptionResolver;

    public JwtAuthenticationFilter(UserRepository userRepository,
                                   JwtTokenUtils jwtTokenUtils,
                                   HandlerExceptionResolver handlerExceptionResolver) {
        this.userRepository = userRepository;
        this.jwtTokenUtils = jwtTokenUtils;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        String token = recoveryToken(request);

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String subject = jwtTokenUtils.getSubjectFromToken(token);
            User user = userRepository.findByEmail(subject).get();

            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }

    private String recoveryToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }
}
