package com.adotapet.adotaPet.adapter.web;

import com.adotapet.adotaPet.core.domain.Animal;
import com.adotapet.adotaPet.core.usecase.CreateAnimalUseCase;
import com.adotapet.adotaPet.shared.json.AnimalJson;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/animal")
public class AnimalController {

    private final CreateAnimalUseCase createAnimalUseCase;

    @PostMapping("/create")
    public void create(@RequestBody @Valid AnimalJson animalJson) {
        log.info("create animal, AnimalJson={}", animalJson);
        Animal animal = animalJson.toDomain(animalJson);
        createAnimalUseCase.create(animal);
        log.info("created animal");
    }
}
