package com.adotapet.adotaPet.core.gateway.database;

import com.adotapet.adotaPet.core.domain.Animal;

public interface AnimalRepositoryGateway {

    void create(Animal animal);
}