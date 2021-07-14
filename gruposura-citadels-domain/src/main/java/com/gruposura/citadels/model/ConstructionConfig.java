package com.gruposura.citadels.model;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class ConstructionConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "construction_config_generator")
    @SequenceGenerator(name = "construction_config_generator", sequenceName = "construction_config_seq", allocationSize = 1)
    private long id;
    private String name;
    private int totalDays;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "config")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<ConstructionConfigDetail> details;

    private LocalDateTime createdAt;

}
