package com.gruposura.citadels.mappers;

import com.gruposura.citadels.model.ConstructionConfig;
import com.gruposura.citadels.model.ConstructionConfigDetail;

import java.time.LocalDateTime;

public class ConstructionDetailMapper {

    public static ConstructionConfigDetail mapToConstructionDetail(String productName, long productId, long quantity, ConstructionConfig config) {
        ConstructionConfigDetail constructionConfigDetail = new ConstructionConfigDetail();
        constructionConfigDetail.setProductName(productName);
        constructionConfigDetail.setProductId(productId);
        constructionConfigDetail.setQuantity(quantity);
        constructionConfigDetail.setConfig(config);
        constructionConfigDetail.setCreateAt(LocalDateTime.now());
        return constructionConfigDetail;
    }


}
