package com.adotapet.adotaPet.shared.json.organization;

import com.adotapet.adotaPet.core.domain.Organization;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrganizationJson {
	@NotNull
	private String name;
	@NotNull
	private String phone;
	@NotNull
	private String location;

	public Organization toDomain(OrganizationJson organizationJson) {
		return Organization.builder()
				.name(organizationJson.getName())
				.phone(organizationJson.getPhone())
				.location(organizationJson.getLocation())
				.build();
	}
}
