package com.adotapet.adotaPet.adapter.web;

import com.adotapet.adotaPet.core.domain.Age;
import com.adotapet.adotaPet.core.domain.Animal;
import com.adotapet.adotaPet.core.domain.AnimalInformation;
import com.adotapet.adotaPet.core.usecase.CreateAnimalUseCase;
import com.adotapet.adotaPet.shared.json.AnimalInformationJson;
import com.adotapet.adotaPet.shared.json.AnimalJson;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/animal")
public class AnimalController {

    private final CreateAnimalUseCase createAnimalUseCase;

    @PostMapping("/create")
    public void create(@RequestBody @Valid AnimalJson animalJson) {
        log.info("create animal, AnimalJson={}", animalJson);
        Animal animal = animalJsonToDomain(animalJson);
        createAnimalUseCase.create(animal);
        log.info("created animal");
    }

    private Animal animalJsonToDomain(AnimalJson animalJson) {
        AnimalInformationJson information = animalJson.getInformation();
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
                .information(AnimalInformation.builder()
                        .about(information.getAbout())
                        .status(information.getStatus())
                        .photo(information.getPhoto())
                        .location(information.getLocation())
                        .build())
                .build();
    }
}
