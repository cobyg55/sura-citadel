package com.gruposura.citadels.response;

import com.gruposura.citadels.model.ConstructionConfig;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConstructionOrderResponse {
    private long id;
    private String code;
    private String status;
    private ConstructionConfig config;
    private String latitude;
    private String longitude;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime createdAt;
}
