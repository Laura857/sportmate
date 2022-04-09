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
import com.example.sportmate.record.external.SendinblueSendEmailRequest;
import com.example.sportmate.record.external.ToDetails;
import com.example.sportmate.repository.*;
import com.example.sportmate.service.external.SendinblueService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import static com.example.sportmate.mapper.UsersMapper.buildUsers;
import static java.util.Collections.singletonList;
import static java.util.Objects.nonNull;


@Service
@AllArgsConstructor
public class AuthService {
    private static final int RESET_PASSWORD_TEMPLATE_ID = 5;
    private final UsersRepository usersRepository;
    private final UserHobbiesRepository userHobbiesRepository;
    private final HobbiesRepository hobbiesRepository;
    private final SportRepository sportRepository;
    private final LevelRepository levelRepository;
    private final UserFavoriteSportRepository userFavoriteSportRepository;
    private final PasswordEncoder passwordEncoder;
    private final SendinblueService sendinblueService;

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

    public boolean isEmailAlreadyUsedForAnotherAccount(final String email) {
        return usersRepository.findByEmail(email).isPresent();
    }

    public void resetPassword(final String email) {
        usersRepository.findByEmail(email)
                .ifPresent(user -> sendinblueService.sendEmail(new SendinblueSendEmailRequest(
                        singletonList(new ToDetails(email)), RESET_PASSWORD_TEMPLATE_ID)));
    }

    private void sendSftpEmail() {
        // Recipient's email ID needs to be mentioned.
        final String username = "luthylou@gmail.com";
        final String password = "laura14JO";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(prop,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("luthylou@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("babylove99@orange.fr")
            );
            message.setSubject("Réinitialisation de votre mot de passe");
//                message.setText("This is actual message");
            final String msg = "<h1>This is actual message embedded in HTML tags</h1>";

            final MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

            final Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (final MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}

