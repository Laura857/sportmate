package com.example.sportmate.service;

import com.example.sportmate.DataTest;
import com.example.sportmate.entity.Users;
import com.example.sportmate.exception.BadRequestException;
import com.example.sportmate.exception.NotFoundException;
import com.example.sportmate.record.user.UpdatePasswordRequestDto;
import com.example.sportmate.record.user.UserDataDto;
import com.example.sportmate.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static com.example.sportmate.DataTest.buildDefaultUserData;
import static com.example.sportmate.DataTest.buildNewUserDefault;
import static com.example.sportmate.enumeration.ErrorMessageEnum.PASSWORD_BAD_REQUEST;
import static com.example.sportmate.enumeration.ErrorMessageEnum.USER_NOT_FOUND;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest implements DataTest {
    @Autowired
    UserService userService;

    @MockBean
    UsersRepository usersRepository;

    @MockBean
    PasswordEncoder passwordEncoder;

    @MockBean
    PasswordService passwordService;

    @Test
    void getUser_should_throw_exception_when_user_id_not_exists() {
        when(usersRepository.findById(ID))
                .thenReturn(empty());

        assertThatThrownBy(() -> userService.getUser(ID))
                .hasMessageContaining(USER_NOT_FOUND.getMessage())
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void getUser_should_return_user_when_user_id_exists() {
        final Users userFound = buildNewUserDefault();
        when(usersRepository.findById(ID))
                .thenReturn(of(userFound));

        final UserDataDto userResponse = new UserDataDto(
                userFound.profilePicture(),
                userFound.consents(),
                userFound.email(),
                userFound.lastName(),
                userFound.firstName(),
                userFound.genre(),
                userFound.birthday(),
                userFound.mobile());

        assertThat(userService.getUser(ID))
                .isEqualTo(userResponse);
    }

    @Test
    void updateUser_should_throw_exception_when_user_id_not_exists() {
        when(usersRepository.findById(ID))
                .thenReturn(empty());

        assertThatThrownBy(() -> userService.updateUser(ID, buildDefaultUserData()))
                .hasMessageContaining(USER_NOT_FOUND.getMessage())
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void updateUser_should_throw_exception_when_user_email_already_used_by_another_user() {
        final Users userFound = buildNewUserDefault();
        when(usersRepository.findById(ID))
                .thenReturn(of(userFound));

        when(usersRepository.findByEmailAndIdNot(EMAIL_OTHER, ID))
                .thenReturn(of(userFound));

        assertThatThrownBy(() -> userService.updateUser(ID, buildDefaultUserData(EMAIL_OTHER)))
                .hasMessageContaining("L'email que vous avez choisi est déjà utilisé par un autre utilisateur. Veuillez en choisir un autre.")
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void updateUser_should_return_user_saved_when_user_email_not_used_by_another_user() {
        final Users userFound = buildNewUserDefault();
        when(usersRepository.findById(ID))
                .thenReturn(of(userFound));

        when(usersRepository.findByEmailAndIdNot(EMAIL, ID))
                .thenReturn(empty());

        final UserDataDto userData = buildDefaultUserData();
        assertThat(userService.updateUser(ID, userData))
                .isEqualTo(userData);
    }

    @Test
    void updatePassword_should_throw_exception_when_user_id_not_exists() {
        when(usersRepository.findById(ID))
                .thenReturn(empty());

        assertThatThrownBy(() -> userService.updatePassword(ID, new UpdatePasswordRequestDto(PASSWORD, OTHER_PASSWORD)))
                .hasMessageContaining(USER_NOT_FOUND.getMessage())
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void updatePassword_should_throw_exception_when_user_old_password_not_match() {
        final Users userFound = buildNewUserDefault();

        when(usersRepository.findById(ID))
                .thenReturn(of(userFound));

        when(passwordService.isPasswordNoMatch(userFound.password(), PASSWORD))
                .thenReturn(true);

        assertThatThrownBy(() -> userService.updatePassword(ID, new UpdatePasswordRequestDto(PASSWORD, OTHER_PASSWORD)))
                .hasMessageContaining(PASSWORD_BAD_REQUEST.getMessage())
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void updatePassword_should_update_only_user_password_when_user_id_exists() {
        final Users userFound = buildNewUserDefault();

        when(usersRepository.findById(ID))
                .thenReturn(of(userFound));

        when(passwordService.isPasswordNoMatch(userFound.password(), PASSWORD))
                .thenReturn(false);

        when(passwordEncoder.encode(OTHER_PASSWORD))
                .thenReturn(OTHER_PASSWORD);

        assertDoesNotThrow(() -> userService.updatePassword(ID, new UpdatePasswordRequestDto(PASSWORD, OTHER_PASSWORD)));

        final Users userToSaved = new Users(
                userFound.id(),
                userFound.email(),
                OTHER_PASSWORD,
                userFound.lastName(),
                userFound.firstName(),
                userFound.mobile(),
                userFound.profilePicture(),
                userFound.genre(),
                userFound.birthday(),
                userFound.consents(),
                userFound.created(),
                LocalDate.now());
        verify(usersRepository).save(userToSaved);
    }

    @Test
    void deleteUser_should_delete_user_when_user_id_exists() {
        doNothing().when(usersRepository).deleteById(ID);

        assertDoesNotThrow(() -> userService.deleteUser(ID));

    }
}