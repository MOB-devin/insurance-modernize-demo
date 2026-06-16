package pl.altkom.asc.lab.micronaut.poc.auth;

import java.util.Optional;
import java.util.UUID;

import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

@JdbcRepository(dialect = Dialect.H2)
public interface InsuranceAgentsRepository extends CrudRepository<InsuranceAgent,UUID> {
    Optional<InsuranceAgent> findByLogin(String login);
}
