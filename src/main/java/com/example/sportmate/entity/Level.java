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
public class Level {
    @Id
//    @SequenceGenerator(allocationSize = 1, name = "LEVEL_ID_SEQUENCE_GENERATOR", sequenceName = "LEVEL_SEQ")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LEVEL_ID_SEQUENCE_GENERATOR")
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
