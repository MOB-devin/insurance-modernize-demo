package pl.altkom.asc.lab.micronaut.poc.auth;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import jakarta.inject.Singleton;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.authentication.provider.AuthenticationProvider;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class AuthProvider<T> implements AuthenticationProvider<T, String, String> {

    private final InsuranceAgentsRepository insuranceAgents;

    @Override
    @NonNull
    public AuthenticationResponse authenticate(@Nullable T requestContext, @NonNull AuthenticationRequest<String, String> authenticationRequest) {
        Optional<InsuranceAgent> agent = insuranceAgents.findByLogin(authenticationRequest.getIdentity());

        if (agent.isPresent() && agent.get().passwordMatches(authenticationRequest.getSecret())) {
            InsuranceAgent a = agent.get();
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("avatar", a.avatar());
            return AuthenticationResponse.success(a.login(), a.availableProductCodes(), attributes);
        }

        return AuthenticationResponse.failure();
    }
}
