package com.oskarwiedeweg.cloudwork.auth.sso.provider;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.oskarwiedeweg.cloudwork.auth.sso.SSODao;
import com.oskarwiedeweg.cloudwork.user.UserService;
import dev.samstevens.totp.secret.SecretGenerator;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Component
public class GoogleSSOProvider extends AbstractSSOProvider {

    private final GoogleIdTokenVerifier googleIdTokenVerifier;

    public GoogleSSOProvider(SSODao ssoDao, UserService userService, SecretGenerator secretGenerator, GoogleIdTokenVerifier googleIdTokenVerifier) {
        super(ssoDao, userService, secretGenerator);
        this.googleIdTokenVerifier = googleIdTokenVerifier;
    }

    @SneakyThrows
    @Override
    protected Map<String, Object> verifyAndGet(String token) {
        GoogleIdToken idToken = googleIdTokenVerifier.verify(token);

        if (idToken == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid id token");
        }

        GoogleIdToken.Payload payload = idToken.getPayload();
        String accId = payload.getSubject();
        String email = payload.getEmail();

        return Map.of("id", accId, "email", email);
    }

    @Override
    public String getProviderName() {
        return "google";
    }
}
