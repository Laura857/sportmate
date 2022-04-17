package com.example.sportmate.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Sport {
    @Id
//    @SequenceGenerator(allocationSize = 1, name = "SPORT_ID_SEQUENCE_GENERATOR", sequenceName = "SPORT_SEQ")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SPORT_ID_SEQUENCE_GENERATOR")
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "native"
    )
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )
    private Integer id;

    @NotBlank(message = "Le label est obligatoire.")
    @Column(nullable = false)
    private String label;
}
