package br.com.fitnessconsultant.services;

import br.com.fitnessconsultant.domain.entities.User;
import br.com.fitnessconsultant.dto.auth.ResponseJwtTokenDTO;
import br.com.fitnessconsultant.service.auth.impl.AuthServiceImpl;
import br.com.fitnessconsultant.utils.JwtTokenUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static br.com.fitnessconsultant.common.UserConstants.LOGIN_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @InjectMocks
    private AuthServiceImpl authService;
    @Mock
    private JwtTokenUtils jwtTokenUtils;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private Authentication authentication;

    @Test
    public void authenticate_WithValidData_ReturnsToken() {
        String expectedToken = "jwt-token-123";
        User user = new User();
        user.setEmail(LOGIN_USER.email());
        user.setPassword(LOGIN_USER.password());

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        when(authentication.getPrincipal()).thenReturn(user);

        when(jwtTokenUtils.generateToken(user)).thenReturn(expectedToken);

        ResponseJwtTokenDTO sut = authService.authenticate(LOGIN_USER);

        assertNotNull(sut);
        assertThat(sut).isEqualTo(new ResponseJwtTokenDTO(expectedToken));
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtTokenUtils, times(1)).generateToken(user);
    }

    @Test
    public void authenticate_WithIncorrectEmailOrPassword_ThrowsBadCredentialsException() {
        doThrow(new BadCredentialsException("Bad Credentials")).when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        assertThatThrownBy(() -> authService.authenticate(LOGIN_USER)).isInstanceOf(BadCredentialsException.class).hasMessageContaining("Bad Credentials");
    }

    @Test
    public void authenticate_WhenUserIsDisabled_ThrowsDisabledException(){
        doThrow(new DisabledException("User is disabled")).when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        assertThatThrownBy(() -> authService.authenticate(LOGIN_USER)).isInstanceOf(DisabledException.class).hasMessageContaining("User is disabled");
    }
}
