package pl.altkom.asc.lab.micronaut.poc.payment.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDate;

import io.micronaut.core.annotation.Introspected;

@Introspected
@Entity
@DiscriminatorValue(value = "expected_payment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExpectedPayment extends AccountingEntry {

    public ExpectedPayment(PolicyAccount policyAccount, LocalDate creationDate, LocalDate effectiveDate, BigDecimal amount) {
        super(policyAccount, creationDate, effectiveDate, amount);
    }

    @Override
    public BigDecimal apply(BigDecimal state) {
        return state.subtract(getAmount());
    }

}
