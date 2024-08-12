package com.adotapet.adotaPet.integration;

import com.adotapet.adotaPet.config.database.repository.AnimalEntityRepository;
import com.adotapet.adotaPet.shared.enums.Sex;
import com.adotapet.adotaPet.shared.enums.Size;
import com.adotapet.adotaPet.shared.enums.Specie;
import com.adotapet.adotaPet.shared.enums.Status;
import com.adotapet.adotaPet.shared.json.AnimalInformationJson;
import com.adotapet.adotaPet.shared.json.AnimalJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

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
    private ObjectMapper objectMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;

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
    void shouldCreateSchemasWithFlyway() {
        String sql = "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'flyway_schema_history'";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        assertTrue(count != null && count > 0, "tabela 'flyway_schema_history' não existe.");
    }
}
