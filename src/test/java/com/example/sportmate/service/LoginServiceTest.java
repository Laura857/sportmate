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
import com.example.sportmate.record.authentification.signin.SigningRequestDto;
import com.example.sportmate.record.authentification.signin.SportRequestDto;
import com.example.sportmate.record.authentification.signin.UserRequestDto;
import com.example.sportmate.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static com.example.sportmate.DataTest.buildNewUser;
import static com.example.sportmate.DataTest.buildNewUserDefault;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class LoginServiceTest implements DataTest {
    @Autowired
    private final LoginService loginService;

    @MockBean
    private final UsersRepository usersRepository;

    @MockBean
    private final PasswordEncoder passwordEncoder;

    @MockBean
    private final UserHobbiesRepository userHobbiesRepository;

    @MockBean
    private final HobbiesRepository hobbiesRepository;

    @MockBean
    private final SportRepository sportRepository;

    @MockBean
    private final LevelRepository levelRepository;

    @MockBean
    private final UserFavoriteSportRepository userFavoriteSportRepository;

    @Autowired
    LoginServiceTest(final LoginService loginService,
                     final UsersRepository usersRepository,
                     final PasswordEncoder passwordEncoder,
                     final UserHobbiesRepository userHobbiesRepository,
                     final HobbiesRepository hobbiesRepository,
                     final SportRepository sportRepository,
                     final LevelRepository levelRepository,
                     final UserFavoriteSportRepository userFavoriteSportRepository) {
        this.loginService = loginService;
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.userHobbiesRepository = userHobbiesRepository;
        this.hobbiesRepository = hobbiesRepository;
        this.sportRepository = sportRepository;
        this.levelRepository = levelRepository;
        this.userFavoriteSportRepository = userFavoriteSportRepository;
    }

    @Test
    void signingAndLogin_should_throw_exception_when_email_already_exist_in_database() {
        final LoginRequestDto loginRequestDto = new LoginRequestDto(EMAIL, PASSWORD);
        final UserRequestDto userRequestDto = new UserRequestDto(PROFILE_PICTURE, false, LAST_NAME, FIRST_NAME, GENRE, BIRTHDAY, MOBILE);
        final SportRequestDto sportRequestDto = new SportRequestDto(SPORT_NAME, LEVEL_NAME);
        final SigningRequestDto signingRequestDto = new SigningRequestDto(loginRequestDto, userRequestDto, singletonList(sportRequestDto), singletonList(HOBBIES));

        when(passwordEncoder.encode(PASSWORD))
                .thenReturn(PASSWORD);

        given(usersRepository.save(new Users(null, EMAIL, PASSWORD, LAST_NAME, FIRST_NAME, MOBILE, PROFILE_PICTURE, GENRE, BIRTHDAY,
                false, LocalDate.now(), null))).willAnswer( invocation -> { throw new Exception("Error message", new Throwable("duplicate key value violates unique constraint \"users_email_key\"")); });


        assertThatThrownBy(() -> loginService.signingAndLogin(signingRequestDto))
                .hasMessage("Un compte existe déjà pour cette adresse email.")
                .isInstanceOf(AuthenticationException.class);
    }

    @Test
    void signingAndLogin_should_throw_exception_when_signing_failed() {
        final LoginRequestDto loginRequestDto = new LoginRequestDto(EMAIL, PASSWORD);
        final UserRequestDto userRequestDto = new UserRequestDto(PROFILE_PICTURE, false, LAST_NAME, FIRST_NAME, GENRE, BIRTHDAY, MOBILE);
        final SportRequestDto sportRequestDto = new SportRequestDto(SPORT_NAME, LEVEL_NAME);
        final SigningRequestDto signingRequestDto = new SigningRequestDto(loginRequestDto, userRequestDto, singletonList(sportRequestDto), singletonList(HOBBIES));

        when(passwordEncoder.encode(PASSWORD))
                .thenReturn(PASSWORD);

        given(usersRepository.save(new Users(null, EMAIL, PASSWORD, LAST_NAME, FIRST_NAME, MOBILE, PROFILE_PICTURE, GENRE, BIRTHDAY,
                false, LocalDate.now(), null))).willAnswer( invocation -> { throw new Exception("Error message"); });


        assertThatThrownBy(() -> loginService.signingAndLogin(signingRequestDto))
                .hasMessage("Error message")
                .isInstanceOf(AuthenticationException.class);
    }

    @Test
    void signingAndLogin_should_saved_a_new_user() {
        final LoginRequestDto loginRequestDto = new LoginRequestDto(EMAIL, PASSWORD);
        final UserRequestDto userRequestDto = new UserRequestDto(PROFILE_PICTURE, false, LAST_NAME, FIRST_NAME, GENRE, BIRTHDAY, MOBILE);
        final SportRequestDto sportRequestDto = new SportRequestDto(SPORT_NAME, LEVEL_NAME);
        final SigningRequestDto signingRequestDto = new SigningRequestDto(loginRequestDto, userRequestDto, singletonList(sportRequestDto), singletonList(HOBBIES));

        when(passwordEncoder.encode(PASSWORD))
                .thenReturn(PASSWORD);

        final Users userSaved = buildNewUserDefault();
        when(usersRepository.save(new Users(null, EMAIL, PASSWORD, LAST_NAME, FIRST_NAME, MOBILE, PROFILE_PICTURE, GENRE, BIRTHDAY,
                false, LocalDate.now(), null)))
                .thenReturn(userSaved);

        final Hobbies moviesHobbies = new Hobbies(ID, HOBBIES);

        when(hobbiesRepository.findByLabel(HOBBIES))
                .thenReturn(Optional.of(moviesHobbies));

        doNothing().when(userHobbiesRepository).save(userSaved.id(), moviesHobbies.id());

        final Sport sport = new Sport(ID, SPORT_NAME);
        when(sportRepository.findByLabel(SPORT_NAME))
                .thenReturn(Optional.of(sport));

        final Level level = new Level(ID, LEVEL_NAME);
        when(levelRepository.findByLabel(LEVEL_NAME))
                .thenReturn(Optional.of(level));
        doNothing().when(userFavoriteSportRepository).save(userSaved.id(), sport.id(), level.id());

        when(usersRepository.findByEmail(EMAIL))
                .thenReturn(Optional.of(userSaved));

        when(passwordEncoder.matches(loginRequestDto.password(), PASSWORD)).thenReturn(true);

        final LoginResponseDto loginResponse = loginService.signingAndLogin(signingRequestDto);
        assertThat(loginResponse.email())
                .isEqualTo(EMAIL);

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

        when(usersRepository.findByEmail(EMAIL)).thenReturn(Optional.of(buildNewUser()));
        when(passwordEncoder.matches(loginRequestDto.password(), PASSWORD)).thenReturn(false);

        assertThatThrownBy(() -> loginService.login(loginRequestDto))
                .hasMessageContaining("Le mot de passe est incorrect")
                .isInstanceOf(AuthenticationException.class);
    }

    @Test
    void login_should_find_user_and_generate_token() {
        final LoginRequestDto loginRequestDto = new LoginRequestDto(EMAIL, PASSWORD);
        final Users users = buildNewUser();

        when(usersRepository.findByEmail(EMAIL)).thenReturn(Optional.of(users));
        when(passwordEncoder.matches(loginRequestDto.password(), PASSWORD)).thenReturn(true);

        final LoginResponseDto loginResponseDto = loginService.login(loginRequestDto);
        assertThat(loginResponseDto.email()).isEqualTo(users.email());
    }
}
