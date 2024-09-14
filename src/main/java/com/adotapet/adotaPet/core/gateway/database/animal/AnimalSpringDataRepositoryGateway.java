package com.adotapet.adotaPet.core.gateway.database.animal;

import com.adotapet.adotaPet.config.database.entity.AnimalEntity;
import com.adotapet.adotaPet.config.database.entity.AnimalInformationEntity;
import com.adotapet.adotaPet.config.database.entity.OrganizationEntity;
import com.adotapet.adotaPet.config.database.repository.animal.AnimalEntityRepository;
import com.adotapet.adotaPet.config.database.repository.animal.AnimalEntityRepositoryImplQueryDsl;
import com.adotapet.adotaPet.core.domain.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class AnimalSpringDataRepositoryGateway implements AnimalRepositoryGateway {

    private final AnimalEntityRepository animalEntityRepository;
    private final AnimalEntityRepositoryImplQueryDsl animalEntityRepositoryImplQueryDsl;
    private static final String urlPhoto = "https://raw.githubusercontent.com/ArthurPeruzzo/imagens/master/";

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

    @Override
    public Page<Animal> findByFilter(FilterAnimal filter) {
        return animalEntityRepositoryImplQueryDsl.findByFilter(filter)
                .map(this::animalEntityToAnimalDomain);
    }

    private Animal animalEntityToAnimalDomain(AnimalEntity entity) {
        final var animalInformation = entity.getAnimalInformation();
        return Animal.builder()
                .id(entity.getId())
                .name(entity.getName())
                .age(Age.toDomain(entity.getBirthYear(), entity.getBirthMonth()))
                .weight(entity.getWeight())
                .size(entity.getSize())
                .specie(entity.getSpecie())
                .race(entity.getRace())
                .sex(entity.getSex())
                .information(animalInformationEntityToAnimalInformation(animalInformation))
                .organization(organizationEntityToOrganization(entity.getOrganization()))
            .build();
    }

    private static AnimalInformation animalInformationEntityToAnimalInformation(AnimalInformationEntity animalInformationEntity) {
        return AnimalInformation.builder()
                .about(animalInformationEntity.getAbout())
                .status(animalInformationEntity.getStatus())
                .photo(urlPhoto + animalInformationEntity.getPhoto())
                .location(animalInformationEntity.getLocation())
                .build();
    }

    private static Organization organizationEntityToOrganization(OrganizationEntity organizationEntity) {
        return Organization.builder()
                .name(organizationEntity.getName())
                .phone(organizationEntity.getPhone())
                .location(organizationEntity.getLocation())
                .build();
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
                .organization(new OrganizationEntity(animal.getOrganization().getId()))
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