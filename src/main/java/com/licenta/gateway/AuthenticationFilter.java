package com.licenta.gateway;

import io.jsonwebtoken.Claims;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
@RefreshScope
public class AuthenticationFilter implements GatewayFilter {

    private final JwtUtil jwtUtil;

    private static final List<String> CONTEXT_APPS = Arrays.asList(
            "/v1/core-api", "/v1/food-api", "/v1/restaurant-api"
    );

    public AuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (!testRequestFromGuest(request.getPath().value())) {
            if (this.isAuthMissing(request))
                return this.onError(exchange, "Authorization header is missing in request", HttpStatus.UNAUTHORIZED);

            final String token = this.getAuthHeader(request);

            if (jwtUtil.isInvalid(token))
                return this.onError(exchange, "Authorization header is invalid", HttpStatus.UNAUTHORIZED);

            this.populateRequestWithHeaders(exchange, token);
        }
        else {
            exchange.getRequest().mutate()
                    .header("authorities", String.valueOf("[GUEST]"))
                    .build();
        }

        return chain.filter(exchange);
    }

    private Boolean testRequestFromGuest(String url) {
        return RouterValidator.openApiEndpoints.contains(formatUrl(url));
    }

    private String formatUrl(String url) {

        for (String path: CONTEXT_APPS) {
            if (url.contains(path)) {
                return url.replace(path, "");
            }
        }

        return url;
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0);
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }

    private void populateRequestWithHeaders(ServerWebExchange exchange, String token) {
        Claims claims = jwtUtil.getAllClaimsFromToken(token);
        exchange.getRequest().mutate()
                .header("authorities", String.valueOf(claims.get("authorities")))
                .header("email", String.valueOf(claims.get("email")))
                .build();
    }
}