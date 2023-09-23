package com.demo.fakestoreproductservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductDto {
    // Fields
    private Status status;
    private String message;
    private ProductResource product;

    // CTOR
    public ProductDto(Status status, ProductResource product) {
        this.status = status;
        this.product = product;
    }
}
