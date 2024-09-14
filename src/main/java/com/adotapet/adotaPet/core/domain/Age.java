package com.adotapet.adotaPet.core.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Age {

    private Integer year;
    private Integer month;

    public static Age toDomain(Integer year, Integer month) {
        return Age.builder()
                .year(year)
                .month(month).build();
    }
}
