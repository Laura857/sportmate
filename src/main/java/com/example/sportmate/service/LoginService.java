package com.example.sportmate.service;

import com.example.sportmate.entity.Hobbies;
import com.example.sportmate.entity.Level;
import com.example.sportmate.entity.Sport;
import com.example.sportmate.entity.Users;
import com.example.sportmate.exception.AuthenticationException;
import com.example.sportmate.exception.NotFoundException;
import com.example.sportmate.record.authentification.login.LoginRequestDto;
import com.example.sportmate.record.authentification.login.LoginResponseDto;
import com.example.sportmate.record.authentification.signing.SigningRequestDto;
import com.example.sportmate.repository.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.example.sportmate.mapper.UsersMapper.buildUsers;
import static java.util.Objects.nonNull;


@Service
@AllArgsConstructor
public class LoginService {
    private final UsersRepository usersRepository;
    private final UserHobbiesRepository userHobbiesRepository;
    private final HobbiesRepository hobbiesRepository;
    private final SportRepository sportRepository;
    private final LevelRepository levelRepository;
    private final UserFavoriteSportRepository userFavoriteSportRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public LoginResponseDto signingAndLogin(final SigningRequestDto signingRequest) {
        try {
            final Users userSaved = saveUser(signingRequest);
            saveAllHobbies(signingRequest, userSaved);
            saveAllFavoriteSports(signingRequest, userSaved);
            return login(signingRequest.login());
        } catch (final Exception exception) {
            if (nonNull(exception.getCause()) && exception.getCause().toString().contains("duplicate key value violates unique constraint \"users_email_key\"")) {
                throw new AuthenticationException("Un compte existe déjà pour cette adresse email.");
            }
            throw new AuthenticationException(exception.getMessage());
        }
    }

    private void saveAllFavoriteSports(final SigningRequestDto signingRequest, final Users userSaved) {
        signingRequest.sports().forEach(sport -> {
            final Sport sportFound = sportRepository.findByLabel(sport.name())
                    .orElseThrow(() -> new NotFoundException("Inscription erreur : sport non trouvé"));
            final Level levelFound = levelRepository.findByLabel(sport.level())
                    .orElseThrow(() -> new NotFoundException("Inscription erreur : niveau non trouvé"));
            userFavoriteSportRepository.save(userSaved.id(), sportFound.id(), levelFound.id());
        });
    }

    private Users saveUser(final SigningRequestDto signingRequest) {
        final String passwordEncoded = passwordEncoder.encode(signingRequest.login().password());
        final Users userToSaved = buildUsers(signingRequest, passwordEncoded);
        return usersRepository.save(userToSaved);
    }

    private void saveAllHobbies(final SigningRequestDto signingRequest, final Users userSaved) {
        signingRequest.hobbies()
                .forEach(hobbies -> {
                            final Hobbies hobbiesFound = hobbiesRepository.findByLabel(hobbies)
                                    .orElseThrow(() -> new NotFoundException("Inscription erreur : hobbies non trouvé"));
                            userHobbiesRepository.save(userSaved.id(), hobbiesFound.id());
                        }
                );
    }

    public LoginResponseDto login(final LoginRequestDto loginRequestDto) {
        final Users user = usersRepository.findByEmail(loginRequestDto.email())
                .orElseThrow(() -> new NotFoundException("Connexion refusée : utilisateur non trouvé"));
        if (isPasswordNoMatch(loginRequestDto, user)) {
            throw new AuthenticationException("Le mot de passe est incorrect");
        }
        return new LoginResponseDto(user.email(), getJWTToken(loginRequestDto.email()));
    }

    private boolean isPasswordNoMatch(final LoginRequestDto loginRequestDto, final Users user) {
        return !passwordEncoder.matches(loginRequestDto.password(), user.password());
    }

    public String getJWTToken(final String username) {
        final String secretKey = "mySecretKey";
        final List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        return Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .toList())
                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();
    }

    public boolean isEmailAlreadyUsedForAnotherAccount(final String email){
        return usersRepository.findByEmail(email).isPresent();
    }
}

