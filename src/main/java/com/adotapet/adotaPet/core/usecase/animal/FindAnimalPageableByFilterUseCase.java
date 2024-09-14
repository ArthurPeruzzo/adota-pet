package com.adotapet.adotaPet.core.usecase.animal;

import com.adotapet.adotaPet.core.domain.Animal;
import com.adotapet.adotaPet.core.domain.FilterAnimal;
import com.adotapet.adotaPet.core.gateway.database.animal.AnimalRepositoryGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindAnimalPageableByFilterUseCase {

    private final AnimalRepositoryGateway animalRepositoryGateway;

    public Page<Animal> findByFilter(FilterAnimal filter) {
        return animalRepositoryGateway.findByFilter(filter);
    }
}
