package com.adotapet.adotaPet.integration.animal;

import com.adotapet.adotaPet.config.database.entity.AnimalEntity;
import com.adotapet.adotaPet.config.database.entity.AnimalInformationEntity;
import com.adotapet.adotaPet.config.database.entity.OrganizationEntity;
import com.adotapet.adotaPet.config.database.repository.animal.AnimalEntityRepository;
import com.adotapet.adotaPet.config.database.repository.animal.AnimalEntityRepositoryImplQueryDsl;
import com.adotapet.adotaPet.config.database.repository.organization.OrganizationEntityRepository;
import com.adotapet.adotaPet.core.domain.FilterAnimal;
import com.adotapet.adotaPet.shared.enums.Sex;
import com.adotapet.adotaPet.shared.enums.Size;
import com.adotapet.adotaPet.shared.enums.Specie;
import com.adotapet.adotaPet.shared.enums.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    private OrganizationEntityRepository organizationEntityRepository;
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
    void shouldCreateAnimal() {

        OrganizationEntity organizationEntity = OrganizationEntity.builder()
                .name("Organization ong")
                .phone("333333333")
                .location("São Domingos sc")
                .build();

        organizationEntityRepository.save(organizationEntity);

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
        animalEntity.setOrganization(organizationEntity);
        AnimalEntity animalSaved = animalEntityRepository.save(animalEntity);

        assertEquals(animalEntity.getName(), animalSaved.getName());
        assertEquals(animalEntity.getBirthYear(), animalSaved.getBirthYear());
        assertEquals(animalEntity.getBirthMonth(), animalSaved.getBirthMonth());
        assertEquals(animalEntity.getWeight(), animalSaved.getWeight());
        assertEquals(animalEntity.getSize(), animalSaved.getSize());
        assertEquals(animalEntity.getSpecie(), animalSaved.getSpecie());
        assertEquals(animalEntity.getRace(), animalSaved.getRace());
        assertEquals(animalEntity.getSex(), animalSaved.getSex());
        assertEquals(animalEntity.getAnimalInformation().getAbout(), animalSaved.getAnimalInformation().getAbout());
        assertEquals(animalEntity.getAnimalInformation().getStatus(), animalSaved.getAnimalInformation().getStatus());
        assertEquals(animalEntity.getAnimalInformation().getPhoto(), animalSaved.getAnimalInformation().getPhoto());
        assertEquals(animalEntity.getAnimalInformation().getLocation(), animalSaved.getAnimalInformation().getLocation());
        assertEquals(animalEntity.getOrganization().getId(), animalSaved.getOrganization().getId());
        assertEquals(animalEntity.getOrganization().getName(), animalSaved.getOrganization().getName());
        assertEquals(animalEntity.getOrganization().getLocation(), animalSaved.getOrganization().getLocation());
        assertEquals(animalEntity.getOrganization().getPhone(), animalSaved.getOrganization().getPhone());
    }

    @Test
    @Transactional
    @Rollback
    void shouldFindAnimalsByFilter() {

        OrganizationEntity organizationEntity = OrganizationEntity.builder()
                .name("Organization ong")
                .phone("333333333")
                .location("São Domingos sc")
                .build();

        organizationEntityRepository.save(organizationEntity);

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
        animalEntity.setOrganization(organizationEntity);

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
        animalEntity1.setOrganization(organizationEntity);

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
