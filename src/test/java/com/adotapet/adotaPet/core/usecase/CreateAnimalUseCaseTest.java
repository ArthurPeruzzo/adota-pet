package com.adotapet.adotaPet.core.usecase;

import com.adotapet.adotaPet.core.domain.Age;
import com.adotapet.adotaPet.core.domain.Animal;
import com.adotapet.adotaPet.core.domain.AnimalInformation;
import com.adotapet.adotaPet.core.gateway.database.animal.AnimalRepositoryGateway;
import com.adotapet.adotaPet.core.usecase.animal.CreateAnimalUseCase;
import com.adotapet.adotaPet.shared.enums.Sex;
import com.adotapet.adotaPet.shared.enums.Size;
import com.adotapet.adotaPet.shared.enums.Specie;
import com.adotapet.adotaPet.shared.enums.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CreateAnimalUseCaseTest {

    @InjectMocks
    private CreateAnimalUseCase createAnimalUseCase;
    @Mock
    private AnimalRepositoryGateway animalRepositoryGateway;

    @Test
    void shouldCreateWithSuccessAnimal() {
        Animal animal = Animal.builder()
                .name("test")
                .age(Age.builder().year(1).month(1).build())
                .weight(10.00)
                .size(Size.MEDIUM)
                .specie(Specie.CAT)
                .race("Test")
                .sex(Sex.FEMALE)
                .information(AnimalInformation.builder()
                        .about("Doguinho top")
                        .status(Status.ACTIVE)
                        .photo("urlPhoto")
                        .location("São domingos SC")
                        .build())
                .build();

        createAnimalUseCase.create(animal);

        ArgumentCaptor<Animal> animalAC = ArgumentCaptor.forClass(Animal.class);

        verify(animalRepositoryGateway).create(animalAC.capture());

        Animal animalValue = animalAC.getValue();

        assertEquals(animal.getName(), animalValue.getName());
        assertEquals(animal.getAge().getYear(), animalValue.getAge().getYear());
        assertEquals(animal.getAge().getMonth(), animalValue.getAge().getMonth());
        assertEquals(animal.getWeight(), animalValue.getWeight());
        assertEquals(animal.getSize(), animalValue.getSize());
        assertEquals(animal.getSpecie(), animalValue.getSpecie());
        assertEquals(animal.getRace(), animalValue.getRace());
        assertEquals(animal.getSex(), animalValue.getSex());
        assertEquals(animal.getInformation().getAbout(), animalValue.getInformation().getAbout());
        assertEquals(animal.getInformation().getStatus(), animalValue.getInformation().getStatus());
        assertEquals(animal.getInformation().getPhoto(), animalValue.getInformation().getPhoto());
        assertEquals(animal.getInformation().getLocation(), animalValue.getInformation().getLocation());
    }
}