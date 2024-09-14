package com.adotapet.adotaPet.core.usecase.organization;

import com.adotapet.adotaPet.core.domain.Organization;
import com.adotapet.adotaPet.core.gateway.database.organization.OrganizationRepositoryGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateOrganizationUseCase {

    private final OrganizationRepositoryGateway organizationRepositoryGateway;

    public void create(Organization organization) {
        organizationRepositoryGateway.create(organization);
    }
}
