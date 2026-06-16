package pl.altkom.asc.lab.micronaut.poc.gateway.client.v1.fallback;

import io.micronaut.retry.annotation.Fallback;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import pl.altkom.asc.lab.micronaut.poc.gateway.client.v1.ProductGatewayClient;
import pl.altkom.asc.lab.micronaut.poc.product.service.api.v1.ProductDto;

import jakarta.inject.Singleton;

@Singleton
@Fallback
public class ProductGatewayClientFallback implements ProductGatewayClient {

    @Override
    public Flux<ProductDto> getAll() {
        return Flux.empty();
    }

    @Override
    public Mono<ProductDto> get(String productCode) {
        return Mono.empty();
    }
}
