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
            "/ingredient/all/byCategory",
            "/ingredient/all/byCategory/filter",
            "/ingredient/all/name",
            "/recipe/findByName",
            "/recipe/findById",
            "/restaurant/id",
            "/restaurant/owner",
            "/recipe/all",
            "/recipe/all/filtered",
            "/utils/measurements",
            "/auth/test",
            "/auth/login",
            "/person/create",
            "/review/create",
            "/review/rating",
            "/review/all/entity",
            "/person/name",
            "/person/details/name",
            "/review/check/entity/existence",
            "/review/all/user",
            "/recipe/all/owner/username",
            "/recipe/favoriteList",
            "/recipe/favoriteNames"
    );
}