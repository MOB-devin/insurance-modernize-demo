package pl.altkom.asc.lab.micronaut.poc.policy.search.service.api.v1;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import pl.altkom.asc.lab.micronaut.poc.policy.search.service.api.v1.queries.findpolicy.FindPolicyQueryResult;

public interface PolicySearchOperations {

    @Get
    FindPolicyQueryResult policies(@QueryValue("q") String queryText);
}
