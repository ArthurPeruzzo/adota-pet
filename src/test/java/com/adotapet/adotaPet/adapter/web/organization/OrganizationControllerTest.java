package com.adotapet.adotaPet.adapter.web.organization;

import com.adotapet.adotaPet.core.domain.Organization;
import com.adotapet.adotaPet.core.usecase.organization.CreateOrganizationUseCase;
import com.adotapet.adotaPet.shared.json.organization.OrganizationJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@WebMvcTest(controllers = OrganizationController.class)
public class OrganizationControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private CreateOrganizationUseCase createOrganizationUseCase;
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void shouldCreateOrganizationWithSuccess() throws Exception {
		OrganizationJson organizationJson = OrganizationJson.builder()
				.name("Ong")
				.phone("333333333")
				.location("São Domingos SC")
				.build();

		mockMvc.perform(MockMvcRequestBuilders.post("/organization/create")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(organizationJson)))
				.andExpect(MockMvcResultMatchers.status().isOk());

		ArgumentCaptor<Organization> organizationAC = ArgumentCaptor.forClass(Organization.class);
		verify(createOrganizationUseCase).create(organizationAC.capture());

		Organization value = organizationAC.getValue();

		assertEquals(organizationJson.getName(), value.getName());
		assertEquals(organizationJson.getPhone(), value.getPhone());
		assertEquals(organizationJson.getLocation(), value.getLocation());
	}

	@Test
	void shouldThrowException400WhenCreateOrganization() throws Exception {
		OrganizationJson organizationJson = OrganizationJson.builder()
				.phone("333333333")
				.location("São Domingos SC")
				.build();

		mockMvc.perform(MockMvcRequestBuilders.post("/organization/create")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(organizationJson)))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());

	}
}
