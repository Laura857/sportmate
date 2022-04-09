package com.example.sportmate.service;

import com.example.sportmate.DataTest;
import com.example.sportmate.entity.Hobbies;
import com.example.sportmate.entity.Level;
import com.example.sportmate.entity.Sport;
import com.example.sportmate.entity.Users;
import com.example.sportmate.exception.AuthenticationException;
import com.example.sportmate.exception.NotFoundException;
import com.example.sportmate.record.authentification.login.LoginRequestDto;
import com.example.sportmate.record.authentification.login.LoginResponseDto;
import com.example.sportmate.record.authentification.signing.SigningRequestDto;
import com.example.sportmate.record.authentification.signing.SportDto;
import com.example.sportmate.record.authentification.signing.UserRequestDto;
import com.example.sportmate.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.example.sportmate.DataTest.*;
import static java.util.Collections.singletonList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class LoginServiceTest implements DataTest {
    @Autowired
    private LoginService loginService;

    @MockBean
    private UsersRepository usersRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private PasswordService passwordService;

    @MockBean
    private UserHobbiesRepository userHobbiesRepository;

    @MockBean
    private HobbiesRepository hobbiesRepository;

    @MockBean
    private SportRepository sportRepository;

    @MockBean
    private LevelRepository levelRepository;

    @MockBean
    private UserFavoriteSportRepository userFavoriteSportRepository;

    @Test
    void signingAndLogin_should_throw_exception_when_email_already_exist_in_database() {
        final LoginRequestDto loginRequestDto = new LoginRequestDto(EMAIL, PASSWORD);
        final UserRequestDto userRequestDto = new UserRequestDto(PROFILE_PICTURE, false, LAST_NAME, FIRST_NAME, GENRE, BIRTHDAY, MOBILE);
        final SportDto sportDto = new SportDto(SPORT_NAME_SWIM, LEVEL_NAME_BEGINNING);
        final SigningRequestDto signingRequestDto = new SigningRequestDto(loginRequestDto, userRequestDto, singletonList(sportDto), singletonList(HOBBIES));

        when(passwordEncoder.encode(PASSWORD))
                .thenReturn(PASSWORD);

        given(usersRepository.save(buildDefaultUsers())).willAnswer( invocation -> { throw new Exception("Error message", new Throwable("duplicate key value violates unique constraint \"users_email_key\"")); });


        assertThatThrownBy(() -> loginService.signingAndLogin(signingRequestDto))
                .hasMessage("Un compte existe déjà pour cette adresse email.")
                .isInstanceOf(AuthenticationException.class);
    }

    @Test
    void signingAndLogin_should_throw_exception_when_signing_failed() {
        final LoginRequestDto loginRequestDto = new LoginRequestDto(EMAIL, PASSWORD);
        final UserRequestDto userRequestDto = new UserRequestDto(PROFILE_PICTURE, false, LAST_NAME, FIRST_NAME, GENRE, BIRTHDAY, MOBILE);
        final SportDto sportDto = new SportDto(SPORT_NAME_SWIM, LEVEL_NAME_BEGINNING);
        final SigningRequestDto signingRequestDto = new SigningRequestDto(loginRequestDto, userRequestDto, singletonList(sportDto), singletonList(HOBBIES));

        when(passwordEncoder.encode(PASSWORD))
                .thenReturn(PASSWORD);

        given(usersRepository.save(buildDefaultUsers())).willAnswer( invocation -> { throw new Exception("Error message"); });


        assertThatThrownBy(() -> loginService.signingAndLogin(signingRequestDto))
                .hasMessage("Error message")
                .isInstanceOf(AuthenticationException.class);
    }

    @Test
    void signingAndLogin_should_saved_a_new_user() {
        final LoginRequestDto loginRequestDto = new LoginRequestDto(EMAIL, PASSWORD);
        final UserRequestDto userRequestDto = buildDefaultUserRequest();
        final SportDto sportDto = new SportDto(SPORT_NAME_SWIM, LEVEL_NAME_BEGINNING);
        final SigningRequestDto signingRequestDto = new SigningRequestDto(loginRequestDto, userRequestDto, singletonList(sportDto), singletonList(HOBBIES));

        when(passwordEncoder.encode(PASSWORD))
                .thenReturn(PASSWORD);

        final Users userSaved = buildNewUserDefault();
        when(usersRepository.save(buildDefaultUsers()))
                .thenReturn(userSaved);

        final Hobbies moviesHobbies = new Hobbies(ID, HOBBIES);

        when(hobbiesRepository.findByLabel(HOBBIES))
                .thenReturn(of(moviesHobbies));

        doNothing().when(userHobbiesRepository).save(userSaved.id(), moviesHobbies.id());

        final Sport sport = new Sport(ID, SPORT_NAME_SWIM);
        when(sportRepository.findByLabel(SPORT_NAME_SWIM))
                .thenReturn(of(sport));

        final Level level = new Level(ID, LEVEL_NAME_BEGINNING);
        when(levelRepository.findByLabel(LEVEL_NAME_BEGINNING))
                .thenReturn(of(level));
        doNothing().when(userFavoriteSportRepository).save(userSaved.id(), sport.id(), level.id());

        when(usersRepository.findByEmail(EMAIL))
                .thenReturn(of(userSaved));

        when(passwordService.isPasswordNoMatch(loginRequestDto.password(), PASSWORD)).thenReturn(false);

        final LoginResponseDto loginResponse = loginService.signingAndLogin(signingRequestDto);
        assertThat(loginResponse.email())
                .isEqualTo(EMAIL);

        assertThat(loginResponse.userId())
                .isEqualTo(ID);

        assertThat(loginResponse.token())
                .isNotBlank();
    }

    @Test
    void login_should_not_find_user_by_email_and_throw_NotFoundException() {
        final LoginRequestDto loginRequestDto = new LoginRequestDto(EMAIL, PASSWORD);

        when(usersRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> loginService.login(loginRequestDto))
                .hasMessageContaining("Connexion refusée : utilisateur non trouvé")
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void login_should_find_user_by_email_but_with_wrong_password_so_throw_AuthenticationException() {
        final LoginRequestDto loginRequestDto = new LoginRequestDto(EMAIL, PASSWORD);

        when(usersRepository.findByEmail(EMAIL)).thenReturn(of(buildNewUser()));
        when(passwordService.isPasswordNoMatch(loginRequestDto.password(), PASSWORD)).thenReturn(true);

        assertThatThrownBy(() -> loginService.login(loginRequestDto))
                .hasMessageContaining("Le mot de passe est incorrect")
                .isInstanceOf(AuthenticationException.class);
    }

    @Test
    void login_should_find_user_and_generate_token() {
        final LoginRequestDto loginRequestDto = new LoginRequestDto(EMAIL, PASSWORD);
        final Users users = buildNewUser();

        when(usersRepository.findByEmail(EMAIL)).thenReturn(of(users));
        when(passwordService.isPasswordNoMatch(loginRequestDto.password(), PASSWORD)).thenReturn(false);

        final LoginResponseDto loginResponseDto = loginService.login(loginRequestDto);
        assertThat(loginResponseDto.email()).isEqualTo(users.email());
        assertThat(loginResponseDto.userId()).isEqualTo(users.id());
    }

    @Test
    void isEmailAlreadyUsedForAnotherAccount_should_return_false_when_email_already_exists_in_database(){
        when(usersRepository.findByEmail(EMAIL))
                .thenReturn(of(buildDefaultUsers()));

        assertThat(loginService.isEmailAlreadyUsedForAnotherAccount(EMAIL))
                .isTrue();
    }

    @Test
    void isEmailAlreadyUsedForAnotherAccount_should_return_true_when_email_not_exists_in_database(){
        when(usersRepository.findByEmail(EMAIL))
                .thenReturn(empty());

        assertThat(loginService.isEmailAlreadyUsedForAnotherAccount(EMAIL))
                .isFalse();
    }
}
