package com.adotapet.adotaPet.core.gateway.database;

import com.adotapet.adotaPet.config.database.entity.AnimalEntity;
import com.adotapet.adotaPet.config.database.repository.AnimalEntityRepository;
import com.adotapet.adotaPet.core.domain.Animal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class AnimalSpringDataRepositoryGateway implements AnimalRepositoryGateway {

    private final AnimalEntityRepository animalEntityRepository;

    @Override
    public void create(Animal animal) {
        try {
            AnimalEntity animalEntity = domainToEntity(animal);
            animalEntityRepository.save(animalEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("Erro ao salvar animal");
        }
    }

    private AnimalEntity domainToEntity(Animal animal) {
        return AnimalEntity.builder()
                .name(animal.getName())
                .birthYear(animal.getAge().getYear())
                .birthMonth(animal.getAge().getMonth())
                .weight(animal.getWeight())
                .size(animal.getSize())
                .specie(animal.getSpecie())
                .race(animal.getRace())
                .sex(animal.getSex())
                .build();
    }
}