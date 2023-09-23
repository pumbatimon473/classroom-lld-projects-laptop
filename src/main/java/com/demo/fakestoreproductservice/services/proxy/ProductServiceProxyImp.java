package com.demo.fakestoreproductservice.services.proxy;

import com.demo.fakestoreproductservice.configs.Config;
import com.demo.fakestoreproductservice.domain.ProductTemplate;
import com.demo.fakestoreproductservice.dtos.NewProduct;
import com.demo.fakestoreproductservice.exceptions.NoResponseException;
import com.demo.fakestoreproductservice.models.Category;
import com.demo.fakestoreproductservice.models.Product;
import com.demo.fakestoreproductservice.services.ProductService;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceProxyImp implements ProductService {
    // Fields
    private static final RestTemplate restTemplate = new RestTemplateBuilder().build();

    // Behaviors
    @Override
    public List<Product> getProducts() throws NoResponseException {
        try {
            ResponseEntity<List<ProductTemplate>> responseEntity = restTemplate.exchange(Config.FAKESTORE_PRODUCTS, HttpMethod.GET, null, new ParameterizedTypeReference<List<ProductTemplate>>() {
            });
            if (responseEntity != null && responseEntity.hasBody()) {
                return responseEntity.getBody().stream()
                        .map(productTemplate -> this.mapToProduct(productTemplate))
                        .collect(Collectors.toList());
            }
            throw new NoResponseException("Looks like the servers are currently wonky! Please try after sometime.");
        } catch (RestClientException e) {
            throw e;
        }
    }

    @Override
    public Optional<Product> getProductById(long id) {
        ProductTemplate productTemplate = restTemplate.getForObject(Config.FAKESTORE_PRODUCTS + "/" + id, ProductTemplate.class);
        Product product = this.mapToProduct(productTemplate);
        return Optional.ofNullable(product);
    }

    @Override
    public Product addProduct(String title, Double price, String description, String category, String image) throws NoResponseException {
        ProductTemplate productTemplate = restTemplate.postForObject(Config.FAKESTORE_PRODUCTS, new NewProduct(title, price, description, category, image), ProductTemplate.class);
        Product savedProduct = this.mapToProduct(productTemplate);
        if (savedProduct == null)
            throw new NoResponseException("Looks like the servers are currently wonky! Please try after sometime.");
        return savedProduct;
    }

    @Override
    public Product replaceProduct(Long id, Product product) throws NoResponseException {
        HttpEntity<NewProduct> requestEntity = new HttpEntity<>(
                new NewProduct(product.getTitle(), product.getPrice(), product.getDescription(), product.getCategory().getName(), product.getImage())
        );
        ResponseEntity<ProductTemplate> responseEntity = restTemplate.exchange(Config.FAKESTORE_PRODUCTS + "/" + id, HttpMethod.PUT, requestEntity, new ParameterizedTypeReference<ProductTemplate>() {
        });
        if (responseEntity != null && responseEntity.hasBody()) {
            return this.mapToProduct(responseEntity.getBody());
        }
        throw new NoResponseException("Looks like the servers are currently wonky! Please try after sometime.");
    }

    /* DISABLED: NOT WORKING
    @Override
    public Optional<Product> updateProduct(Long id, String title, Double price, String description, String category, String image) throws NoResponseException {
        HttpEntity<NewProduct> requestEntity = new HttpEntity<>(new NewProduct(title, price, description, category, image));
        ResponseEntity<ProductTemplate> responseEntity = restTemplate.exchange(Config.FAKESTORE_PRODUCTS + "/" + id, HttpMethod.PATCH, requestEntity, new ParameterizedTypeReference<ProductTemplate>() {
        });
        if (responseEntity != null && responseEntity.hasBody())
            return Optional.ofNullable(this.mapToProduct(responseEntity.getBody()));
        throw new NoResponseException("Looks like the servers are currently wonky! Please try after sometime.");
    }
    */

    @Override
    public Optional<Product> removeProduct(Long id) {
        ResponseEntity<ProductTemplate> responseEntity = restTemplate.exchange(Config.FAKESTORE_PRODUCTS + "/" + id, HttpMethod.DELETE, null, new ParameterizedTypeReference<ProductTemplate>() {
        });
        if (responseEntity != null && responseEntity.hasBody())
            return Optional.of(this.mapToProduct(responseEntity.getBody()));
        return Optional.empty();
    }

    private Product mapToProduct(ProductTemplate productTemplate) {
        if (productTemplate == null) return null;
        Product product = new Product();
        product.setId(productTemplate.getId());
        product.setTitle(productTemplate.getTitle());
        product.setPrice(productTemplate.getPrice());
        product.setDescription(productTemplate.getDescription());
        product.setCategory(new Category(productTemplate.getCategory()));
        product.setImage(productTemplate.getImage());
        return product;
    }
}
