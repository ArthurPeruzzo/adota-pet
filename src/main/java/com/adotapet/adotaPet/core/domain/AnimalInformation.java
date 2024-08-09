package com.adotapet.adotaPet.core.domain;

import com.adotapet.adotaPet.shared.enums.Status;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AnimalInformation {

    private String about;
    private Status status;
    private String photo;
    private String location;

}
