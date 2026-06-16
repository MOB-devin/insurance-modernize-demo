package pl.altkom.asc.lab.micronaut.poc.policy.search.readmodel;

import java.util.List;
import pl.altkom.asc.lab.micronaut.poc.policy.search.service.api.v1.queries.findpolicy.FindPolicyQuery;

public interface PolicyViewRepository {

    List<PolicyView> findAll(FindPolicyQuery query);

    void save(PolicyView view);
}
