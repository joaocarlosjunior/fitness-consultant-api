package br.com.fitnessconsultant.controller;

import br.com.fitnessconsultant.domain.repository.UserRepository;
import br.com.fitnessconsultant.dto.auth.RequestLoginUserDTO;
import br.com.fitnessconsultant.dto.auth.ResponseJwtTokenDTO;
import br.com.fitnessconsultant.dto.user.RequestUserDTO;
import br.com.fitnessconsultant.factories.UserFactory;
import br.com.fitnessconsultant.service.auth.AuthService;
import br.com.fitnessconsultant.service.user.UserService;
import br.com.fitnessconsultant.utils.JwtTokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private UserRepository userRepository;
    @MockitoBean
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private AuthService authService;
    @MockitoBean
    private UserService userService;

    private RequestUserDTO requestUser;
    private RequestLoginUserDTO requestLoginUser;

    @BeforeEach
    void setUp() {
        requestUser = UserFactory.createUserRequest();
        requestLoginUser = UserFactory.createLoginUserRequest();
    }

    @Test
    public void createUser_WithValidData_ReturnsCreated() throws Exception {
        doNothing().when(userService).create(requestUser);

        mockMvc.perform(
                post("/api/v1/auth/signup")
                        .content(objectMapper.writeValueAsString(requestUser))
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(status().isCreated());
    }

    @ParameterizedTest
    @MethodSource("providesInvalidUsers")
    public void createUser_WithInvalidData_ReturnsBadRequest(RequestUserDTO requestUserDTO) throws Exception {
        mockMvc.perform(
                post("/api/v1/auth/signup")
                        .content(objectMapper.writeValueAsString(requestUserDTO))
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(status().isBadRequest());
    }

    private static Stream<Arguments> providesInvalidUsers() {
        return Stream.of(
                Arguments.of(new RequestUserDTO("", "Sobrenome Usuario", "email_usuario@email.com", "77999999999", 0)),
                Arguments.of(new RequestUserDTO("", "", "email_usuario@email.com", "77999999999", 0)),
                Arguments.of(new RequestUserDTO("Nome Usuario", "", "email_usuario@email.com", "77999999999", 0)),
                Arguments.of(new RequestUserDTO("Nome Usuario", "Sobrenome Usuario", "", "77999999999", 0)),
                Arguments.of(new RequestUserDTO("Nome Usuario", "Sobrenome Usuario", "email_errado", "77999999999", 0)),
                Arguments.of(new RequestUserDTO("Nome Usuario", "Sobrenome Usuario", "email_usuario@email.com", "77999", 0)),
                Arguments.of(new RequestUserDTO(null, "Sobrenome Usuario", "email_usuario@email.com", "77999999999", 0)),
                Arguments.of(new RequestUserDTO(null, null, "email_usuario@email.com", "77999999999", 0)),
                Arguments.of(new RequestUserDTO(null, null, null, null, null))
        );
    }

    @Test
    public void authenticate_WithValidData_ReturnsJwtToken() throws Exception {
        ResponseJwtTokenDTO tokenExample = new ResponseJwtTokenDTO("tokenexemplo");
        when(authService.authenticate(requestLoginUser))
                .thenReturn(tokenExample);

        mockMvc.perform(
                        post("/api/v1/auth/login")
                                .content(objectMapper.writeValueAsString(requestLoginUser))
                                .contentType(MediaType.APPLICATION_JSON)

                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(tokenExample.token()));
    }

    @ParameterizedTest
    @MethodSource("providesInvalidDataAuthenticate")
    public void authenticate_WithInvalidData_ReturnsBadRequest(RequestLoginUserDTO requestLoginUserDTO) throws Exception {
        mockMvc.perform(
                post("/api/v1/auth/login")
                        .content(objectMapper.writeValueAsString(requestLoginUserDTO))
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(status().isBadRequest());
    }

    private static Stream<Arguments> providesInvalidDataAuthenticate() {
        return Stream.of(
                Arguments.of(new RequestLoginUserDTO("", "password")),
                Arguments.of(new RequestLoginUserDTO("", "")),
                Arguments.of(new RequestLoginUserDTO(null, null)),
                Arguments.of(new RequestLoginUserDTO("emailerrado", ""))
        );
    }

    @Test
    public void authenticate_WithUserInvalid_ReturnsForbidden() throws Exception {
        doThrow(BadCredentialsException.class).when(authService).authenticate(requestLoginUser);

        mockMvc.perform(
                post("/api/v1/auth/login")
                        .content(objectMapper.writeValueAsString(requestLoginUser))
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(status().isForbidden());
    }
}
