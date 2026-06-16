package pl.altkom.asc.lab.micronaut.poc.dashboard.infrastructure.adapters.elastic;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;

import pl.altkom.asc.lab.micronaut.poc.dashboard.domain.AgentSalesQuery;
import pl.altkom.asc.lab.micronaut.poc.dashboard.domain.PolicyDocument;
import pl.altkom.asc.lab.micronaut.poc.dashboard.domain.PolicyRepository;
import pl.altkom.asc.lab.micronaut.poc.dashboard.domain.SalesTrendsQuery;
import pl.altkom.asc.lab.micronaut.poc.dashboard.domain.TotalSalesQuery;

import java.io.IOException;
import java.util.List;

import jakarta.inject.Singleton;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class PolicyElasticRepository implements PolicyRepository {

    private final ElasticsearchClient esClient;

    public void save(PolicyDocument policyDocument) {
        try {
            IndexRequest<PolicyDocument> indexRequest = IndexRequest.of(b -> b
                    .index("policy_stats")
                    .id(policyDocument.getNumber())
                    .refresh(co.elastic.clients.elasticsearch._types.Refresh.True)
                    .document(policyDocument)
            );
            esClient.index(indexRequest);
        } catch (IOException e) {
            log.error("Error while saving policy", e);
            throw new RuntimeException("Error while executing query", e);
        }
    }

    public PolicyDocument findByNumber(String number) {
        try {
            SearchRequest searchRequest = SearchRequest.of(s -> s
                    .index("policy_stats")
                    .size(10)
                    .query(q -> q
                            .bool(b -> b
                                    .must(m -> m
                                            .term(t -> t
                                                    .field("number.keyword")
                                                    .value(number)
                                            )
                                    )
                            )
                    )
            );

            SearchResponse<PolicyDocument> response = esClient.search(searchRequest, PolicyDocument.class);
            List<Hit<PolicyDocument>> hits = response.hits().hits();

            return !hits.isEmpty() ? hits.get(0).source() : null;
        } catch (IOException e) {
            throw new RuntimeException("Failed to execute search", e);
        }
    }

    public TotalSalesQuery.Result getTotalSales(TotalSalesQuery query) {
        TotalSalesQueryAdapter queryAdapter = QueryAdapter.of(query);
        SearchResponse<Void> searchResponse = executeSearch(queryAdapter.buildQuery());
        return queryAdapter.extractResult(searchResponse);
    }

    public SalesTrendsQuery.Result getSalesTrends(SalesTrendsQuery query) {
        SalesTrendsQueryAdapter queryAdapter = QueryAdapter.of(query);
        SearchResponse<Void> searchResponse = executeSearch(queryAdapter.buildQuery());
        return queryAdapter.extractResult(searchResponse);
    }

    public AgentSalesQuery.Result getAgentSales(AgentSalesQuery query) {
        AgentSalesQueryAdapter queryAdapter = QueryAdapter.of(query);
        SearchResponse<Void> searchResponse = executeSearch(queryAdapter.buildQuery());
        return queryAdapter.extractResult(searchResponse);
    }

    private SearchResponse<Void> executeSearch(SearchRequest request) {
        try {
            return esClient.search(request, Void.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to execute search", e);
        }
    }
}
