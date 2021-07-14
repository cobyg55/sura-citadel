package com.gruposura.citadels.response;

import lombok.Data;

@Data
public class ConstructionDayResponse {
    private String endDate;
    private ConstructionOrderResponse endConstruction;
}
