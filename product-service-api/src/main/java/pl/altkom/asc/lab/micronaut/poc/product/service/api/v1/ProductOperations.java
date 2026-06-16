package pl.altkom.asc.lab.micronaut.poc.product.service.api.v1;

import io.micronaut.http.annotation.Get;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ProductOperations {

    @Get
    Flux<ProductDto> getAll();

    @Get("/{productCode}")
    Mono<ProductDto> get(String productCode);
}
