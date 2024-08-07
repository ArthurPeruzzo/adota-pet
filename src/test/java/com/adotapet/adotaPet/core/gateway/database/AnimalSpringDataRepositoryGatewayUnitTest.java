package com.adotapet.adotaPet.core.gateway.database;

import com.adotapet.adotaPet.config.database.entity.AnimalEntity;
import com.adotapet.adotaPet.config.database.repository.AnimalEntityRepository;
import com.adotapet.adotaPet.core.domain.Age;
import com.adotapet.adotaPet.core.domain.Animal;
import com.adotapet.adotaPet.shared.enums.Sex;
import com.adotapet.adotaPet.shared.enums.Specie;
import org.apache.logging.log4j.util.InternalException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AnimalSpringDataRepositoryGatewayUnitTest {

    @InjectMocks
    private AnimalSpringDataRepositoryGateway repositoryGateway;
    @Mock
    private AnimalEntityRepository animalEntityRepository;

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

        repositoryGateway.create(animal);

        ArgumentCaptor<AnimalEntity> animalEntityAC = ArgumentCaptor.forClass(AnimalEntity.class);

        verify(animalEntityRepository).save(animalEntityAC.capture());

        AnimalEntity animalValue = animalEntityAC.getValue();

        assertEquals(animal.getName(), animalValue.getName());
        assertEquals(animal.getAge().getYear(), animalValue.getBirthYear());
        assertEquals(animal.getAge().getMonth(), animalValue.getBirthMonth());
        assertEquals(animal.getWeight(), animalValue.getWeight());
        assertEquals(animal.getSize(), animalValue.getSize());
        assertEquals(animal.getSpecie(), animalValue.getSpecie());
        assertEquals(animal.getRace(), animalValue.getRace());
        assertEquals(animal.getSex(), animalValue.getSex());
    }

    @Test
    void shouldThrowExceptionWhenCreateAnimal() {
        Animal animal = Animal.builder()
                .name("test")
                .age(Age.builder().year(1).month(1).build())
                .weight(10.00)
                .size(30.00)
                .specie(Specie.CAT)
                .race("Test")
                .sex(Sex.FEMALE)
                .build();

        Mockito.when(animalEntityRepository.save(Mockito.any(AnimalEntity.class))).thenThrow(InternalException.class);

        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> repositoryGateway.create(animal));
        assertEquals("Erro ao salvar animal", runtimeException.getMessage());

    }
}
