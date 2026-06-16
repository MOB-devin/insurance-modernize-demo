package pl.altkom.asc.lab.micronaut.poc.policy.domain;

import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.GenericRepository;

import java.util.Optional;

@Repository
public interface PolicyRepository extends GenericRepository<Policy, Long> {
    @Query("SELECT p FROM Policy p WHERE p.number = :number")
    Optional<Policy> findByNumber(String number);

    Policy save(Policy policy);
}
