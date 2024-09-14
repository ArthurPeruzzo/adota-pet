package com.adotapet.adotaPet.core.usecase;

import com.adotapet.adotaPet.core.domain.Animal;
import com.adotapet.adotaPet.core.domain.FilterAnimal;
import com.adotapet.adotaPet.core.gateway.database.animal.AnimalRepositoryGateway;
import com.adotapet.adotaPet.core.usecase.animal.FindAnimalPageableByFilterUseCase;
import com.adotapet.adotaPet.shared.enums.Sex;
import com.adotapet.adotaPet.shared.enums.Size;
import com.adotapet.adotaPet.shared.enums.Specie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindAnimalPageableByFilterUseCaseTest {

	@InjectMocks
	private FindAnimalPageableByFilterUseCase findAnimalPageableByFilterUseCase;
	@Mock
	private AnimalRepositoryGateway animalRepositoryGateway;

	@Test
	void shouldFindAnimalsByFilterWithSuccess() {
		FilterAnimal filter = FilterAnimal.builder()
				.size(Size.MEDIUM)
				.sex(Sex.FEMALE)
				.specie(Specie.CAT)
				.build();

		Animal animal = Animal.builder().build();

		when(animalRepositoryGateway.findByFilter(filter)).thenReturn(new PageImpl<>(List.of(animal)));

		Page<Animal> animals = findAnimalPageableByFilterUseCase.findByFilter(filter);

		ArgumentCaptor<FilterAnimal> filterAnimalAC = ArgumentCaptor.forClass(FilterAnimal.class);
		verify(animalRepositoryGateway).findByFilter(filterAnimalAC.capture());

		FilterAnimal filterAnimalValue = filterAnimalAC.getValue();

		assertEquals(filter.getSize(), filterAnimalValue.getSize());
		assertEquals(filter.getSex(), filterAnimalValue.getSex());
		assertEquals(filter.getSpecie(), filterAnimalValue.getSpecie());
		assertInstanceOf(Page.class, animals);
		assertInstanceOf(Animal.class, animals.getContent().getFirst());
	}
}