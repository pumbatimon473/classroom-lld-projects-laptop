package com.demo.fakestoreproductservice.controllers.proxy;

import com.demo.fakestoreproductservice.dtos.CategoriesDto;
import com.demo.fakestoreproductservice.dtos.ProductResource;
import com.demo.fakestoreproductservice.dtos.ProductsDto;
import com.demo.fakestoreproductservice.dtos.Status;
import com.demo.fakestoreproductservice.models.Product;
import com.demo.fakestoreproductservice.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
public class CategoryControllerProxy {
    CategoryService categoryService;

    // Behaviors
    @GetMapping("/products/categories")
    public CategoriesDto getProductCategories() {
        CategoriesDto response = null;
        try {
            List<String> categories = categoryService.getProductCategories().stream()
                    .map(category -> category.getName())
                    .collect(Collectors.toList());
            response = new CategoriesDto(Status.SUCCESS, categories);
            response.setMessage("Total Categories: " + categories.size());
        } catch (Exception e) {
            response = new CategoriesDto(Status.FAILED, null);
            response.setMessage("Oops! Something is not right. Please try again.");
        }
        return response;
    }

    @GetMapping("/products/category/{type}")
    public ProductsDto getProductsByCategory(@PathVariable(name = "type") String type) {
        ProductsDto response = null;
        try {
            List<ProductResource> products = categoryService.getProductsByCategory(type).stream()
                    .map(product -> this.mapToProductResource(product))
                    .collect(Collectors.toList());
            response = new ProductsDto(Status.SUCCESS, products);
            response.setMessage("Total products available for the category '" + type + "': " + products.size());
        } catch (Exception e) {
            response = new ProductsDto(Status.FAILED, null);
            response.setMessage("Oops! Something is not right. Please try again.");
        }
        return response;
    }

    private ProductResource mapToProductResource(Product product) {
        return ProductResource.builder()
                .id(product.getId())
                .title(product.getTitle())
                .price(product.getPrice())
                .description(product.getDescription())
                .category(product.getCategory().getName())
                .image(product.getImage())
                .build();
    }
}
