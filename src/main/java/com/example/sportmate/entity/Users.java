package com.example.sportmate.entity;

import com.example.sportmate.enumeration.GenreEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

import static com.example.sportmate.record.Regex.*;
import static javax.persistence.EnumType.STRING;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id
//    @SequenceGenerator(allocationSize = 1, name = "USER_ID_SEQUENCE_GENERATOR", sequenceName = "USER_SEQ")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_ID_SEQUENCE_GENERATOR")
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "native"
    )
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )
    private Integer id;

    @NotBlank(message = "L'email est obligatoire.")
    @Pattern(regexp = EMAIL, message = "L'email ne respecte pas le bon format")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire.")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "Le nom est obligatoire.")
    @Column(nullable = false)
    private String lastName;

    @NotBlank(message = "Le prénom est obligatoire.")
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "Le numéro de téléphone est obligatoire.")
    @Pattern(regexp = MOBILE_PHONE, message = "Le numéro de téléphone ne respecte pas le bon format")
    @Column(nullable = false)
    private String mobile;

    private String profilePicture;

    @NotNull(message = "Le genre est obligatoire.")
    @Column(nullable = false)
    @Enumerated(value = STRING)
    private GenreEnum genre;

    private LocalDate birthday;

    private boolean consents;

    @NotNull(message = "La date de création est obligatoire.")
    @Column(nullable = false)
    private LocalDate created;

    private LocalDate updated;
}
