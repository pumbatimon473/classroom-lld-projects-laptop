package com.demo.fakestoreproductservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Product extends BaseModel {
    // Fields
    private String title;
    private Double price;
    @ManyToOne
    private Category category;
    private String description;
    private String image;

    // Behaviors
    @Override
    public String toString() {
        return "Product{" +
                "title='" + title + '\'' +
                ", price=" + price +
                ", category=" + category +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
