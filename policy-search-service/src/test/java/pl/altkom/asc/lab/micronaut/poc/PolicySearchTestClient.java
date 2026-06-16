package pl.altkom.asc.lab.micronaut.poc;

import reactor.core.publisher.Mono;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.annotation.QueryValue;
import pl.altkom.asc.lab.micronaut.poc.policy.search.service.api.v1.queries.findpolicy.FindPolicyQueryResult;

@Client(id = "/policy-search-service", path = "/policies")
public interface PolicySearchTestClient {

    @Get
    Mono<FindPolicyQueryResult> policies(@QueryValue("q") String queryText);
}
