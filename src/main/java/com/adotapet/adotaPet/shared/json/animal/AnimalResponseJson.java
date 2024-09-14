
package com.adotapet.adotaPet.shared.json.animal;

import com.adotapet.adotaPet.core.domain.Age;
import com.adotapet.adotaPet.core.domain.Animal;
import com.adotapet.adotaPet.core.domain.AnimalInformation;
import com.adotapet.adotaPet.core.domain.Organization;
import com.adotapet.adotaPet.shared.enums.Sex;
import com.adotapet.adotaPet.shared.enums.Size;
import com.adotapet.adotaPet.shared.enums.Specie;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AnimalResponseJson {

	private Long id;
	private String name;
	private Age age;
	private Double weight;
	private Size size;
	private Specie specie;
	private String race;
	private Sex sex;
	private AnimalInformation information;
	private Organization organization;

	public AnimalResponseJson(Animal animal) {
		this.name = animal.getName();
		this.age = animal.getAge();
		this.weight = animal.getWeight();
		this.size = animal.getSize();
		this.specie = animal.getSpecie();
		this.race = animal.getRace();
		this.sex = animal.getSex();
		this.information = animal.getInformation();
		this.organization = animal.getOrganization();
	}
}
