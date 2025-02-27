package com.qikserve.checkout.service;

import com.qikserve.checkout.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

    private static final String PRODUCT_URL = "http://localhost:8081/products";
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final RestTemplate restTemplate;

    public ProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Product> getAllProducts() {
        logger.info("Fetching all products from WireMock...");
        Product[] products = restTemplate.getForObject(PRODUCT_URL, Product[].class);
        return Arrays.asList(products != null ? products : new Product[0]);
    }

    public Product getProductById(String productId) {
        logger.info("Fetching product with ID: {}", productId);
        return restTemplate.getForObject(PRODUCT_URL + "/" + productId, Product.class);
    }
}
