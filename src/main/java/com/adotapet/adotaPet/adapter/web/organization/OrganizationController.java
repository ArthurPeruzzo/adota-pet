package com.adotapet.adotaPet.adapter.web.organization;

import com.adotapet.adotaPet.core.domain.Organization;
import com.adotapet.adotaPet.core.usecase.organization.CreateOrganizationUseCase;
import com.adotapet.adotaPet.shared.json.organization.OrganizationJson;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/organization")
public class OrganizationController {

	private final CreateOrganizationUseCase createOrganizationUseCase;

	@PostMapping("/create")
	public void create(@RequestBody @Valid OrganizationJson organizationJson) {
		log.info("create organization, OrganizationJson={}", organizationJson);
		Organization organization = organizationJson.toDomain(organizationJson);
		createOrganizationUseCase.create(organization);
		log.info("created organization");
	}
}
