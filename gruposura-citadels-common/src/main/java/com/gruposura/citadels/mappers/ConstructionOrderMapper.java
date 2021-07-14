package com.gruposura.citadels.mappers;

import com.gruposura.citadels.enums.ConstructionStatus;
import com.gruposura.citadels.model.ConstructionConfig;
import com.gruposura.citadels.model.ConstructionOrder;
import com.gruposura.citadels.request.ConstructionOrderRequest;
import com.gruposura.citadels.response.ConstructionOrderResponse;

import java.time.LocalDateTime;

public class ConstructionOrderMapper {

    public static ConstructionOrderResponse mapToConstructionResponse(ConstructionOrder constructionOrder) {

        ConstructionOrderResponse constructionResponse = new ConstructionOrderResponse();
        constructionResponse.setId(constructionOrder.getId());
        constructionResponse.setCode(constructionOrder.getCode());
        constructionResponse.setStatus(constructionOrder.getStatus());
        constructionResponse.setConfig(constructionOrder.getConfig());
        constructionResponse.setLatitude(constructionOrder.getLatitude());
        constructionResponse.setLongitude(constructionOrder.getLongitude());
        constructionResponse.setStartDate(constructionOrder.getStartDate());
        constructionResponse.setEndDate(constructionOrder.getEndDate());
        constructionResponse.setCreatedAt(LocalDateTime.now());

        return constructionResponse;
    }

    public static ConstructionOrder mapToConstruction(String ConstructionCode, LocalDateTime start, LocalDateTime end, ConstructionOrderRequest constructionOrderRequest, ConstructionConfig config) {
        ConstructionOrder constructionOrder = new ConstructionOrder();
        constructionOrder.setCode(ConstructionCode);
        constructionOrder.setStatus(ConstructionStatus.IN_PROGRESS.name());
        constructionOrder.setConfig(config);
        constructionOrder.setLatitude(constructionOrderRequest.getLatitude());
        constructionOrder.setLongitude(constructionOrderRequest.getLongitude());
        constructionOrder.setStartDate(start);
        constructionOrder.setEndDate(end);
        constructionOrder.setCreatedAt(LocalDateTime.now());
        return constructionOrder;
    }

}
