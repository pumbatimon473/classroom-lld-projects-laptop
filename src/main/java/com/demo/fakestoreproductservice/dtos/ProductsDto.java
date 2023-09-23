package com.demo.fakestoreproductservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductsDto {
    // Fields
    private Status status;
    private String message;
    private List<ProductResource> products;

    // CTOR
    public ProductsDto(Status status, List<ProductResource> products) {
        this.status = status;
        this.products = products;
    }
}
