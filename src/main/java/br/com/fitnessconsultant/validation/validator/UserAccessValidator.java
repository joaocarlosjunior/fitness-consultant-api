package br.com.fitnessconsultant.validation.validator;

import br.com.fitnessconsultant.domain.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("userAccessValidator")
public class UserAccessValidator {

    public boolean hasAccess(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return false;

        User principal = (User) auth.getPrincipal();

        boolean isAdmin = principal.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        return isAdmin || principal.getId().equals(id);
    }
}