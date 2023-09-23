package com.demo.fakestoreproductservice.services;

import com.demo.fakestoreproductservice.exceptions.NoResponseException;
import com.demo.fakestoreproductservice.models.Category;
import com.demo.fakestoreproductservice.models.Product;

import java.util.List;

public interface CategoryService {

    List<Category> getProductCategories() throws NoResponseException;

    List<Product> getProductsByCategory(String type) throws NoResponseException;
}
