package com.library.api.entities.enums;

import lombok.Getter;

@Getter
public enum RentalStatusEnum {
    PENDING(1L),
    ON_TIME(2L),
    LATE_TIME(3L);

    private final Long id;

    RentalStatusEnum(Long id) {
        this.id = id;
    }
}
