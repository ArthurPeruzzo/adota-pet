package com.adotapet.adotaPet.core.gateway.database.organization;

import com.adotapet.adotaPet.config.database.entity.OrganizationEntity;
import com.adotapet.adotaPet.config.database.repository.organization.OrganizationEntityRepository;
import com.adotapet.adotaPet.core.domain.Organization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrganizationSpringDataRepositoryGateway implements OrganizationRepositoryGateway {

	private final OrganizationEntityRepository organizationEntityRepository;

	@Override
	public void create(Organization organization) {
		try {
			OrganizationEntity entity = domainToEntity(organization);
			organizationEntityRepository.save(entity);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException("Erro ao salvar organização");
		}
	}

	private OrganizationEntity domainToEntity(Organization organization) {
		return OrganizationEntity.builder()
				.name(organization.getName())
				.phone(organization.getPhone())
				.location(organization.getLocation())
				.build();
	}
}
