package com.adotapet.adotaPet.core.domain;

import com.adotapet.adotaPet.shared.enums.Sex;
import com.adotapet.adotaPet.shared.enums.Size;
import com.adotapet.adotaPet.shared.enums.Specie;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
@Builder
public class FilterAnimal { //Isso não deveria ser dominio

	private Size size;
	private Sex sex;
	private Specie specie;
	private Pageable pageable;
}
