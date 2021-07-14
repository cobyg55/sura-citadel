package com.gruposura.citadels.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class ConstructionConfigDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "construction_config_detail_generator")
    @SequenceGenerator(name = "construction_config_detail_generator", sequenceName = "construction_config_detail_seq", allocationSize = 1)
    private long id;
    private long productId;
    private String productName;
    private long quantity;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "config_id")
    private ConstructionConfig config;
    private LocalDateTime createAt;
}
