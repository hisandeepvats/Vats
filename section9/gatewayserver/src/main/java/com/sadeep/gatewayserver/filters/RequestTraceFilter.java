package com.sadeep.gatewayserver.filters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
@Order(1)
@Component
public class RequestTraceFilter implements GlobalFilter {
    private static final Logger logger = LoggerFactory.getLogger(RequestTraceFilter.class);
    @Autowired
    FilterUtility filterUtility;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) ");
        HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
        if(isCorrelationIdPresent(requestHeaders)) {
            logger.debug("vatsBank-correlation-id found in Request TraceFilter: {}", filterUtility.getCorrelationId(requestHeaders));
        } else {
            String correlationId = generateCorrelationId();
            exchange = filterUtility.setCorrelationId(exchange, correlationId);
            logger.debug("vatsBank-correlation-id generated in Request TraceFilter: {}", correlationId);
        }
        return chain.filter(exchange);
    }
    private boolean isCorrelationIdPresent(HttpHeaders requestHeaders) {
        if (filterUtility.getCorrelationId(requestHeaders) != null) {
            logger.info("boolean isCorrelationIdPresent(HttpHeaders requestHeaders) true ");
            return true;
        } else {
            logger.info("boolean isCorrelationIdPresent(HttpHeaders requestHeaders) false");
            return false;
        }
    }
    private String generateCorrelationId() {
        logger.debug("String generateCorrelationId() ");
        return java.util.UUID.randomUUID().toString();
    }
}
