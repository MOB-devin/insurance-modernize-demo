package pl.altkom.asc.lab.micronaut.poc.policy.service.api.v1.commands.createoffer.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.math.BigDecimal;

import io.micronaut.core.annotation.Introspected;

@Introspected
@JsonTypeName("numeric")
public class NumericQuestionAnswer extends QuestionAnswer<BigDecimal> {
    @JsonCreator
    public NumericQuestionAnswer(@JsonProperty("questionCode") String questionCode, @JsonProperty("answer") BigDecimal answer) {
        super(questionCode, answer);
    }
}
