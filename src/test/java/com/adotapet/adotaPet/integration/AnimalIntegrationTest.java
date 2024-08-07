package com.adotapet.adotaPet.integration;

import com.adotapet.adotaPet.config.database.repository.AnimalEntityRepository;
import com.adotapet.adotaPet.shared.enums.Sex;
import com.adotapet.adotaPet.shared.enums.Specie;
import com.adotapet.adotaPet.shared.json.AnimalJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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

    @Test
    void shouldCreateAnimal() throws Exception {

        AnimalJson animalJson = AnimalJson.builder()
                .name("doguinho")
                .year(0)
                .month(1)
                .weight(1.00)
                .size(30.00)
                .specie(Specie.DOG)
                .race("Guai")
                .sex(Sex.MALE)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/animal/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(animalJson)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
