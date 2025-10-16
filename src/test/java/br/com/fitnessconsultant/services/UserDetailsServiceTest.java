package br.com.fitnessconsultant.services;

import br.com.fitnessconsultant.domain.entities.User;
import br.com.fitnessconsultant.domain.repository.UserRepository;
import br.com.fitnessconsultant.factories.UserFactory;
import br.com.fitnessconsultant.service.security.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceTest {
    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;
    @Mock
    private UserRepository userRepository;
    private User user;

    @BeforeEach
    void setUp() {
        user = UserFactory.createUser();
    }

    @Test
    public void loadUserByUsername_WhenUsernameExists_ReturnsUserDetails() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        UserDetails sut = userDetailsService.loadUserByUsername(user.getEmail());

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(user);
        verify(userRepository).findByEmail(user.getEmail());
    }

    @Test
    public void loadUserByUsername_WhenUsernameDoesNotExists_ThrowsException() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(user.getEmail()));
        verify(userRepository).findByEmail(user.getEmail());
    }
}
