package com.adotapet.adotaPet.core.domain;

import com.adotapet.adotaPet.shared.enums.Sex;
import com.adotapet.adotaPet.shared.enums.Size;
import com.adotapet.adotaPet.shared.enums.Specie;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FilterAnimal {

	private Size size;
	private Sex sex;
	private Specie specie;
}
