package com.adotapet.adotaPet.core.domain;

import com.adotapet.adotaPet.shared.enums.Sex;
import com.adotapet.adotaPet.shared.enums.Specie;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Animal {

    private Long id;
    private String name;
    private Age age;
    private Double weight;
    private Double size;
    private Specie specie;
    private String race;
    private Sex sex;

}
