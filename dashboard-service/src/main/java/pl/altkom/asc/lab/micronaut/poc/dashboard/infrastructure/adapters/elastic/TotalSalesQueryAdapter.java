package pl.altkom.asc.lab.micronaut.poc.dashboard.infrastructure.adapters.elastic;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch._types.aggregations.FilterAggregate;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsAggregate;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsBucket;
import co.elastic.clients.elasticsearch._types.aggregations.SumAggregate;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;

import pl.altkom.asc.lab.micronaut.poc.dashboard.domain.SalesResult;
import pl.altkom.asc.lab.micronaut.poc.dashboard.domain.TotalSalesQuery;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

class TotalSalesQueryAdapter extends QueryAdapter<TotalSalesQuery, TotalSalesQuery.Result> {

    public TotalSalesQueryAdapter(TotalSalesQuery query) {
        super(query);
    }

    @Override
    SearchRequest buildQuery() {
        List<Query> filters = new ArrayList<>();
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
                        .aggregations("count_by_product", sa -> sa
                                .terms(t -> t.field("productCode.keyword"))
                                .aggregations("total_premium", sp -> sp
                                        .sum(su -> su.field("totalPremium"))
                                )
                        )
                )
        );
    }

    @Override
    TotalSalesQuery.Result extractResult(SearchResponse<Void> searchResponse) {
        TotalSalesQuery.Result.ResultBuilder result = TotalSalesQuery.Result.builder();
        long count = 0;
        BigDecimal amount = BigDecimal.ZERO;

        FilterAggregate filterAgg = searchResponse.aggregations().get("agg_filter").filter();
        StringTermsAggregate products = filterAgg.aggregations().get("count_by_product").sterms();

        for (StringTermsBucket b : products.buckets().array()) {
            count += b.docCount();
            SumAggregate sum = b.aggregations().get("total_premium").sum();
            amount = amount.add(BigDecimal.valueOf(sum.value()).setScale(2, RoundingMode.HALF_UP));
            result.productTotal(b.key().stringValue(), SalesResult.of(b.docCount(), BigDecimal.valueOf(sum.value())));
        }
        result.total(SalesResult.of(count, amount));

        return result.build();
    }

}
