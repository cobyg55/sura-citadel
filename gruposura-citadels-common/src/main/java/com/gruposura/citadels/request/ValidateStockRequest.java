package com.gruposura.citadels.request;

import lombok.Data;

@Data
public class ValidateStockRequest {
    private long productId;
    private long quantity;

    public ValidateStockRequest() {

    }

    public ValidateStockRequest(long productId, long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
