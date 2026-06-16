package pl.altkom.asc.lab.micronaut.poc.auth;

import io.micronaut.security.authentication.AuthenticationResponse;
import lombok.Getter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
class InsuranceAgentDetails implements AuthenticationResponse {

    private final String username;
    private final String avatarUrl;
    private final Collection<String> roles;

    InsuranceAgentDetails(String username, String avatarUrl, Collection<String> roles) {
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.roles = roles;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public Optional<String> getAuthentication() {
        return Optional.of(username);
    }

    @Override
    public Map<String, Object> getAttributes() {
        Map<String, Object> attrs = new HashMap<>();
        attrs.put("roles", roles);
        attrs.put("avatar", avatarUrl);
        return attrs;
    }

    @Override
    public Collection<String> getRoles() {
        return roles;
    }
}
