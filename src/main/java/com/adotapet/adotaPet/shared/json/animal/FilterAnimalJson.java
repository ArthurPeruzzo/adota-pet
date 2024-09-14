package com.adotapet.adotaPet.shared.json.animal;

import com.adotapet.adotaPet.core.domain.FilterAnimal;
import com.adotapet.adotaPet.shared.enums.Sex;
import com.adotapet.adotaPet.shared.enums.Size;
import com.adotapet.adotaPet.shared.enums.Specie;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.PageRequest;

@Getter
@Builder
@ToString
public class FilterAnimalJson {

	private Size size;
	private Sex sex;
	private Specie specie;
	@NotNull
	private Integer page;
	@NotNull
	private Integer pageSize;

	public FilterAnimal toDomain(FilterAnimalJson filter) {
		return FilterAnimal.builder()
				.size(filter.getSize())
				.sex(filter.getSex())
				.specie(filter.getSpecie())
				.pageable(PageRequest.of(filter.getPage(), filter.getPageSize()))
				.build();
	}
}
