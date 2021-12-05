package com.example.sportmate.service;

import com.example.sportmate.DataTest;
import com.example.sportmate.entity.Users;
import com.example.sportmate.exception.AuthenticationException;
import com.example.sportmate.exception.NotFoundException;
import com.example.sportmate.record.LoginRequestDto;
import com.example.sportmate.record.LoginResponseDto;
import com.example.sportmate.record.ResponseDefaultDto;
import com.example.sportmate.record.SigninRequestDto;
import com.example.sportmate.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.example.sportmate.DataTest.buildNewUser;
import static com.example.sportmate.mapper.UsersMapper.buildUsers;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@SpringBootTest
class LoginServiceTest implements DataTest {
    @Autowired
    private final LoginService loginService;

    @MockBean
    private final UsersRepository usersRepository;

    @MockBean
    private final PasswordEncoder passwordEncoder;


    @Autowired
    LoginServiceTest(final LoginService loginService,
                     final UsersRepository usersRepository,
                     final PasswordEncoder passwordEncoder) {
        this.loginService = loginService;
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Test
    void signin_should_saved_a_new_user() {
        String encodedPassword = passwordEncoder.encode(PASSWORD);
        SigninRequestDto signinRequestDto = new SigninRequestDto(EMAIL, encodedPassword, LAST_NAME, FIRST_NAME,
                MOBILE, PROFILE_PICTURE, SEX, BIRTHDAY, CONSENTS);

        Users users = buildUsers(signinRequestDto, encodedPassword);
        when(usersRepository.save(users)).thenReturn(users);

        ResponseEntity<ResponseDefaultDto> signinResponse = loginService.signin(signinRequestDto);
        assertThat(signinResponse).isEqualTo(new ResponseEntity<>(new ResponseDefaultDto("Création du compte réalisée avec succès"), HttpStatus.CREATED));
    }

    @Test
    void login_should_not_find_user_by_email_and_throw_NotFoundException() {
        LoginRequestDto loginRequestDto = new LoginRequestDto(EMAIL, PASSWORD);

        when(usersRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> loginService.login(loginRequestDto))
                .hasMessageContaining("Connexion refusée : utilisateur non trouvé")
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void login_should_find_user_by_email_but_with_wrong_password_so_throw_AuthenticationException() {
        LoginRequestDto loginRequestDto = new LoginRequestDto(EMAIL, PASSWORD);

        when(usersRepository.findByEmail(EMAIL)).thenReturn(Optional.of(buildNewUser()));
        when(passwordEncoder.matches(loginRequestDto.password(), PASSWORD)).thenReturn(false);

        assertThatThrownBy(() -> loginService.login(loginRequestDto))
                .hasMessageContaining("Le mot de passe est incorrect")
                .isInstanceOf(AuthenticationException.class);
    }

    @Test
    void login_should_find_user_and_generate_token() {
        LoginRequestDto loginRequestDto = new LoginRequestDto(EMAIL, PASSWORD);
        Users users = buildNewUser();

        when(usersRepository.findByEmail(EMAIL)).thenReturn(Optional.of(users));
        when(passwordEncoder.matches(loginRequestDto.password(), PASSWORD)).thenReturn(true);

        LoginResponseDto loginResponseDto = loginService.login(loginRequestDto);
        assertThat(loginResponseDto.email()).isEqualTo(users.email());
    }
}