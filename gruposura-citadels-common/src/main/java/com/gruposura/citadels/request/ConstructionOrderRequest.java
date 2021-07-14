package com.gruposura.citadels.request;

import lombok.Data;

@Data
public class ConstructionOrderRequest {
    private long constructionConfigId;
    private String latitude;
    private String longitude;
}
