package pl.altkom.asc.lab.micronaut.poc.auth;

import org.reactivestreams.Publisher;

import java.util.Optional;

import jakarta.inject.Singleton;

import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import reactor.core.publisher.Flux;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class AuthProvider<B> implements AuthenticationProvider<B> {

    private final InsuranceAgentsRepository insuranceAgents;

    @Override
    public Publisher<AuthenticationResponse> authenticate(HttpRequest<B> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
        Optional<InsuranceAgent> agent = insuranceAgents.findByLogin((String) authenticationRequest.getIdentity());

        if (agent.isPresent() && agent.get().passwordMatches((String) authenticationRequest.getSecret())) {
            return Flux.just(createUserDetails(agent.get()));
        }

        return Flux.just(AuthenticationResponse.failure());
    }

    private InsuranceAgentDetails createUserDetails(InsuranceAgent agent) {
        return new InsuranceAgentDetails(agent.login(), agent.avatar(), agent.availableProductCodes());
    }
}
