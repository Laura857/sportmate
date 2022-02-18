package com.example.sportmate.service;

import com.example.sportmate.DataTest;
import com.example.sportmate.entity.Hobbies;
import com.example.sportmate.entity.Level;
import com.example.sportmate.entity.Sport;
import com.example.sportmate.entity.Users;
import com.example.sportmate.exception.AuthenticationException;
import com.example.sportmate.exception.NotFoundException;
import com.example.sportmate.record.LoginRequestDto;
import com.example.sportmate.record.LoginResponseDto;
import com.example.sportmate.record.signin.SigningRequestDto;
import com.example.sportmate.record.signin.SportRequestDto;
import com.example.sportmate.record.signin.UserRequestDto;
import com.example.sportmate.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static com.example.sportmate.DataTest.buildNewUser;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
    void signingAndLogin_should_saved_a_new_user() {
        LoginRequestDto loginRequestDto = new LoginRequestDto(EMAIL, PASSWORD);
        UserRequestDto userRequestDto = new UserRequestDto(PROFILE_PICTURE, LAST_NAME, FIRST_NAME, GENRE, BIRTHDAY, MOBILE);
        SportRequestDto sportRequestDto = new SportRequestDto(SPORT_NAME, LEVEL_NAME);
        SigningRequestDto signinRequestDto = new SigningRequestDto(loginRequestDto, userRequestDto, singletonList(sportRequestDto), singletonList(HOBBIES));

        when(passwordEncoder.encode(PASSWORD))
                .thenReturn(PASSWORD);

        Users userSaved = new Users(ID, EMAIL, PASSWORD, LAST_NAME, FIRST_NAME, MOBILE, PROFILE_PICTURE, GENRE, BIRTHDAY,
                false, LocalDate.now(), null);
        when(usersRepository.save(new Users(null, EMAIL, PASSWORD, LAST_NAME, FIRST_NAME, MOBILE, PROFILE_PICTURE, GENRE, BIRTHDAY,
                false, LocalDate.now(), null)))
                .thenReturn(userSaved);

        Hobbies moviesHobbies = new Hobbies(ID, HOBBIES);

        when(hobbiesRepository.findByLabel(HOBBIES))
                .thenReturn(Optional.of(moviesHobbies));

        doNothing().when(userHobbiesRepository).save(userSaved.id(), moviesHobbies.id());

        Sport sport = new Sport(ID, SPORT_NAME);
        when(sportRepository.findByLabel(SPORT_NAME))
                .thenReturn(Optional.of(sport));

        Level level = new Level(ID, LEVEL_NAME);
        when(levelRepository.findByLabel(LEVEL_NAME))
                .thenReturn(Optional.of(level));
        doNothing().when(userFavoriteSportRepository).save(userSaved.id(), sport.id(), level.id());

        when(usersRepository.findByEmail(EMAIL))
                .thenReturn(Optional.of(userSaved));

        when(passwordEncoder.matches(loginRequestDto.password(), PASSWORD)).thenReturn(true);

        LoginResponseDto loginResponse = loginService.signingAndLogin(signinRequestDto);
        assertThat(loginResponse.email())
                .isEqualTo(EMAIL);

        assertThat(loginResponse.token())
                .isNotBlank();
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
