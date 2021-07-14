package com.gruposura.citadels.request;

import lombok.Data;

@Data
public class InventoryRequest {
    private long quantity;
    private long unitCost;
    private long productId;
}
