package com.adotapet.adotaPet.config.database.repository;

import com.adotapet.adotaPet.config.database.entity.AnimalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalEntityRepository  extends JpaRepository<AnimalEntity, Long> {
}