package com.adotapet.adotaPet.core.gateway.database;

import com.adotapet.adotaPet.core.domain.Animal;
import com.adotapet.adotaPet.core.domain.FilterAnimal;
import org.springframework.data.domain.Page;

public interface AnimalRepositoryGateway {

    void create(Animal animal);

    Page<Animal> findByFilter(FilterAnimal filter);
}