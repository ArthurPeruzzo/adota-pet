package com.adotapet.adotaPet.shared.json.animal;

import com.adotapet.adotaPet.core.domain.Age;
import com.adotapet.adotaPet.core.domain.Animal;
import com.adotapet.adotaPet.core.domain.AnimalInformation;
import com.adotapet.adotaPet.shared.enums.Sex;
import com.adotapet.adotaPet.shared.enums.Size;
import com.adotapet.adotaPet.shared.enums.Specie;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class AnimalJson {

    @NotNull(message = "Nome deve ser preenchido")
    private String name;

    @Min(value = 0, message = "O campo ano não pode ser negativo")
    private Integer year;

    @Min(value = 0, message = "O campo mês não pode ser negativo")
    private Integer month;

    @Min(value = 0, message = "O peso do animal deve ser negativo")
    @NotNull(message = "O peso deve ser preenchido!")
    private Double weight;

    @NotNull(message = "O porte deve ser preenchido")
    private Size size;

    @NotNull(message = "A espécie deve ser preenchida")
    private Specie specie;

    private String race;

    @NotNull(message = "O sexo do animal deve ser preenchido")
    private Sex sex;

    @Valid
    @NotNull
    private AnimalInformationJson information;

    @NotNull(message = "A organização deve ser informada")
    private Long organizationId;

    public Animal toDomain(AnimalJson animalJson) {
        AnimalInformationJson information = animalJson.getInformation();
        return Animal.builder()
                .name(animalJson.getName())
                .age(Age.toDomain(animalJson.getYear(), animalJson.getMonth()))
                .weight(animalJson.getWeight())
                .size(animalJson.getSize())
                .specie(animalJson.getSpecie())
                .race(animalJson.getRace())
                .sex(animalJson.getSex())
                .information(AnimalInformation.toDomain(information))
                .build();
    }
}
