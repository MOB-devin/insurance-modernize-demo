package pl.altkom.asc.lab.micronaut.poc.pricing.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import lombok.NoArgsConstructor;

import io.micronaut.core.annotation.Introspected;

@Introspected
@Entity
@DiscriminatorValue("perc_markup")
@NoArgsConstructor
public class PercentMarkupRule extends DiscountMarkupRule {

    PercentMarkupRule(Tariff tariff, String applyIfFormula, BigDecimal paramValue) {
        this.tariff = tariff;
        this.applyIfFormula = applyIfFormula;
        this.paramValue = paramValue;
    }

    @Override
    public Calculation apply(Calculation calculation) {
        for (Cover cover : calculation.getCovers().values()) {
            cover.setPrice(cover.getPrice()
                    .multiply(paramValue)
                    .setScale(2, RoundingMode.HALF_UP)
            );
        }
        return calculation;
    }


}
