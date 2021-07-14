package com.gruposura.citadels.mappers;

import com.gruposura.citadels.model.Inventory;
import com.gruposura.citadels.model.Product;
import com.gruposura.citadels.request.InventoryRequest;
import com.gruposura.citadels.response.InventoryResponse;

import java.util.Date;

public class InventoryMapper {

    public static InventoryResponse mapToInventoryResponse(Inventory inventory) {
        InventoryResponse inventoryResponse = new InventoryResponse();
        inventoryResponse.setId(inventory.getId());
        inventoryResponse.setQuantity(inventory.getQuantity());
        inventoryResponse.setUnitCost(inventory.getUnitCost());
        inventoryResponse.setProduct(inventory.getProduct().getId());
        inventoryResponse.setTotalCost(inventory.getTotalCost());
        inventoryResponse.setCreatedAt(new Date());
        return inventoryResponse;
    }

    public static Inventory mapToInventory(InventoryRequest inventoryRequest, Product product) {
        Inventory inventory = new Inventory();
        inventory.setQuantity(inventoryRequest.getQuantity());
        inventory.setUnitCost(inventoryRequest.getUnitCost());
        inventory.setTotalCost(inventoryRequest.getUnitCost() * inventoryRequest.getQuantity());
        inventory.setProduct(product);
        inventory.setCreatedAt(new Date());
        return inventory;
    }

}
