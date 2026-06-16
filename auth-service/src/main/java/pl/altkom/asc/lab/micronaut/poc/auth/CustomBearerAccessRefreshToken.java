package pl.altkom.asc.lab.micronaut.poc.auth;

import io.micronaut.security.token.render.BearerAccessRefreshToken;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;

import java.util.Collection;

@Serdeable
class CustomBearerAccessRefreshToken extends BearerAccessRefreshToken {

    @Getter
    private final String avatar;

    CustomBearerAccessRefreshToken(String username,
                                   Collection<String> roles,
                                   Integer expiresIn,
                                   String accessToken,
                                   String refreshToken,
                                   String tokenType,
                                   String avatar) {
        super(username, roles, expiresIn, accessToken, refreshToken, tokenType);
        this.avatar = avatar;
    }
}
