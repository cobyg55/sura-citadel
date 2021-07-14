package com.gruposura.citadels.controller;


import com.gruposura.citadels.ex.SuraException;
import com.gruposura.citadels.request.ConstructionOrderRequest;
import com.gruposura.citadels.response.ConstructionDayResponse;
import com.gruposura.citadels.response.ConstructionOrderResponse;
import com.gruposura.citadels.routes.Router;
import com.gruposura.citadels.service.ConstructionOrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Router.Construction.CONSTRUCTION_API)
public class ConstructionOrderController {

    private final ConstructionOrderService constructionOrderService;

    public ConstructionOrderController(ConstructionOrderService constructionOrderService) {
        this.constructionOrderService = constructionOrderService;
    }

    @GetMapping
    public List<ConstructionOrderResponse> findAll() {
        return constructionOrderService.findAll();
    }

    @GetMapping(Router.Construction.FIND_BY_ID)
    public ConstructionOrderResponse findById(@PathVariable("id") long id) throws SuraException {
        return constructionOrderService.findById(id);
    }

    @GetMapping(Router.Construction.FIND_BY_CODE)
    public ConstructionOrderResponse findByCode(@PathVariable("code") String code) throws SuraException {
        return constructionOrderService.findByCode(code);
    }

    @GetMapping(Router.Construction.FIND_END_CONSTRUCTION_DATE)
    public ConstructionDayResponse findEndConstructionDate() throws SuraException {
        return constructionOrderService.findEndConstructionDate();
    }

    @PostMapping
    public ConstructionOrderResponse create(@RequestBody ConstructionOrderRequest constructionOrderRequest) throws SuraException {
        return constructionOrderService.create(constructionOrderRequest);
    }
}
