package com.demo.fakestoreproductservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Category extends BaseModel {
    // Fields
    private String name;
    @OneToMany(mappedBy = "category")
    private List<Product> products;

    // CTOR
    public Category(String name) {
        this.name = name;
    }

    // Behavior
    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                '}';
    }
}
