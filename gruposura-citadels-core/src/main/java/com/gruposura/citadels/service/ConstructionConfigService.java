package com.gruposura.citadels.service;

import com.gruposura.citadels.ex.ErrorCodes;
import com.gruposura.citadels.ex.ErrorMessages;
import com.gruposura.citadels.ex.SuraException;
import com.gruposura.citadels.mappers.ConstructionConfigMapper;
import com.gruposura.citadels.mappers.ConstructionDetailMapper;
import com.gruposura.citadels.model.ConstructionConfig;
import com.gruposura.citadels.model.ConstructionConfigDetail;
import com.gruposura.citadels.model.Product;
import com.gruposura.citadels.repository.ConstructionConfigDetailRepository;
import com.gruposura.citadels.repository.ConstructionConfigRepository;
import com.gruposura.citadels.request.ConstructionConfigDetailRequest;
import com.gruposura.citadels.request.ConstructionConfigRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ConstructionConfigService {

    private final ConstructionConfigRepository constructionConfigRepository;
    private final ConstructionConfigDetailRepository constructionConfigDetailRepository;
    private final ProductService productService;
    private final MessageService messageService;

    public ConstructionConfigService(ConstructionConfigRepository constructionConfigRepository, ConstructionConfigDetailRepository constructionConfigDetailRepository, ProductService productService, MessageService messageService) {
        this.constructionConfigRepository = constructionConfigRepository;
        this.constructionConfigDetailRepository = constructionConfigDetailRepository;
        this.productService = productService;
        this.messageService = messageService;
    }

    public List<ConstructionConfig> findAll() throws SuraException {
        return constructionConfigRepository.findAll();
    }

    public ConstructionConfig findById(long id) throws SuraException {
        try {
            return getById(id);
        } catch (SuraException ex) {
            log.error("ConstructionConfigService:findById() --Error[{}]", ex.getMessage());
            throw new SuraException(ErrorCodes.BAD_REQUEST, ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            log.error("ConstructionConfigService:findById() --Error[{}]", ex.getMessage());
            throw ex;
        }
    }

    public ConstructionConfig create(ConstructionConfigRequest constructionConfigRequest) {
        List<ConstructionConfigDetail> details = new ArrayList<>();
        ConstructionConfig config = constructionConfigRepository.save(ConstructionConfigMapper.mapToConstructionConfig(constructionConfigRequest));

        for (ConstructionConfigDetailRequest detail : constructionConfigRequest.getDetails()) {
            Product product = productService.getById(detail.getProductId());
            details.add(ConstructionDetailMapper.mapToConstructionDetail(product.getName(), detail.getProductId(), detail.getQuantity(), config));
        }
        List<ConstructionConfigDetail> constructionConfigDetails = constructionConfigDetailRepository.saveAll(details);
        config.setDetails(constructionConfigDetails);
        return config;
    }

    public ConstructionConfig getById(long id) throws SuraException {
        return constructionConfigRepository.findById(id).orElseThrow(() -> new SuraException(ErrorCodes.VALIDATION, messageService.getMessage(ErrorMessages.CONSTRUCTION_CONFIG_NOT_FOUND, new Object[]{id}), HttpStatus.BAD_REQUEST));
    }
}
