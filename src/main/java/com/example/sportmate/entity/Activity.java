package com.example.sportmate.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Activity {
    @Id
//    @SequenceGenerator(allocationSize = 1, name = "ACTIVITY_ID_SEQUENCE_GENERATOR", sequenceName = "ACTIVITY_SEQ")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACTIVITY_ID_SEQUENCE_GENERATOR")
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "native"
    )
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )
    private Integer id;

    private boolean isEvent;

    private String activityName;

    @NotNull(message = "Le date est obligatoire.")
    @Column(nullable = false)
    private LocalDateTime activityDate;

    @NotNull(message = "Le créateur est obligatoire.")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "creator", nullable = false)
    private Users creator;

    @NotBlank(message = "L'addresse est obligatoire'.")
    @Column(nullable = false)
    private String address;

    @NotBlank(message = "La longitude est obligatoire.")
    @Column(nullable = false)
    private String longitude;

    @NotBlank(message = "La latitude est obligatoire.")
    @Column(nullable = false)
    private String latitude;

    private Integer participant;

    @NotNull(message = "Le sport est obligatoire.")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "sport", nullable = false)
    private Sport sport;

    @NotNull(message = "Le niveau est obligatoire.")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "activityLevel", nullable = false)
    private Level activityLevel;

    private String description;

    @NotBlank(message = "Le contact est obligatoire.")
    @Column(nullable = false)
    private String contact;

    @NotNull(message = "La date de création est obligatoire.")
    @Column(nullable = false)
    private LocalDate created;

    private LocalDate updated;
}
