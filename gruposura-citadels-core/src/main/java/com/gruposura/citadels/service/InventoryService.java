package com.gruposura.citadels.service;

import com.gruposura.citadels.ex.ErrorCodes;
import com.gruposura.citadels.ex.ErrorMessages;
import com.gruposura.citadels.ex.SuraException;
import com.gruposura.citadels.mappers.InventoryMapper;
import com.gruposura.citadels.model.Inventory;
import com.gruposura.citadels.model.Product;
import com.gruposura.citadels.repository.InventoryRepository;
import com.gruposura.citadels.request.InventoryRequest;
import com.gruposura.citadels.request.ValidateStockRequest;
import com.gruposura.citadels.response.InventoryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductService productService;
    private final MessageService messageService;

    public InventoryService(InventoryRepository inventoryRepository, ProductService productService, MessageService messageService) {
        this.inventoryRepository = inventoryRepository;
        this.productService = productService;
        this.messageService = messageService;
    }

    public List<InventoryResponse> findAll() {
        return inventoryRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt")).stream().map(InventoryMapper::mapToInventoryResponse).collect(Collectors.toList());
    }

    public InventoryResponse findById(long id) throws SuraException {
        try {
            Inventory inventory = getById(id);
            if (inventory == null) {
                throw new SuraException(ErrorCodes.BAD_REQUEST, messageService.getMessage(ErrorMessages.INVENTORY_NOT_FOUNT, new Object[]{id}), HttpStatus.BAD_REQUEST);
            }
            return InventoryMapper.mapToInventoryResponse(inventory);
        } catch (SuraException ex) {
            log.error("InventoryService:findById() --Error:[{}]", ex.getMessage());
            throw new SuraException(ErrorCodes.BAD_REQUEST, ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            log.error("InventoryService:findById() --Error:[{}]", ex.getMessage());
            throw ex;
        }
    }

    public Inventory findByProductId(long productId) throws SuraException {
        try {
            return inventoryRepository.findByProductId(productId).orElse(null);
        } catch (Exception ex) {
            log.error("InventoryService:findByProductId() --Error:[{}]", ex.getMessage());
            throw ex;
        }
    }

    public void validateStock(List<ValidateStockRequest> validateStockRequest) {
        for (ValidateStockRequest stockRequest : validateStockRequest) {
            Inventory productInventory = findByProductId(stockRequest.getProductId());
            if (productInventory == null) {
                throw new SuraException(ErrorCodes.BAD_REQUEST, messageService.getMessage(ErrorMessages.INVENTORY_PRODUCT_NOT_FOUNT, new Object[]{stockRequest.getProductId()}), HttpStatus.BAD_REQUEST);
            }
            if (productInventory.getQuantity() < stockRequest.getQuantity()) {
                throw new SuraException(ErrorCodes.BAD_REQUEST, messageService.getMessage(ErrorMessages.INVENTORY_PRODUCT_NOT_STOCK, new Object[]{productInventory.getProduct().getName()}), HttpStatus.BAD_REQUEST);
            }
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public void subtractUnits(long productId, long units) {
        try {
            Inventory productInventory = findByProductId(productId);
            if (productInventory == null) {
                throw new SuraException(ErrorCodes.BAD_REQUEST, messageService.getMessage(ErrorMessages.INVENTORY_PRODUCT_NOT_FOUNT, new Object[]{productId}), HttpStatus.BAD_REQUEST);
            }
            if (units > productInventory.getQuantity()) {
                throw new SuraException(ErrorCodes.VALIDATION, messageService.getMessage(ErrorMessages.INVENTORY_PRODUCT_NOT_STOCK), HttpStatus.BAD_REQUEST);
            }
            productInventory.setQuantity(productInventory.getQuantity() - units);
            productInventory.setTotalCost(productInventory.getQuantity() * productInventory.getUnitCost());
            inventoryRepository.save(productInventory);
        } catch (SuraException ex) {
            log.error("InventoryService:subtractUnits() --Error:[{}]", ex.getMessage());
            throw new SuraException(ErrorCodes.BAD_REQUEST, ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            log.error("InventoryService:subtractUnits() --Error:[{}]", ex.getMessage());
            throw ex;
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public InventoryResponse create(InventoryRequest inventoryRequest) throws SuraException {
        try {
            Inventory productInventory = findByProductId(inventoryRequest.getProductId());
            if (productInventory == null) {
                Product product = productService.getById(inventoryRequest.getProductId());
                Inventory newInventory = inventoryRepository.save(InventoryMapper.mapToInventory(inventoryRequest, productService.getById(product.getId())));
                return InventoryMapper.mapToInventoryResponse(newInventory);
            } else {
                throw new SuraException(ErrorCodes.BAD_REQUEST, messageService.getMessage(ErrorMessages.INVENTORY_ALREADY_EXISTS, new Object[]{productInventory.getProduct().getName()}), HttpStatus.BAD_REQUEST);
            }
        } catch (SuraException ex) {
            log.error("InventoryService:create() --Error:[{}]", ex.getMessage());
            throw new SuraException(ErrorCodes.BAD_REQUEST, ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            log.error("InventoryService:create() --Error:[{}]", ex.getMessage());
            throw ex;
        }
    }

    public Inventory getById(long id) throws SuraException {
        return inventoryRepository.findById(id).orElseThrow(() -> new SuraException(ErrorCodes.VALIDATION, messageService.getMessage(ErrorMessages.INVENTORY_NOT_FOUNT, new Object[]{id}), HttpStatus.BAD_REQUEST));
    }


}
