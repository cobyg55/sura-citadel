package com.gruposura.citadels.response;

import lombok.Data;

import java.util.Date;

@Data
public class InventoryResponse {
    private long id;
    private long quantity;
    private double unitCost;
    private double totalCost;
    private long product;
    private Date createdAt;
}


