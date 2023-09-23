package com.demo.fakestoreproductservice.controllers.proxy;

import com.demo.fakestoreproductservice.configs.Config;
import com.demo.fakestoreproductservice.dtos.*;
import com.demo.fakestoreproductservice.models.Product;
import com.demo.fakestoreproductservice.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class ProductControllerProxy {
    // Fields
    private ProductService productServiceProxy;

    // Behaviors
    @GetMapping("/products")
    public ProductsDto getAllProducts() {
        ProductsDto response = null;
        try {
            List<ProductResource> products = productServiceProxy.getProducts().stream()
                    .map(product -> this.mapToProductResource(product))
                    .collect(Collectors.toList());
            response = new ProductsDto(Status.SUCCESS, products);
            response.setMessage("Total Products: " + products.size());
        } catch (Exception e) {
            response = new ProductsDto(Status.FAILED, null);
            response.setMessage(Config.PRODUCT_RETRIEVAL_ERROR);
        }
        return response;
    }

    @GetMapping("/products/{id}")
    public ProductDto getSingleProduct(@PathVariable(name = "id") Long id) {
        ProductDto response = null;
        try {
            ProductResource productInfo = productServiceProxy.getProductById(id)
                    .map(product -> this.mapToProductResource(product))
                    .orElse(new ProductResource());
            response = new ProductDto(Status.SUCCESS, productInfo);
            response.setMessage("Retrieved product with id: " + id);
        } catch (Exception e) {
            response = new ProductDto(Status.FAILED, null);
            response.setMessage(Config.PRODUCT_RETRIEVAL_ERROR);
        }
        return response;
    }

    @PostMapping("/products")
    public ProductDto addNewProduct(@RequestBody NewProduct newProduct) {
        ProductDto response = null;
        try {
            Product productWithId = productServiceProxy.addProduct(newProduct.title(), newProduct.price(), newProduct.description(), newProduct.category(), newProduct.image());
            response = new ProductDto(Status.CREATED, this.mapToProductResource(productWithId));
            response.setMessage("Added new product");
        } catch (Exception e) {
            response = new ProductDto(Status.FAILED, null);
            response.setMessage(Config.PRODUCT_ADD_ERROR);
        }
        return response;
    }

    @PutMapping("/products/{id}")
    public ProductDto replaceProduct(@PathVariable(name = "id") Long id, @RequestBody Product product) {
        ProductDto response = null;
        try {
            Product savedProduct = productServiceProxy.replaceProduct(id, product);
            response = new ProductDto(Status.REPLACED, this.mapToProductResource(savedProduct));
            response.setMessage(String.format("Product with id %d has been replaced.", id));
        } catch (Exception e) {
            response = new ProductDto(Status.FAILED, null);
            response.setMessage(String.format(Config.PRODUCT_REPLACE_ERROR, id));
        }
        return response;
    }

    /* DISABLED: NOT WORKING
    @PatchMapping("/products/{id}")
    public ProductDto updateProduct(@PathVariable(name = "id") Long id, @RequestBody NewProduct newProduct) {
        ProductDto response = null;
        try {
            response = productServiceProxy.updateProduct(id, newProduct.title(), newProduct.price(), newProduct.description(), newProduct.category(), newProduct.image())
                    .map(updatedProduct -> new ProductDto(Status.UPDATED, this.mapToProductResource(updatedProduct)))
                    .orElse(new ProductDto(Status.FAILED, null));
            if (response.getStatus() == Status.UPDATED)
                response.setMessage("Updated product with id: " + id);
            else
                response.setMessage("Product with id " + id + " does not exist! Cannot update.");
        } catch (Exception e) {
            response = new ProductDto(Status.FAILED, null);
            response.setMessage(String.format(Config.PRODUCT_UPDATE_ERROR + " " + e.getMessage(), id));
        }
        return response;
    }
    */

    @DeleteMapping("/products/{id}")
    public ProductDto removeProduct(@PathVariable(name = "id") Long id) {
        ProductDto response = null;
        try {
            response = productServiceProxy.removeProduct(id)
                    .map(product -> new ProductDto(Status.REMOVED, this.mapToProductResource(product)))
                    .orElse(new ProductDto(Status.FAILED, null));
            if (response.getStatus() == Status.REMOVED)
                response.setMessage("Removed product with id: " + id);
            else
                response.setMessage("Product with id " + id + " does not exist! Cannot remove.");
        } catch (Exception e) {
            response = new ProductDto(Status.FAILED, null);
            response.setMessage(String.format(Config.PRODUCT_DELETE_ERROR, id));
        }
        return response;
    }


    private ProductResource mapToProductResource(Product product) {
        ProductResource productResource = new ProductResource(
                product.getId(),
                product.getTitle(),
                product.getPrice(),
                product.getDescription(),
                product.getCategory().getName(),
                product.getImage()
        );
        return productResource;
    }
}
