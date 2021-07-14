package com.gruposura.citadels.routes;

public class Router {
    public static final String API = "/api";

    public static class Inventory {
        public static final String INVENTORY_API = API + "/inventory";
        public static final String FIND_BY_ID = "/{id}";
        public static final String FIND_BY_PRODUCT_ID = "/product/{productId}";
    }

    public static class Product {
        public static final String PRODUCT_API = API + "/product";
        public static final String FIND_PRODUCT_BY_ID = "/{id}";
        public static final String UPDATE = "/{id}";
    }

    public static class Construction {
        public static final String CONSTRUCTION_API = API + "/construction";
        public static final String FIND_BY_ID = "/{id}";
        public static final String FIND_BY_CODE = "/code/{code}";
        public static final String FIND_END_CONSTRUCTION_DATE = "/last-construction";
    }

    public static class ConstructionConfig {
        public static final String CONSTRUCTION_CONFIG_API = API + "/config";
        public static final String FIND_BY_ID = "/{id}";
    }

}
