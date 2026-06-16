package pl.altkom.asc.lab.micronaut.poc.gateway;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import pl.altkom.asc.lab.micronaut.poc.gateway.client.v1.ProductGatewayClient;
import pl.altkom.asc.lab.micronaut.poc.product.service.api.v1.ProductDto;

import jakarta.inject.Inject;

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/api/products")
public class ProductGatewayController {

    @Inject
    private ProductGatewayClient client;

    @Get
    public Flux<ProductDto> getAll() {
        return client.getAll();
    }

    @Get("/{productCode}")
    public Mono<ProductDto> get(String productCode) {
        return client.get(productCode);
    }
}
