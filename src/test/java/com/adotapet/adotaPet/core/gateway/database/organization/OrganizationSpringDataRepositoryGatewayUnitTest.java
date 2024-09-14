package com.adotapet.adotaPet.core.gateway.database.organization;

import com.adotapet.adotaPet.config.database.entity.OrganizationEntity;
import com.adotapet.adotaPet.config.database.repository.organization.OrganizationEntityRepository;
import com.adotapet.adotaPet.core.domain.Organization;
import org.apache.logging.log4j.util.InternalException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OrganizationSpringDataRepositoryGatewayUnitTest {

    @InjectMocks
    private OrganizationSpringDataRepositoryGateway repositoryGateway;

    @Mock
    private OrganizationEntityRepository organizationEntityRepository;

    @Test
    void shouldCreateWithSuccessOrganization() {
        Organization organization = Organization.builder()
                .name("Ong")
                .phone("333333333")
                .location("São Domingos SC")
                .build();

        repositoryGateway.create(organization);

        ArgumentCaptor<OrganizationEntity> organizationEntityAC = ArgumentCaptor.forClass(OrganizationEntity.class);

        verify(organizationEntityRepository).save(organizationEntityAC.capture());

        OrganizationEntity organizationValue = organizationEntityAC.getValue();

        assertEquals(organization.getName(), organizationValue.getName());
        assertEquals(organization.getPhone(), organizationValue.getPhone());
        assertEquals(organization.getLocation(), organizationValue.getLocation());
    }

    @Test
    void shouldThrowExceptionWhenCreateOrganization() {
        Organization organization = Organization.builder()
                .name("Ong")
                .phone("333333333")
                .location("São Domingos SC")
                .build();

        Mockito.when(organizationEntityRepository.save(Mockito.any(OrganizationEntity.class))).thenThrow(InternalException.class);

        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> repositoryGateway.create(organization));
        assertEquals("Erro ao salvar organização", runtimeException.getMessage());

    }
}
