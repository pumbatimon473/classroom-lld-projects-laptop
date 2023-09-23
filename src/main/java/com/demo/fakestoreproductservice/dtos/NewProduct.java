package com.demo.fakestoreproductservice.dtos;

// record - Preview Feature - Added from Java 14
// record - is an immutable class
public record NewProduct(String title, Double price, String description, String category, String image) {}
