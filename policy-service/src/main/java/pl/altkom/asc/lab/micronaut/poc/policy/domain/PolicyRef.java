package pl.altkom.asc.lab.micronaut.poc.policy.domain;

import lombok.*;

import jakarta.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
class PolicyRef {
    private String policyNumber;

    static PolicyRef of(Policy policy) {
        return new PolicyRef(policy.getNumber());
    }
}
