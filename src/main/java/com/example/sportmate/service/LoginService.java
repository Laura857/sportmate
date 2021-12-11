package com.example.sportmate.service;

import com.example.sportmate.entity.Users;
import com.example.sportmate.exception.AuthenticationException;
import com.example.sportmate.exception.NotFoundException;
import com.example.sportmate.record.LoginRequestDto;
import com.example.sportmate.record.LoginResponseDto;
import com.example.sportmate.record.ResponseDefaultDto;
import com.example.sportmate.record.SigninRequestDto;
import com.example.sportmate.repository.UsersRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.sportmate.mapper.UsersMapper.buildUsers;

@Service
public class LoginService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoginService(final UsersRepository usersRepository,
                        final PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<ResponseDefaultDto> signin(SigninRequestDto signinRequestDto){
        String passwordEncoded = passwordEncoder.encode(signinRequestDto.password());
        Users userToSaved = buildUsers(signinRequestDto, passwordEncoded);
        usersRepository.save(userToSaved);
        return new ResponseEntity<>(new ResponseDefaultDto("Création du compte réalisée avec succès"), HttpStatus.CREATED);
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto){
        Users user = usersRepository.findByEmail(loginRequestDto.email())
                .orElseThrow(() -> new NotFoundException("Connexion refusée : utilisateur non trouvé"));
        if(isPasswordNoMatch(loginRequestDto, user)){
            throw new AuthenticationException("Le mot de passe est incorrect");
        }
        return new LoginResponseDto(user.email(), getJWTToken(loginRequestDto.email()));
    }

    private boolean isPasswordNoMatch(LoginRequestDto loginRequestDto, Users user) {
        return !passwordEncoder.matches(loginRequestDto.password(), user.password());
    }

    public String getJWTToken(String username) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return token;
    }
}

