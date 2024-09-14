package com.adotapet.adotaPet.config.database.repository.organization;

import com.adotapet.adotaPet.config.database.entity.OrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationEntityRepository extends JpaRepository<OrganizationEntity, Long> {
}