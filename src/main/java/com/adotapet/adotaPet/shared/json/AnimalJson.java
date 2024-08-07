package com.adotapet.adotaPet.shared.json;

import com.adotapet.adotaPet.shared.enums.Sex;
import com.adotapet.adotaPet.shared.enums.Specie;
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

    @NotNull(message = "O tamanho deve ser preenchido")
    @Min(value = 0, message = "O peso do animal deve ser negativo")
    private Double size;

    @NotNull(message = "A espécie deve ser preenchida")
    private Specie specie;

    private String race;

    @NotNull(message = "O sexo do animal deve ser preenchido")
    private Sex sex;
}
