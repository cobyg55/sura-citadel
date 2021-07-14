package com.gruposura.citadels.mappers;

import com.gruposura.citadels.model.Product;
import com.gruposura.citadels.request.ProductRequest;
import com.gruposura.citadels.response.ProductResponse;

import java.util.Date;

public class ProductMapper {

    public static ProductResponse mapToProductResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setReference(product.getReference());
        productResponse.setCreatedAt(product.getCreatedAt());
        return productResponse;
    }

    public static Product mapToProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName().toUpperCase());
        product.setReference(productRequest.getReference().toUpperCase());
        product.setCreatedAt(new Date());
        return product;
    }

}
