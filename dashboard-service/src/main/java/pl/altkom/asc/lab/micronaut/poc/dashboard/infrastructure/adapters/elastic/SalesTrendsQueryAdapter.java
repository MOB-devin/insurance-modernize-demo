package pl.altkom.asc.lab.micronaut.poc.dashboard.infrastructure.adapters.elastic;

import co.elastic.clients.elasticsearch._types.aggregations.DateHistogramBucket;
import co.elastic.clients.elasticsearch._types.aggregations.FilterAggregate;
import co.elastic.clients.elasticsearch._types.aggregations.SumAggregate;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;

import pl.altkom.asc.lab.micronaut.poc.dashboard.domain.SalesResult;
import pl.altkom.asc.lab.micronaut.poc.dashboard.domain.SalesTrendsQuery;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class SalesTrendsQueryAdapter extends QueryAdapter<SalesTrendsQuery, SalesTrendsQuery.Result> {
    public SalesTrendsQueryAdapter(SalesTrendsQuery query) {
        super(query);
    }

    @Override
    SearchRequest buildQuery() {
        List<Query> filters = new ArrayList<>();
        if (query.getFilterByProductCode() != null) {
            filters.add(Query.of(q -> q.term(t -> t.field("productCode.keyword").value(query.getFilterByProductCode()))));
        }
        if (query.getFilterBySalesDate() != null) {
            filters.add(Query.of(q -> q.range(r -> r.untyped(u -> u.field("from")
                    .gte(co.elastic.clients.json.JsonData.of(query.getFilterBySalesDate().getFrom().toString()))
                    .lt(co.elastic.clients.json.JsonData.of(query.getFilterBySalesDate().getTo().toString()))))));
        }

        BoolQuery boolQuery = BoolQuery.of(b -> b.must(filters));

        return SearchRequest.of(s -> s
                .index("policy_stats")
                .size(0)
                .aggregations("agg_filter", a -> a
                        .filter(f -> f.bool(boolQuery))
                        .aggregations("sales", sa -> sa
                                .dateHistogram(dh -> dh
                                        .field("from")
                                        .calendarInterval(query.getAggregationUnit().toCalendarInterval())
                                )
                                .aggregations("total_premium", sp -> sp
                                        .sum(su -> su.field("totalPremium"))
                                )
                        )
                )
        );
    }

    @Override
    SalesTrendsQuery.Result extractResult(SearchResponse<Void> searchResponse) {
        SalesTrendsQuery.Result.ResultBuilder result = SalesTrendsQuery.Result.builder();

        FilterAggregate filterAgg = searchResponse.aggregations().get("agg_filter").filter();
        List<DateHistogramBucket> buckets = filterAgg.aggregations().get("sales").dateHistogram().buckets().array();

        for (DateHistogramBucket b : buckets) {
            SumAggregate sum = b.aggregations().get("total_premium").sum();
            LocalDate key = Instant.ofEpochMilli(b.key()).atZone(ZoneOffset.UTC).toLocalDate();
            result.periodSale(
                    new SalesTrendsQuery.PeriodSales(
                            key,
                            b.keyAsString(),
                            SalesResult.of(b.docCount(), BigDecimal.valueOf(sum.value()).setScale(2, RoundingMode.HALF_UP))
                    )
            );
        }

        return result.build();
    }
}
