package com.example.sportmate.service;

import com.example.sportmate.entity.Users;
import com.example.sportmate.exception.BadRequestException;
import com.example.sportmate.exception.NotFoundException;
import com.example.sportmate.record.user.UpdatePasswordRequestDto;
import com.example.sportmate.record.user.UserDataDto;
import com.example.sportmate.repository.UserActivityRepository;
import com.example.sportmate.repository.UserFavoriteSportRepository;
import com.example.sportmate.repository.UserHobbiesRepository;
import com.example.sportmate.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.sportmate.enumeration.ErrorMessageEnum.PASSWORD_BAD_REQUEST;
import static com.example.sportmate.enumeration.ErrorMessageEnum.USER_NOT_FOUND;
import static com.example.sportmate.mapper.UsersMapper.buildUserData;
import static com.example.sportmate.mapper.UsersMapper.buildUsers;

@Service
@AllArgsConstructor
public class UserService {
    private final PasswordService passwordService;
    private final UsersRepository usersRepository;
    private final UserFavoriteSportRepository userFavoriteSportRepository;
    private final UserHobbiesRepository userHobbiesRepository;
    private final UserActivityRepository userActivityRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDataDto getUser(final Integer userId) {
        final Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND.getMessage()));
        return buildUserData(user);
    }

    public UserDataDto updateUser(final Integer userId, final UserDataDto userRequest) {
        final Users userSaved = usersRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND.getMessage()));
        if (!userSaved.email().equals(userRequest.email())) {
            usersRepository.findByEmailAndIdNot(userRequest.email(), userId)
                    .ifPresent(user -> {
                        throw new BadRequestException("L'email que vous avez choisi est déjà utilisé par un autre utilisateur. Veuillez en choisir un autre.");
                    });
        }
        usersRepository.save(buildUsers(userRequest, userSaved));
        return userRequest;
    }

    public void updatePassword(final Integer userId, final UpdatePasswordRequestDto updatePasswordRequestDto) {
        final Users userSaved = usersRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND.getMessage()));

        if (passwordService.isPasswordNoMatch(updatePasswordRequestDto.oldPassword(), userSaved.password())) {
            throw new BadRequestException(PASSWORD_BAD_REQUEST.getMessage());
        }
        final String passwordEncoded = passwordEncoder.encode(updatePasswordRequestDto.newPassword());
        usersRepository.save(buildUsers(userSaved, passwordEncoded));
    }

    @Transactional
    public void deleteUser(final Integer userId) {
        userHobbiesRepository.deleteAllByUserId(userId);
        userFavoriteSportRepository.deleteAllByUserId(userId);
        userActivityRepository.deleteAllByUserId(userId);
        usersRepository.deleteById(userId);
    }
}
