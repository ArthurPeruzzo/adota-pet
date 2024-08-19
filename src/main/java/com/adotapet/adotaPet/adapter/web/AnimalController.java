package com.adotapet.adotaPet.adapter.web;

import com.adotapet.adotaPet.core.domain.Animal;
import com.adotapet.adotaPet.core.domain.FilterAnimal;
import com.adotapet.adotaPet.core.usecase.CreateAnimalUseCase;
import com.adotapet.adotaPet.core.usecase.FindAnimalPageableByFilterUseCase;
import com.adotapet.adotaPet.shared.json.AnimalJson;
import com.adotapet.adotaPet.shared.json.FilterAnimalJson;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/animal")
public class AnimalController {

	private final CreateAnimalUseCase createAnimalUseCase;
	private final FindAnimalPageableByFilterUseCase findAnimalPageableByFilterUseCase;

	@PostMapping("/create")
	public void create(@RequestBody @Valid AnimalJson animalJson) {
		log.info("create animal, AnimalJson={}", animalJson);
		Animal animal = animalJson.toDomain(animalJson);
		createAnimalUseCase.create(animal);
		log.info("created animal");
	}

	@GetMapping("/find-by-filter")
	public ResponseEntity<Page<Animal>> findByFilter(@RequestBody @Valid FilterAnimalJson filterAnimalJson) {
		log.info("find animal by filter, filter={}", filterAnimalJson);
		FilterAnimal filter = filterAnimalJson.toDomain(filterAnimalJson);
		Page<Animal> animals = findAnimalPageableByFilterUseCase.findByFilter(filter);
		log.info("find animal finish");
		return ResponseEntity.ok(animals);
	}
}
