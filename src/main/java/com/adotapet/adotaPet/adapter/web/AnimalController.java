package com.adotapet.adotaPet.adapter.web;

import com.adotapet.adotaPet.core.domain.Age;
import com.adotapet.adotaPet.core.domain.Animal;
import com.adotapet.adotaPet.core.usecase.CreateAnimalUseCase;
import com.adotapet.adotaPet.shared.json.AnimalJson;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/animal")
public class AnimalController {

    private final CreateAnimalUseCase createAnimalUseCase;

    @PostMapping("/create")
    public void create(@RequestBody @Valid AnimalJson animalJson) {
        Animal animal = animalJsonToDomain(animalJson);
        createAnimalUseCase.create(animal);
    }

    private Animal animalJsonToDomain(AnimalJson animalJson) {
        return Animal.builder()
                .name(animalJson.getName())
                .age(Age.builder()
                        .year(animalJson.getYear())
                        .month(animalJson.getMonth()).build())
                .weight(animalJson.getWeight())
                .size(animalJson.getSize())
                .specie(animalJson.getSpecie())
                .race(animalJson.getRace())
                .sex(animalJson.getSex())
                .build();
    }
}
