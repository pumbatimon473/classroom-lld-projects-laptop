package com.demo.fakestoreproductservice.configs;

public class Config {
    // Fake Store API Endpoints
    public static final String FAKESTORE_API_HOST = "https://fakestoreapi.com";
    public static final String FAKESTORE_PRODUCTS = FAKESTORE_API_HOST + "/products";
    public static final String FAKESTORE_CATEGORIES = FAKESTORE_API_HOST + "/products/categories";
    public static final String FAKESTORE_PRODUCTS_BY_CATEGORY = FAKESTORE_API_HOST + "/products/category";

    // Error Messages
    public static final String PRODUCT_RETRIEVAL_ERROR = "Oops! Something is not right. Please try again.";
    public static final String PRODUCT_ADD_ERROR = "Failed to create an entry for the product! Please check request format.";
    public static final String PRODUCT_REPLACE_ERROR = "Failed to replace the product with id %d. Please check request format.";
    public static final String PRODUCT_UPDATE_ERROR = "Failed to update the product with id %d.";
    public static final String PRODUCT_DELETE_ERROR = "Failed to delete the product with id %d.";
}
