package pl.altkom.asc.lab.micronaut.poc.product.service.infrastructure.adapters.web;

import io.micronaut.http.annotation.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import lombok.RequiredArgsConstructor;
import pl.altkom.asc.lab.micronaut.poc.product.service.api.v1.ProductDto;
import pl.altkom.asc.lab.micronaut.poc.product.service.api.v1.ProductOperations;
import pl.altkom.asc.lab.micronaut.poc.product.service.domain.Products;

@Controller("/products")
@RequiredArgsConstructor
public class ProductsController implements ProductOperations {

    private final Products products;

    @Override
    public Flux<ProductDto> getAll() {
        return products.findAll().flatMapMany(list -> Flux.fromIterable(ProductsAssembler.map(list)));
    }

    @Override
    public Mono<ProductDto> get(String productCode) {
        return products.findOne(productCode).map(ProductsAssembler::map);
    }
}
