package com.gruposura.citadels.controller;

import com.gruposura.citadels.ex.SuraException;
import com.gruposura.citadels.request.ProductRequest;
import com.gruposura.citadels.response.ProductResponse;
import com.gruposura.citadels.routes.Router;
import com.gruposura.citadels.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Router.Product.PRODUCT_API)
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductResponse> findAll() {
        return productService.findAll();
    }

    @GetMapping(Router.Product.FIND_PRODUCT_BY_ID)
    public ProductResponse findById(@PathVariable("id") long id) throws SuraException {
        return productService.findById(id);
    }

    @PostMapping
    public ProductResponse create(@RequestBody ProductRequest productRequest) throws SuraException {
        return productService.create(productRequest);
    }

    @PutMapping(Router.Product.UPDATE)
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse update(@PathVariable("id") long id, @RequestBody ProductRequest request) {
        return productService.update(id, request);
    }
}
