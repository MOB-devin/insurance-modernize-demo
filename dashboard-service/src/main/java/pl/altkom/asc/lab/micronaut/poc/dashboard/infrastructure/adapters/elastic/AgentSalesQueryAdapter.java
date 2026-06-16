package pl.altkom.asc.lab.micronaut.poc.dashboard.infrastructure.adapters.elastic;

import co.elastic.clients.elasticsearch._types.aggregations.FilterAggregate;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsAggregate;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsBucket;
import co.elastic.clients.elasticsearch._types.aggregations.SumAggregate;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import pl.altkom.asc.lab.micronaut.poc.dashboard.domain.AgentSalesQuery;
import pl.altkom.asc.lab.micronaut.poc.dashboard.domain.SalesResult;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AgentSalesQueryAdapter extends QueryAdapter<AgentSalesQuery, AgentSalesQuery.Result> {
    public AgentSalesQueryAdapter(AgentSalesQuery query) {
        super(query);
    }

    @Override
    SearchRequest buildQuery() {
        List<Query> filters = new ArrayList<>();
        if (query.getFilterByAgentLogin() != null) {
            filters.add(Query.of(q -> q.term(t -> t.field("agentLogin.keyword").value(query.getFilterByAgentLogin()))));
        }
        if (query.getFilterByProductCode() != null) {
            filters.add(Query.of(q -> q.term(t -> t.field("productCode.keyword").value(query.getFilterByProductCode()))));
        }
        if (query.getFilterBySalesDate() != null) {
            filters.add(Query.of(q -> q.range(r -> r.field("from")
                    .gte(co.elastic.clients.json.JsonData.of(query.getFilterBySalesDate().getFrom().toString()))
                    .lt(co.elastic.clients.json.JsonData.of(query.getFilterBySalesDate().getTo().toString())))));
        }

        BoolQuery boolQuery = BoolQuery.of(b -> b.must(filters));

        return SearchRequest.of(s -> s
                .index("policy_stats")
                .size(0)
                .aggregations("agg_filter", a -> a
                        .filter(f -> f.bool(boolQuery))
                        .aggregations("count_by_agent", sa -> sa
                                .terms(t -> t.field("agentLogin.keyword"))
                                .aggregations("total_premium", sp -> sp
                                        .sum(su -> su.field("totalPremium"))
                                )
                        )
                )
        );
    }

    @Override
    AgentSalesQuery.Result extractResult(SearchResponse<Void> searchResponse) {
        AgentSalesQuery.Result.ResultBuilder result = AgentSalesQuery.Result.builder();
        FilterAggregate filterAgg = searchResponse.aggregations().get("agg_filter").filter();
        StringTermsAggregate agents = filterAgg.aggregations().get("count_by_agent").sterms();

        for (StringTermsBucket b : agents.buckets().array()) {
            SumAggregate sum = b.aggregations().get("total_premium").sum();
            result.agentTotal(
                    b.key().stringValue(),
                    SalesResult.of(b.docCount(), BigDecimal.valueOf(sum.value()))
            );
        }

        return result.build();
    }
}
