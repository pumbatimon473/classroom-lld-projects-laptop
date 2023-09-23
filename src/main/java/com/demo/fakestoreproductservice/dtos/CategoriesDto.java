package com.demo.fakestoreproductservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoriesDto {
    // Fields
    private Status status;
    private String message;
    private List<String> types;

    // CTOR
    public CategoriesDto(Status status, List<String> types) {
        this.status = status;
        this.types = types;
    }
}
