package com.adotapet.adotaPet.shared.json;

import com.adotapet.adotaPet.core.domain.FilterAnimal;
import com.adotapet.adotaPet.shared.enums.Sex;
import com.adotapet.adotaPet.shared.enums.Size;
import com.adotapet.adotaPet.shared.enums.Specie;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class FilterAnimalJson {

	private Size size;
	private Sex sex;
	private Specie specie;

	public FilterAnimal toDomain(FilterAnimalJson filter) {
		return FilterAnimal.builder()
				.size(filter.getSize())
				.sex(filter.getSex())
				.specie(filter.getSpecie())
				.build();
	}
}
