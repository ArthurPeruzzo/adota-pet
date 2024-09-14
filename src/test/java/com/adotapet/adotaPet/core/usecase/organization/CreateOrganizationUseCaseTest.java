package com.adotapet.adotaPet.core.usecase.organization;

import com.adotapet.adotaPet.core.domain.Organization;
import com.adotapet.adotaPet.core.gateway.database.organization.OrganizationRepositoryGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CreateOrganizationUseCaseTest {

    @InjectMocks
    private CreateOrganizationUseCase createOrganizationUseCase;
    @Mock
    private OrganizationRepositoryGateway organizationRepositoryGateway;

    @Test
    void shouldCreateWithSuccessOrganization() {
        Organization organization = Organization.builder()
                .name("Ong")
                .phone("333333333")
                .location("São Domingos SC")
                .build();

        createOrganizationUseCase.create(organization);

        ArgumentCaptor<Organization> organizationAC = ArgumentCaptor.forClass(Organization.class);

        verify(organizationRepositoryGateway).create(organizationAC.capture());

        Organization organizationValue = organizationAC.getValue();

        assertEquals(organization.getName(), organizationValue.getName());
        assertEquals(organization.getPhone(), organizationValue.getPhone());
        assertEquals(organization.getLocation(), organizationValue.getLocation());
    }
}