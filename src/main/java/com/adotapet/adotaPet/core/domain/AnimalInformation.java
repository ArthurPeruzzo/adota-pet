package com.adotapet.adotaPet.core.domain;

import com.adotapet.adotaPet.shared.enums.Status;
import com.adotapet.adotaPet.shared.json.animal.AnimalInformationJson;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AnimalInformation {

    private String about;
    private Status status;
    private String photo;
    private String location;

    public static AnimalInformation toDomain(AnimalInformationJson information) {
        return AnimalInformation.builder()
                .about(information.getAbout())
                .status(information.getStatus())
                .photo(information.getPhoto())
                .location(information.getLocation())
                .build();
    }

}
