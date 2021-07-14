package com.gruposura.citadels.request;

import lombok.Data;

@Data
public class ConstructionConfigDetailRequest {
    private String productName;
    private long productId;
    private long quantity;
}
