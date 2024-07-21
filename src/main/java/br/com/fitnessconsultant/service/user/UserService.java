package br.com.fitnessconsultant.service.user;

import br.com.fitnessconsultant.dto.auth.LoginUserDTO;
import br.com.fitnessconsultant.dto.auth.ResponseJwtTokenDTO;
import br.com.fitnessconsultant.dto.user.RequestUserDTO;
import br.com.fitnessconsultant.dto.user.ResponseUserDTO;
import br.com.fitnessconsultant.dto.user.UpdateUserDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    void create(RequestUserDTO requestUserDTO, String siteUrl);

    ResponseUserDTO findById(Long id);

    List<ResponseUserDTO> list();

    ResponseUserDTO update(Long id, UpdateUserDTO updateUserDTO);

    void delete(Long id);

    void setActiveUser(Long id);

    void setDisableUser(Long id);

    ResponseEntity<?> verify(String verificationToken);

    public ResponseJwtTokenDTO authenticate(LoginUserDTO loginUser);
}
