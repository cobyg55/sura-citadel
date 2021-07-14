package com.gruposura.citadels.service;

import com.gruposura.citadels.ex.ErrorCodes;
import com.gruposura.citadels.ex.ErrorMessages;
import com.gruposura.citadels.ex.SuraException;
import com.gruposura.citadels.mappers.ProductMapper;
import com.gruposura.citadels.model.Product;
import com.gruposura.citadels.repository.ProductRepository;
import com.gruposura.citadels.request.ProductRequest;
import com.gruposura.citadels.response.ProductResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final MessageService messageService;

    public ProductService(ProductRepository productRepository, MessageService messageService) {
        this.productRepository = productRepository;
        this.messageService = messageService;
    }

    public List<ProductResponse> findAll() {
        try {
            return productRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt")).stream().map(ProductMapper::mapToProductResponse).collect(Collectors.toList());
        } catch (SuraException ex) {
            log.error("ProductService:findAll() --Error:[{}]", ex.getMessage());
            throw new SuraException(ErrorCodes.BAD_REQUEST, ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            log.error("ProductService:findAll() --Error:[{}]", ex.getMessage());
            throw ex;
        }
    }

    public ProductResponse findById(long id) throws SuraException {
        try {
            Product product = getById(id);
            return ProductMapper.mapToProductResponse(product);
        } catch (SuraException ex) {
            log.error("ProductService:findById() --Error:[{}]", ex.getMessage());
            throw new SuraException(ErrorCodes.BAD_REQUEST, ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            log.error("ProductService:findById() --Error:[{}]", ex.getMessage());
            throw ex;
        }
    }

    public ProductResponse create(ProductRequest productRequest) throws SuraException {
        try {
            Optional<Product> productByName = productRepository.findByName(productRequest.getName().toUpperCase());
            if (productByName.isPresent()) {
                throw new SuraException(ErrorCodes.VALIDATION, messageService.getMessage(ErrorMessages.PRODUCT_NAME_DUPLICATED, new Object[]{productRequest.getName()}), HttpStatus.BAD_REQUEST);
            }
            Optional<Product> productByReference = productRepository.findByReference(productRequest.getReference());
            if (productByReference.isPresent()) {
                throw new SuraException(ErrorCodes.VALIDATION, messageService.getMessage(ErrorMessages.PRODUCT_REFERENCE_DUPLICATED, new Object[]{productRequest.getReference()}), HttpStatus.BAD_REQUEST);
            }
            return ProductMapper.mapToProductResponse(productRepository.save(ProductMapper.mapToProduct(productRequest)));
        } catch (SuraException ex) {
            log.error("ProductService:create() --Error:[{}]", ex.getMessage());
            throw new SuraException(ErrorCodes.BAD_REQUEST, ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            log.error("ProductService:create() --Error:[{}]", ex.getMessage());
            throw ex;
        }
    }

    public ProductResponse update(long id, ProductRequest request) {
        try {
            Product current = getById(id);
            return ProductMapper.mapToProductResponse(productRepository.save(current));
        } catch (SuraException ex) {
            log.error("ProductService:update() --Error:[{}]", ex.getMessage());
            throw new SuraException(ErrorCodes.BAD_REQUEST, ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            log.error("ProductService:update() --Error:[{}]", ex.getMessage());
            throw ex;
        }
    }

    public Product getById(long id) throws SuraException {
        return productRepository.findById(id).orElseThrow(() -> new SuraException(ErrorCodes.VALIDATION, messageService.getMessage(ErrorMessages.PRODUCT_NOT_FOUND, new Object[]{id}), HttpStatus.BAD_REQUEST));
    }

}
