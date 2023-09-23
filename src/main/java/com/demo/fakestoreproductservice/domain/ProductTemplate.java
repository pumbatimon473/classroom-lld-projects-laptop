package com.demo.fakestoreproductservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor  // com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Cannot construct instance of `com.demo.fakestoreproductservice.domain.ProductTemplate` (no Creators, like default constructor, exist): cannot deserialize from Object value (no delegate- or property-based Creator)
@AllArgsConstructor // Req for auto-binding of keys in Json to the fields with the same name
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductTemplate {
    // Fields
    private Long id;
    private String title;
    private Double price;
    private String description;
    private String category;
    private String image;
}
