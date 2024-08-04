package com.adotapet.adotaPet.config.database.entity;

import com.adotapet.adotaPet.shared.enums.Sex;
import com.adotapet.adotaPet.shared.enums.Specie;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "animal")
@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimalEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "year")
    private Integer year;

    @Column(name = "month")
    private Integer month;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "size")
    private Double size;

    @Enumerated(EnumType.STRING)
    @Column(name = "specie")
    private Specie specie;

    @Column(name = "race")
    private String race;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex")
    private Sex sex;
}
