package pl.altkom.asc.lab.micronaut.poc.policy.search.infrastructure.adapters.db;

import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.altkom.asc.lab.micronaut.poc.policy.search.readmodel.PolicyView;
import pl.altkom.asc.lab.micronaut.poc.policy.search.readmodel.PolicyViewRepository;
import pl.altkom.asc.lab.micronaut.poc.policy.search.service.api.v1.queries.findpolicy.FindPolicyQuery;

@Singleton
@Slf4j
@RequiredArgsConstructor
public class ElasticPolicyViewRepository implements PolicyViewRepository {

    private static final String INDEX_NAME = "policy-views";

    private final ElasticClientAdapter elasticClientAdapter;

    @Override
    public void save(PolicyView policy) {
        try {
            IndexRequest<PolicyView> indexRequest = IndexRequest.of(b -> b
                    .index(INDEX_NAME)
                    .id(policy.getNumber())
                    .document(policy)
            );
            elasticClientAdapter.index(indexRequest);
        } catch (IOException e) {
            log.error("Failed to index policy view", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PolicyView> findAll(FindPolicyQuery query) {
        try {
            SearchRequest searchRequest = SearchRequest.of(s -> s
                    .index(INDEX_NAME)
                    .size(100)
                    .query(q -> q
                            .queryString(qs -> qs
                                    .query(query.getQueryText())
                                    .fields("number", "policyHolder")
                            )
                    )
            );

            SearchResponse<PolicyView> response = elasticClientAdapter.search(searchRequest, PolicyView.class);
            return response.hits().hits().stream()
                    .map(Hit::source)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.error("Failed to search policies", e);
            throw new RuntimeException(e);
        }
    }
}
