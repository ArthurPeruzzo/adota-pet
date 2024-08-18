package com.adotapet.adotaPet.shared.json;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrganizationInformationJson {
	private String name;
	private String phone;
	private String location;
}
