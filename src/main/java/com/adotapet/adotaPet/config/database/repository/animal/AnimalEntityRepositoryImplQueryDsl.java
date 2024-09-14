package com.adotapet.adotaPet.config.database.repository.animal;

import com.adotapet.adotaPet.config.database.entity.AnimalEntity;
import com.adotapet.adotaPet.config.database.entity.QAnimalEntity;
import com.adotapet.adotaPet.config.database.entity.QAnimalInformationEntity;
import com.adotapet.adotaPet.core.domain.FilterAnimal;
import com.adotapet.adotaPet.shared.enums.Status;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AnimalEntityRepositoryImplQueryDsl {

	private final EntityManager em;

	public Page<AnimalEntity> findByFilter(FilterAnimal filter) {
		Pageable pageable = filter.getPageable();
		QAnimalEntity animal = QAnimalEntity.animalEntity;
		QAnimalInformationEntity animalInformation = QAnimalInformationEntity.animalInformationEntity;
		JPAQuery<AnimalEntity> query = new JPAQuery<>(em);

		query.from(animal);
		query.join(animal.animalInformation, animalInformation);
		BooleanExpression predicate = buildPredicate(animal, animalInformation, filter);
		query.where(predicate);

		OrderSpecifier<?> orderBy = animal.name.asc();
		if (pageable.getSort().isSorted()) {
			orderBy = new OrderSpecifier<>(Order.ASC, animal.name);
		}

		long total = query
				.from(animal)
				.where(predicate)
				.fetch().size();
		query.offset(pageable.getOffset());
		query.limit(pageable.getPageSize());
		query.orderBy(orderBy);
		List<AnimalEntity> results = query.fetch();
		return new PageImpl<>(results, pageable, total);
	}

	private BooleanExpression buildPredicate(QAnimalEntity animal, QAnimalInformationEntity animalInformation, FilterAnimal filter) {
		BooleanExpression predicate = animalInformation.status.eq(Status.ACTIVE);

		if (filter.getSize() != null) {
			predicate = predicate.and(animal.size.eq(filter.getSize()));
		}
		if (filter.getSex() != null) {
			predicate = predicate.and(animal.sex.eq(filter.getSex()));
		}
		if (filter.getSpecie() != null) {
			predicate = predicate.and(animal.specie.eq(filter.getSpecie()));
		}

		return predicate;
	}
}
