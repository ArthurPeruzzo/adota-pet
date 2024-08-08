package com.adotapet.adotaPet.config.database.entity;

import com.adotapet.adotaPet.shared.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "animal_information")
@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimalInformationEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "animal_id", nullable = false)
    private AnimalEntity animal;

    @Column(name = "about")
    private String about;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "photo")
    private String photo;

    @Column(name = "location")
    private String location;
}
