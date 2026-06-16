package pl.altkom.asc.lab.micronaut.poc.policy.service.api.v1.commands.createoffer.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import io.micronaut.core.annotation.Introspected;

@Introspected
@JsonTypeName("choice")
public class ChoiceQuestionAnswer extends QuestionAnswer<String> {
    @JsonCreator
    public ChoiceQuestionAnswer(@JsonProperty("questionCode") String questionCode, @JsonProperty("answer") String answer) {
        super(questionCode, answer);
    }
}
