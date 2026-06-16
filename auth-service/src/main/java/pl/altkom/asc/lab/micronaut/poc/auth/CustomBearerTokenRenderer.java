package pl.altkom.asc.lab.micronaut.poc.auth;

import io.micronaut.context.annotation.Replaces;
import io.micronaut.http.HttpHeaderValues;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.token.render.AccessRefreshToken;
import io.micronaut.security.token.render.BearerAccessRefreshToken;
import io.micronaut.security.token.render.BearerTokenRenderer;

@Replaces(bean = BearerTokenRenderer.class)
public class CustomBearerTokenRenderer extends BearerTokenRenderer {

    private final String BEARER_TOKEN_TYPE = HttpHeaderValues.AUTHORIZATION_PREFIX_BEARER;

    @Override
    public AccessRefreshToken render(Authentication authentication, Integer expiresIn, String accessToken, String refreshToken) {
        Object avatar = authentication.getAttributes().get("avatar");
        if (avatar != null) {
            return new CustomBearerAccessRefreshToken(
                    authentication.getName(),
                    authentication.getRoles(),
                    expiresIn,
                    accessToken,
                    refreshToken,
                    BEARER_TOKEN_TYPE,
                    (String) avatar
            );
        }

        return new BearerAccessRefreshToken(
                authentication.getName(),
                authentication.getRoles(),
                expiresIn,
                accessToken,
                refreshToken,
                BEARER_TOKEN_TYPE);
    }
}
