package pl.altkom.asc.lab.micronaut.poc.policy.search.service.api.v1.queries.findpolicy;

import pl.altkom.asc.lab.micronaut.poc.command.bus.api.Query;

import io.micronaut.core.annotation.Introspected;
import reactor.core.publisher.Mono;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Introspected
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FindPolicyQuery implements Query<Mono<FindPolicyQueryResult>> {
    private String queryText; 
}
