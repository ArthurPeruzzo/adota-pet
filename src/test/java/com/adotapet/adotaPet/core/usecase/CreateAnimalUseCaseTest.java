package com.adotapet.adotaPet.core.usecase;

import com.adotapet.adotaPet.core.domain.Age;
import com.adotapet.adotaPet.core.domain.Animal;
import com.adotapet.adotaPet.core.gateway.database.AnimalRepositoryGateway;
import com.adotapet.adotaPet.shared.enums.Sex;
import com.adotapet.adotaPet.shared.enums.Specie;
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
                .size(30.00)
                .specie(Specie.CAT)
                .race("Test")
                .sex(Sex.FEMALE)
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
    }
}