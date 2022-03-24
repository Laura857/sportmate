package com.example.sportmate.service;

import com.example.sportmate.entity.Users;
import com.example.sportmate.exception.BadRequestException;
import com.example.sportmate.exception.NotFoundException;
import com.example.sportmate.record.user.UserDataDto;
import com.example.sportmate.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.sportmate.enumeration.ErrorMessageEnum.USER_NOT_FOUND;
import static com.example.sportmate.mapper.UsersMapper.buildUserData;
import static com.example.sportmate.mapper.UsersMapper.buildUsers;

@Service
@AllArgsConstructor
public class UserService {
    private final UsersRepository usersRepository;
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

    public void updatePassword(final Integer userId, final String password) {
        final Users userSaved = usersRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND.getMessage()));

        final String passwordEncoded = passwordEncoder.encode(password);

        usersRepository.save(buildUsers(userSaved, passwordEncoded));
    }

    public void deleteUser(final Integer userId) {
        usersRepository.deleteById(userId);
    }
}
