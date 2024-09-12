package com.adotapet.adotaPet.integration;

import com.adotapet.adotaPet.config.database.entity.AnimalEntity;
import com.adotapet.adotaPet.config.database.entity.AnimalInformationEntity;
import com.adotapet.adotaPet.config.database.repository.AnimalEntityRepository;
import com.adotapet.adotaPet.config.database.repository.AnimalEntityRepositoryImplQueryDsl;
import com.adotapet.adotaPet.core.domain.FilterAnimal;
import com.adotapet.adotaPet.shared.enums.Sex;
import com.adotapet.adotaPet.shared.enums.Size;
import com.adotapet.adotaPet.shared.enums.Specie;
import com.adotapet.adotaPet.shared.enums.Status;
import com.adotapet.adotaPet.shared.json.AnimalInformationJson;
import com.adotapet.adotaPet.shared.json.AnimalJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AnimalIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AnimalEntityRepository animalEntityRepository;
    @Autowired
    private AnimalEntityRepositoryImplQueryDsl animalEntityRepositoryImplQueryDsl;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    @Rollback
    void shouldCreateAnimal() throws Exception {

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
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/animal/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(animalJson)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Transactional
    @Rollback
    void shouldFindAnimalsByFilter() {
        var animalEntity = AnimalEntity.builder()
                .name("Teste")
                .birthYear(1)
                .birthMonth(1)
                .weight(10.00)
                .size(Size.MEDIUM)
                .specie(Specie.DOG)
                .race("race test")
                .sex(Sex.MALE)
                .build();

        AnimalInformationEntity animalInformation = AnimalInformationEntity.builder()
                .about("Doguinho top")
                .animal(animalEntity)
                .status(Status.ACTIVE)
                .photo("urlPhoto")
                .location("São domingos SC")
                .build();

        animalEntity.setAnimalInformation(animalInformation);

        var animalEntity1 = AnimalEntity.builder()
                .name("Teste1")
                .birthYear(1)
                .birthMonth(1)
                .weight(10.00)
                .size(Size.MEDIUM)
                .specie(Specie.DOG)
                .race("race test")
                .sex(Sex.MALE)
                .build();

        AnimalInformationEntity animalInformation1 = AnimalInformationEntity.builder()
                .about("Doguinho top")
                .animal(animalEntity1)
                .status(Status.INACTIVE)
                .photo("urlPhoto")
                .location("São domingos SC")
                .build();

        animalEntity1.setAnimalInformation(animalInformation1);

        entityManager.persist(animalEntity);
        entityManager.persist(animalEntity1);

        FilterAnimal filter = FilterAnimal.builder()
                .size(Size.MEDIUM)
                .sex(Sex.MALE)
                .specie(Specie.DOG)
                .pageable(PageRequest.of(0, 10))
                .build();
        Page<AnimalEntity> animalsPage = animalEntityRepositoryImplQueryDsl.findByFilter(filter);
        List<AnimalEntity> animals = animalsPage.getContent();
        Assertions.assertEquals(1, animals.size());
    }

    @Test
    @Transactional
    @Rollback
    void shouldCreateSchemasWithFlyway() {
        String sql = "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'flyway_schema_history'";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        assertTrue(count != null && count > 0, "tabela 'flyway_schema_history' não existe.");
    }
}
