package com.gruposura.citadels.mappers;

import com.gruposura.citadels.model.ConstructionConfig;
import com.gruposura.citadels.request.ConstructionConfigRequest;

import java.time.LocalDateTime;

public class ConstructionConfigMapper {

    public static ConstructionConfig mapToConstructionConfig(ConstructionConfigRequest constructionConfigRequest) {
        ConstructionConfig constructionConfig = new ConstructionConfig();
        constructionConfig.setName(constructionConfigRequest.getName());
        constructionConfig.setTotalDays(constructionConfigRequest.getTotalDays());
        constructionConfig.setCreatedAt(LocalDateTime.now());
        return constructionConfig;
    }

}
