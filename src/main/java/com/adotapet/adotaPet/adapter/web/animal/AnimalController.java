package com.adotapet.adotaPet.adapter.web.animal;

import com.adotapet.adotaPet.core.domain.Animal;
import com.adotapet.adotaPet.core.domain.FilterAnimal;
import com.adotapet.adotaPet.core.usecase.animal.CreateAnimalUseCase;
import com.adotapet.adotaPet.core.usecase.animal.FindAnimalPageableByFilterUseCase;
import com.adotapet.adotaPet.shared.json.animal.AnimalJson;
import com.adotapet.adotaPet.shared.json.animal.AnimalResponseJson;
import com.adotapet.adotaPet.shared.json.animal.FilterAnimalJson;
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

	@PostMapping("/find-by-filter")
	public ResponseEntity<Page<AnimalResponseJson>> findByFilter(@RequestBody @Valid FilterAnimalJson filterAnimalJson) {
		log.info("find animal by filter, filter={}", filterAnimalJson);
		FilterAnimal filter = filterAnimalJson.toDomain(filterAnimalJson); //TODO aqui não deve ser dominio
		Page<AnimalResponseJson> animalsPage = findAnimalPageableByFilterUseCase.findByFilter(filter).map(AnimalResponseJson::new);
		log.info("find animal finish");
		return ResponseEntity.ok(animalsPage);
	}
}
