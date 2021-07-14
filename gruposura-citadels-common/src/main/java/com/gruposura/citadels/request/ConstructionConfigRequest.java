package com.gruposura.citadels.request;

import lombok.Data;

import java.util.List;

@Data
public class ConstructionConfigRequest {
    private String name;
    private int totalDays;
    private List<ConstructionConfigDetailRequest> details;
}
