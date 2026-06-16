package pl.altkom.asc.lab.micronaut.poc.auth;

import com.nimbusds.jwt.JWTClaimsSet;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.runtime.ApplicationConfiguration;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.token.config.TokenConfiguration;
import io.micronaut.security.token.claims.ClaimsAudienceProvider;
import io.micronaut.security.token.jwt.generator.claims.JWTClaimsSetGenerator;
import io.micronaut.security.token.claims.JtiGenerator;

import jakarta.annotation.Nullable;
import jakarta.inject.Singleton;

@Singleton
@Replaces(bean = JWTClaimsSetGenerator.class)
public class InsuranceAgentJWTClaimsSetGenerator extends JWTClaimsSetGenerator {

    public InsuranceAgentJWTClaimsSetGenerator(TokenConfiguration tokenConfiguration,
                                               @Nullable JtiGenerator jtiGenerator,
                                               @Nullable ClaimsAudienceProvider claimsAudienceProvider,
                                               @Nullable ApplicationConfiguration applicationConfiguration) {
        super(tokenConfiguration, jtiGenerator, claimsAudienceProvider, applicationConfiguration);
    }

    @Override
    protected void populateWithAuthentication(JWTClaimsSet.Builder builder, Authentication authentication) {
        super.populateWithAuthentication(builder, authentication);
        Object avatar = authentication.getAttributes().get("avatar");
        if (avatar != null) {
            builder.claim("avatar", avatar);
        }
    }
}
