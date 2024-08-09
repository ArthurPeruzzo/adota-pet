package com.adotapet.adotaPet.core.gateway.database;

import com.adotapet.adotaPet.config.database.entity.AnimalEntity;
import com.adotapet.adotaPet.config.database.entity.AnimalInformationEntity;
import com.adotapet.adotaPet.config.database.repository.AnimalEntityRepository;
import com.adotapet.adotaPet.core.domain.Animal;
import com.adotapet.adotaPet.core.domain.AnimalInformation;
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
        AnimalEntity animalEntity = animalToAnimalEntity(animal);

        AnimalInformation information = animal.getInformation();
        AnimalInformationEntity animalInformationEntity = animalInformationToAnimalInformationEntity(information, animalEntity);

        animalEntity.setAnimalInformation(animalInformationEntity);
        return animalEntity;
    }

    private static AnimalEntity animalToAnimalEntity(Animal animal) {
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

    private static AnimalInformationEntity animalInformationToAnimalInformationEntity(AnimalInformation information, AnimalEntity animalEntity) {
        return AnimalInformationEntity.builder()
                .animal(animalEntity)
                .about(information.getAbout())
                .status(information.getStatus())
                .photo(information.getPhoto())
                .location(information.getLocation())
                .build();
    }
}