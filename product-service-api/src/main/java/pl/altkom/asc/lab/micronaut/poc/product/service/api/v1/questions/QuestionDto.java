package pl.altkom.asc.lab.micronaut.poc.product.service.api.v1.questions;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import io.micronaut.core.annotation.Introspected;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Introspected
@NoArgsConstructor
@Getter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
public class QuestionDto {
    private String code;
    private int index;
    private String text;

    public QuestionDto(String code, int index, String text) {
        this.code = code;
        this.index = index;
        this.text = text;
    }
}
