package pl.altkom.asc.lab.micronaut.poc.product.service.api.v1.questions;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.micronaut.core.annotation.Introspected;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Introspected
@NoArgsConstructor
@JsonTypeName("date")
@Getter
public class DateQuestionDto extends QuestionDto {
    public DateQuestionDto(String code, int index, String text) {
        super(code, index, text);
    }
}
