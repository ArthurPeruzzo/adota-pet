package com.adotapet.adotaPet.core.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Organization {

    private String name;
    private String phone;
    private String location;
}
