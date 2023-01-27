package com.licenta.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;

@Configuration
public class GatewayConfig {

    private final AuthenticationFilter filter;

    public GatewayConfig(AuthenticationFilter filter) {
        this.filter = filter;
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("core-service-auth", r -> r.path("/v1/core-api/auth/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:3001/v1/core-api/auth"))
                .route("core-service-person", r -> r.path("/v1/core-api/person/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:3001/v1/core-api/person"))
                .route("core-service-review", r -> r.path("/v1/core-api/review/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:3001/v1/core-api/review"))
                .route("food-service-ingredient", r -> r.path("/v1/food-api/ingredient/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:3000/v1/food-api/ingredient"))
                .route("food-service-recipe", r -> r.path("/v1/food-api/recipe/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:3000/v1/food-api/recipe"))
                .route("food-service-recipe", r -> r.path("/v1/food-api/utils/measurements")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:3000/v1/food-api/utils/measurements"))
                .route("restaurant-service-restaurant", r -> r.path("/v1/restaurant-api/restaurant/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:3002/v1/restaurant-api/restaurant"))
                .route("restaurant-service-menuItem", r -> r.path("/v1/restaurant-api/menuItem/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:3002/v1/restaurant-api/menuItem"))
                .route("restaurant-service-menu", r -> r.path("/v1/restaurant-api/menu/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:3002/v1/restaurant-api/menu"))
                .build();
    }
}