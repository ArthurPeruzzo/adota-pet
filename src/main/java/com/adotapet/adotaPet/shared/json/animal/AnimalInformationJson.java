package com.adotapet.adotaPet.shared.json.animal;

import com.adotapet.adotaPet.shared.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class AnimalInformationJson {

	@NotNull(message = "Forneça informações sobre o animal")
	private String about;
	private Status status;
	private String photo;
	private String location;
}
