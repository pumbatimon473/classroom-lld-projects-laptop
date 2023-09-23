package com.demo.fakestoreproductservice.services;

import com.demo.fakestoreproductservice.exceptions.NoResponseException;
import com.demo.fakestoreproductservice.models.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getProducts() throws NoResponseException;

    Optional<Product> getProductById(long id) throws NoResponseException;

    Product addProduct(String title, Double price, String description, String category, String image) throws NoResponseException;

    Product replaceProduct(Long id, Product product) throws NoResponseException;

    /* DISABLED: NOT WORKING
    Optional<Product> updateProduct(Long id, String title, Double price, String description, String category, String image) throws NoResponseException;
    */

    Optional<Product> removeProduct(Long id);
}
