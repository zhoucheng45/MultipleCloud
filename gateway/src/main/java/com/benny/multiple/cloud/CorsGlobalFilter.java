package com.benny.multiple.cloud;

import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class CorsGlobalFilter implements WebFilter, Ordered {

    private static final String ALL = "*";
    private static final String MAX_AGE = "3600L";

    // 允许可以跨域的域名
    private static final List<String> ALLOWED_ORIGINS = Arrays.asList("https://example1.com", "http://example2.com");

    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String origin = request.getHeaders().getOrigin();
        if (CorsUtils.isCorsRequest(request) && ALLOWED_ORIGINS.contains(origin)) {
            response.getHeaders().add("Access-Control-Allow-Origin", origin);
            response.getHeaders().add("Access-Control-Allow-Methods", ALL);
            response.getHeaders().add("Access-Control-Max-Age", MAX_AGE);
            response.getHeaders().add("Access-Control-Allow-Headers", ALL);
        }
        if (CorsUtils.isCorsRequest(request) && !ALLOWED_ORIGINS.contains(origin)) {
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return Mono.empty();
        }
        // 枚举，直接等号判断。 当是options请求时，直接返回，不转发到后端服务
        if(CorsUtils.isCorsRequest(request) &&  request.getMethod() == HttpMethod.OPTIONS){
            //返回204状态码
            response.setStatusCode(HttpStatus.NO_CONTENT);
            return Mono.empty();
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1; // 设置为最高优先级
    }
}
