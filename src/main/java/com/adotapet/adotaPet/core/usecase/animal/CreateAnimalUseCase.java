package com.adotapet.adotaPet.core.usecase.animal;

import com.adotapet.adotaPet.core.domain.Animal;
import com.adotapet.adotaPet.core.gateway.database.animal.AnimalRepositoryGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAnimalUseCase {

    private final AnimalRepositoryGateway animalRepositoryGateway;

    public void create(Animal animal) {
        animalRepositoryGateway.create(animal);
    }
}
