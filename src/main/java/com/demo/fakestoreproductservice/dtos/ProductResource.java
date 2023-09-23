package com.demo.fakestoreproductservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ProductResource {
    private Long id;
    private String title;
    private Double price;
    private String description;
    private String category;
    private String image;
}
