package com.gruposura.citadels.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class ConstructionOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "construction_order_generator")
    @SequenceGenerator(name = "construction_order_generator", sequenceName = "construction_order_seq", allocationSize = 1)
    private long id;
    private String code;
    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "config_id")
    private ConstructionConfig config;
    private String latitude;
    private String longitude;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime createdAt;

}
