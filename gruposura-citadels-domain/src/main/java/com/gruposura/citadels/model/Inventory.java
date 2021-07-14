package com.gruposura.citadels.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventory_generator")
    @SequenceGenerator(name = "inventory_generator", sequenceName = "inventory_seq", allocationSize = 1)
    private long id;
    private long quantity;
    private double unitCost;
    private double totalCost;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;
    private Date createdAt;
}
