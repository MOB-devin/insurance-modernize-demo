package pl.altkom.asc.lab.micronaut.poc.pricing.domain;

import java.util.Optional;
import io.micronaut.data.repository.CrudRepository;
import io.micronaut.data.annotation.*;

@Repository
public interface Tariffs extends CrudRepository<Tariff, Long>  {

    @Query("SELECT t FROM Tariff t WHERE t.code = :code")
    Optional<Tariff> findByCode(String code);

    @Query("SELECT t FROM Tariff t WHERE t.code = :code")
    Tariff getByCode(String code);
}
