package com.gruposura.citadels.service;

import com.gruposura.citadels.ex.ErrorCodes;
import com.gruposura.citadels.ex.ErrorMessages;
import com.gruposura.citadels.ex.SuraException;
import com.gruposura.citadels.mappers.ConstructionOrderMapper;
import com.gruposura.citadels.model.ConstructionConfig;
import com.gruposura.citadels.model.ConstructionOrder;
import com.gruposura.citadels.repository.ConstructionOrderRepository;
import com.gruposura.citadels.request.ConstructionOrderRequest;
import com.gruposura.citadels.request.ValidateStockRequest;
import com.gruposura.citadels.response.ConstructionDayResponse;
import com.gruposura.citadels.response.ConstructionOrderResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ConstructionOrderService {

    private final ConstructionOrderRepository constructionOrderRepository;
    private final ConstructionConfigService constructionConfigService;
    private final InventoryService inventoryService;
    private final MessageService messageService;

    public ConstructionOrderService(ConstructionOrderRepository constructionOrderRepository, ConstructionConfigService constructionConfigService, InventoryService inventoryService, MessageService messageService) {
        this.constructionOrderRepository = constructionOrderRepository;
        this.constructionConfigService = constructionConfigService;
        this.inventoryService = inventoryService;
        this.messageService = messageService;
    }

    public List<ConstructionOrderResponse> findAll() {
        try {
            return constructionOrderRepository.findAll().stream().map(ConstructionOrderMapper::mapToConstructionResponse).collect(Collectors.toList());
        } catch (Exception ex) {
            log.error("ConstructionOrderService:findAll() --Error[{}]", ex.getMessage());
            throw ex;

        }
    }

    public ConstructionOrderResponse findById(long id) throws SuraException {
        try {
            return ConstructionOrderMapper.mapToConstructionResponse(getById(id));
        } catch (SuraException ex) {
            log.error("ConstructionOrderService:findById() --Error[{}]", ex.getMessage());
            throw new SuraException(ErrorCodes.BAD_REQUEST, ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            log.error("ConstructionOrderService:findById() --Error[{}]", ex.getMessage());
            throw ex;
        }
    }

    public ConstructionOrderResponse findByCode(String code) throws SuraException {
        try {
            return ConstructionOrderMapper.mapToConstructionResponse(getByCode(code));
        } catch (SuraException ex) {
            log.error("ConstructionOrderService:findByCode() --Error[{}]", ex.getMessage());
            throw new SuraException(ErrorCodes.BAD_REQUEST, ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            log.error("ConstructionOrderService:findByCode() --Error[{}]", ex.getMessage());
            throw ex;
        }
    }

    public ConstructionDayResponse findEndConstructionDate() {
        try {
            Optional<ConstructionOrder> lastConstructionOrder = constructionOrderRepository.findLastOrder();
            if (lastConstructionOrder.isPresent()) {
                ConstructionDayResponse response = new ConstructionDayResponse();
                response.setEndConstruction(ConstructionOrderMapper.mapToConstructionResponse(lastConstructionOrder.get()));
                response.setEndDate(lastConstructionOrder.get().getEndDate().toString());
                return response;
            } else {
                throw new SuraException(ErrorCodes.VALIDATION, messageService.getMessage(ErrorMessages.CONSTRUCTIONS_EMPTY), HttpStatus.BAD_REQUEST);
            }
        } catch (SuraException ex) {
            log.error("ConstructionOrderService:findEndConstructionDate() --Error[{}]", ex.getMessage());
            throw new SuraException(ErrorCodes.BAD_REQUEST, ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            log.error("ConstructionOrderService:findEndConstructionDate() --Error[{}]", ex.getMessage());
            throw ex;
        }
    }


    @Transactional(rollbackOn = Exception.class)
    public ConstructionOrderResponse create(ConstructionOrderRequest constructionOrderRequest) throws SuraException {
        try {
            Optional<ConstructionOrder> constructionOrderByLocation = constructionOrderRepository.findByLatitudeAndLongitude(constructionOrderRequest.getLatitude(), constructionOrderRequest.getLongitude());
            if (constructionOrderByLocation.isPresent()) {
                throw new SuraException(ErrorCodes.VALIDATION, messageService.getMessage(ErrorMessages.CONSTRUCTION_EXIST_BY_LOCATION, new Object[]{constructionOrderByLocation.get().getConfig().getName(), constructionOrderByLocation.get().getCode()}), HttpStatus.BAD_REQUEST);
            }
            ConstructionConfig config = constructionConfigService.findById(constructionOrderRequest.getConstructionConfigId());

            LocalDateTime constructionStartDate;
            LocalDateTime constructionEndDate;
            Optional<ConstructionOrder> lastConstructionOrder = constructionOrderRepository.findLastOrder();
            if (lastConstructionOrder.isPresent()) {
                constructionStartDate = lastConstructionOrder.get().getEndDate().plusDays(1);
                constructionEndDate = constructionStartDate.plusDays(config.getTotalDays());
            } else {
                constructionStartDate = LocalDateTime.now().plusDays(1);
                constructionEndDate = constructionStartDate.plusDays(config.getTotalDays());
            }
            List<ValidateStockRequest> validateStockRequests = config.getDetails().stream().map(detail -> new ValidateStockRequest(detail.getProductId(), detail.getQuantity())).collect(Collectors.toList());
            inventoryService.validateStock(validateStockRequests);
            validateStockRequests.forEach(validateStockRequest -> {
                inventoryService.subtractUnits(validateStockRequest.getProductId(), validateStockRequest.getQuantity());
            });
            String orderCode = UUID.randomUUID().toString();
            return ConstructionOrderMapper.mapToConstructionResponse(constructionOrderRepository.save(ConstructionOrderMapper.mapToConstruction(orderCode, constructionStartDate, constructionEndDate, constructionOrderRequest, config)));
        } catch (SuraException ex) {
            log.error("BuildersService:create() --Error:[{}]", ex.getMessage());
            throw new SuraException(ErrorCodes.BAD_REQUEST, ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            log.error("BuildersService:create() --Error:[{}]", ex.getMessage());
            throw ex;
        }
    }

    public ConstructionOrder getById(long id) throws SuraException {
        return constructionOrderRepository.findById(id).orElseThrow(() -> new SuraException(ErrorCodes.VALIDATION, messageService.getMessage(ErrorMessages.CONSTRUCTION_NOT_FOUND, new Object[]{id}), HttpStatus.BAD_REQUEST));
    }

    public ConstructionOrder getByCode(String code) throws SuraException {
        return constructionOrderRepository.findByCode(code).orElseThrow(() -> new SuraException(ErrorCodes.VALIDATION, messageService.getMessage(ErrorMessages.CONSTRUCTION_NOT_FOUND_CODE, new Object[]{code}), HttpStatus.BAD_REQUEST));
    }
}
