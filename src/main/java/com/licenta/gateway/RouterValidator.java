package com.licenta.gateway;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouterValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/ingredient/name",
            "/ingredient/category",
            "/ingredient/all",
            "/all/byCategory",
            "/all/byCategory/filter",
            "/all/name",
            "/recipe/name",
            "/recipe/id",
            "/recipe/all",
            "/all/filterIngredients",
            "/utils/measurements",
            "/auth/test",
            "/auth/login",
            "/person/create"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}