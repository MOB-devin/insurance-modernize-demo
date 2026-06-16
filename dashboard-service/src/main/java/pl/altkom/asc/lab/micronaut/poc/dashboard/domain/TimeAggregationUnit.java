package pl.altkom.asc.lab.micronaut.poc.dashboard.domain;

import co.elastic.clients.elasticsearch._types.aggregations.CalendarInterval;

public enum TimeAggregationUnit {
    DAY,
    WEEK,
    MONTH,
    YEAR;

    public CalendarInterval toCalendarInterval() {
        return switch (this) {
            case DAY -> CalendarInterval.Day;
            case WEEK -> CalendarInterval.Week;
            case MONTH -> CalendarInterval.Month;
            case YEAR -> CalendarInterval.Year;
        };
    }
}
