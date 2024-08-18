package com.adotapet.adotaPet.config.database.entity;

import com.adotapet.adotaPet.shared.enums.Sex;
import com.adotapet.adotaPet.shared.enums.Size;
import com.adotapet.adotaPet.shared.enums.Specie;
import jakarta.persistence.*;
import lombok.*;

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

    @Setter
    @OneToOne(mappedBy = "animal", cascade = CascadeType.ALL)
    private AnimalInformationEntity animalInformation;

//	@Setter
//	@OneToOne(mappedBy = "animal", cascade = CascadeType.ALL)
//	private OrganizationEntity organization;

    @Column(name = "name")
    private String name;

    @Column(name = "birth_year")
    private Integer birthYear;

    @Column(name = "birth_month")
    private Integer birthMonth;

    @Column(name = "weight")
    private Double weight;

    @Enumerated(EnumType.STRING)
    @Column(name = "size")
    private Size size;

    @Enumerated(EnumType.STRING)
    @Column(name = "specie")
    private Specie specie;

    @Column(name = "race")
    private String race;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex")
    private Sex sex;
}
