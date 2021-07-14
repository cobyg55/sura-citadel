package com.gruposura.citadels.controller;

import com.gruposura.citadels.ex.SuraException;
import com.gruposura.citadels.model.ConstructionConfig;
import com.gruposura.citadels.request.ConstructionConfigRequest;
import com.gruposura.citadels.routes.Router;
import com.gruposura.citadels.service.ConstructionConfigService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Router.ConstructionConfig.CONSTRUCTION_CONFIG_API)
public class ConstructionConfigController {

    private final ConstructionConfigService constructionConfigService;

    public ConstructionConfigController(ConstructionConfigService constructionConfigService) {
        this.constructionConfigService = constructionConfigService;
    }

    @GetMapping
    public List<ConstructionConfig> findAll() throws SuraException {
        return constructionConfigService.findAll();
    }

    @GetMapping(Router.ConstructionConfig.FIND_BY_ID)
    public ConstructionConfig findById(@PathVariable("id") long id) throws SuraException {
        return constructionConfigService.findById(id);
    }

    @PostMapping
    public ConstructionConfig create(@RequestBody ConstructionConfigRequest constructionConfigRequest) throws SuraException {
        return constructionConfigService.create(constructionConfigRequest);
    }
}
