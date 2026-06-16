package pl.altkom.asc.lab.micronaut.poc.policy.domain;

import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.GenericRepository;

@Repository
public interface OfferRepository extends GenericRepository<Offer, Long> {
    @Query("SELECT o FROM Offer o WHERE o.number = :number")
    Offer getByNumber(String number);

    Offer save(Offer offer);
}
