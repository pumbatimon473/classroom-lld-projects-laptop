package com.demo.fakestoreproductservice.services.proxy;

import com.demo.fakestoreproductservice.configs.Config;
import com.demo.fakestoreproductservice.domain.ProductTemplate;
import com.demo.fakestoreproductservice.exceptions.NoResponseException;
import com.demo.fakestoreproductservice.models.Category;
import com.demo.fakestoreproductservice.models.Product;
import com.demo.fakestoreproductservice.services.CategoryService;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceProxyImp implements CategoryService {
    // Fields
    RestTemplate restTemplate = new RestTemplateBuilder().build();
    @Override
    public List<Category> getProductCategories() throws NoResponseException {
        ResponseEntity<List<String>> responseEntity = restTemplate.exchange(Config.FAKESTORE_CATEGORIES, HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>() {});
        if (responseEntity != null && responseEntity.hasBody())
            return responseEntity.getBody().stream()
                    .map(categoryName -> new Category(categoryName))
                    .collect(Collectors.toList());
        throw new NoResponseException("Looks like our servers are wonky right now. Please try after sometime.");
    }

    @Override
    public List<Product> getProductsByCategory(String type) throws NoResponseException {
        ResponseEntity<List<ProductTemplate>> responseEntity = restTemplate.exchange(Config.FAKESTORE_PRODUCTS_BY_CATEGORY + "/" + type, HttpMethod.GET, null, new ParameterizedTypeReference<List<ProductTemplate>>() {});
        if (responseEntity != null && responseEntity.hasBody())
            return responseEntity.getBody().stream()
                    .map(productTemplate -> this.mapToProduct(productTemplate))
                    .collect(Collectors.toList());
        throw new NoResponseException("Looks like our servers are wonky right now. Please try after sometime.");
    }

    private Product mapToProduct(ProductTemplate productTemplate) {
        Product product = Product.builder()
                .title(productTemplate.getTitle())
                .price(productTemplate.getPrice())
                .description(productTemplate.getDescription())
                .category(new Category(productTemplate.getCategory()))
                .image(productTemplate.getImage())
                .build();
        product.setId(productTemplate.getId());
        return product;
    }
}
