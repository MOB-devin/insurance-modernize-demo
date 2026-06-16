package pl.altkom.asc.lab.micronaut.poc.product.service.domain;

import reactor.core.publisher.Mono;

import java.util.List;

public interface Products {

    Mono<Product> add(Product product);

    Mono<List<Product>> findAll();

    Mono<Product> findOne(String productCode);
}
