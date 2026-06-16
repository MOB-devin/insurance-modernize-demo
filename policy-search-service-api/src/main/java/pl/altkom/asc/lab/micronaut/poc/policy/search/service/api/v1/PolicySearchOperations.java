package pl.altkom.asc.lab.micronaut.poc.policy.search.service.api.v1;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import reactor.core.publisher.Mono;
import pl.altkom.asc.lab.micronaut.poc.policy.search.service.api.v1.queries.findpolicy.FindPolicyQueryResult;

public interface PolicySearchOperations {

    @Get
    Mono<FindPolicyQueryResult> policies(@QueryValue("q") String queryText);
}
