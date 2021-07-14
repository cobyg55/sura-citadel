package com.gruposura.citadels.controller;

import com.gruposura.citadels.ex.SuraException;
import com.gruposura.citadels.model.Inventory;
import com.gruposura.citadels.request.InventoryRequest;
import com.gruposura.citadels.response.InventoryResponse;
import com.gruposura.citadels.routes.Router;
import com.gruposura.citadels.service.InventoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Router.Inventory.INVENTORY_API)
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public List<InventoryResponse> findAll() {
        return inventoryService.findAll();
    }

    @GetMapping(Router.Inventory.FIND_BY_ID)
    public InventoryResponse findById(@PathVariable("id") long id) throws SuraException {
        return inventoryService.findById(id);
    }

    @PostMapping
    public InventoryResponse create(@RequestBody InventoryRequest inventoryRequest) throws SuraException {
        return inventoryService.create(inventoryRequest);
    }

    @GetMapping(Router.Inventory.FIND_BY_PRODUCT_ID)
    public Inventory findByProductId(@PathVariable("productId") long productId) throws SuraException {
        return inventoryService.findByProductId(productId);
    }

}
