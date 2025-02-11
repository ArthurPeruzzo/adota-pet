package com.adotapet.adotaPet.adapter.web.animal;

import com.adotapet.adotaPet.core.domain.Animal;
import com.adotapet.adotaPet.core.domain.FilterAnimal;
import com.adotapet.adotaPet.core.gateway.database.animal.AnimalRepositoryGateway;
import com.adotapet.adotaPet.core.usecase.animal.CreateAnimalUseCase;
import com.adotapet.adotaPet.core.usecase.animal.FindAnimalPageableByFilterUseCase;
import com.adotapet.adotaPet.shared.enums.Sex;
import com.adotapet.adotaPet.shared.enums.Size;
import com.adotapet.adotaPet.shared.enums.Specie;
import com.adotapet.adotaPet.shared.enums.Status;
import com.adotapet.adotaPet.shared.json.animal.AnimalInformationJson;
import com.adotapet.adotaPet.shared.json.animal.AnimalJson;
import com.adotapet.adotaPet.shared.json.animal.FilterAnimalJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = AnimalController.class)
public class AnimalControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private CreateAnimalUseCase createAnimalUseCase;
	@MockBean
	private AnimalRepositoryGateway animalRepositoryGateway;
	@MockBean
	private FindAnimalPageableByFilterUseCase findAnimalPageableByFilterUseCase;
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void shouldCreateAnimalWithSuccess() throws Exception {
		AnimalJson animalJson = AnimalJson.builder()
				.name("doguinho")
				.year(0)
				.month(1)
				.weight(1.00)
				.size(Size.MEDIUM)
				.specie(Specie.DOG)
				.race("Guai")
				.sex(Sex.MALE)
				.information(AnimalInformationJson.builder()
						.about("Doguinho top")
						.status(Status.ACTIVE)
						.photo("urlPhoto")
						.location("São domingos SC")
						.build())
				.organizationId(1L)
				.build();

		mockMvc.perform(MockMvcRequestBuilders.post("/animal/create")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(animalJson)))
				.andExpect(MockMvcResultMatchers.status().isOk());

		ArgumentCaptor<Animal> animalAC = ArgumentCaptor.forClass(Animal.class);
		verify(createAnimalUseCase).create(animalAC.capture());

		Animal value = animalAC.getValue();

		assertEquals(animalJson.getName(), value.getName());
		assertEquals(animalJson.getYear(), value.getAge().getYear());
		assertEquals(animalJson.getMonth(), value.getAge().getMonth());
		assertEquals(animalJson.getWeight(), value.getWeight());
		assertEquals(animalJson.getSize(), value.getSize());
		assertEquals(animalJson.getSpecie(), value.getSpecie());
		assertEquals(animalJson.getRace(), value.getRace());
		assertEquals(animalJson.getSex(), value.getSex());
		assertEquals(animalJson.getInformation().getAbout(), value.getInformation().getAbout());
		assertEquals(animalJson.getInformation().getStatus(), value.getInformation().getStatus());
		assertEquals(animalJson.getInformation().getPhoto(), value.getInformation().getPhoto());
		assertEquals(animalJson.getInformation().getLocation(), value.getInformation().getLocation());
		assertEquals(animalJson.getOrganizationId(), value.getOrganization().getId());
	}

	@Test
	void shouldThrowException400WhenCreateAnimal() throws Exception {
		AnimalJson animalJson = AnimalJson.builder()
				.year(-1)
				.month(-1)
				.weight(-1.00)
				.size(Size.MEDIUM)
				.race("Guai")
				.information(AnimalInformationJson.builder().build())
				.build();

		mockMvc.perform(MockMvcRequestBuilders.post("/animal/create")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(animalJson)))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());

	}

	@Test
	void shouldFindByFilterWithSuccess() throws Exception {
		FilterAnimalJson filterJson = FilterAnimalJson.builder()
				.size(Size.MEDIUM)
				.sex(Sex.FEMALE)
				.specie(Specie.CAT)
				.page(0)
				.pageSize(10)
				.build();

		when(findAnimalPageableByFilterUseCase.findByFilter(Mockito.any(FilterAnimal.class))).thenReturn(new PageImpl<>(List.of()));

		mockMvc.perform(MockMvcRequestBuilders.post("/animal/find-by-filter")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(filterJson)))
				.andExpect(MockMvcResultMatchers.status().isOk());

		ArgumentCaptor<FilterAnimal> filterAnimalAC = ArgumentCaptor.forClass(FilterAnimal.class);
		verify(findAnimalPageableByFilterUseCase).findByFilter(filterAnimalAC.capture());

		FilterAnimal value = filterAnimalAC.getValue();

		assertEquals(filterJson.getSize(), value.getSize());
		assertEquals(filterJson.getSex(), value.getSex());
		assertEquals(filterJson.getSpecie(), value.getSpecie());
	}
}
