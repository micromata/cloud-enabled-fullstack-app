package com.oskarwiedeweg.cloudwork.auth.sso.provider;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Component
public class GoogleSSOProvider implements SSOProvider {

    private final GoogleIdTokenVerifier googleIdTokenVerifier;

    public GoogleSSOProvider(GoogleIdTokenVerifier googleIdTokenVerifier) {
        this.googleIdTokenVerifier = googleIdTokenVerifier;
    }

    @SneakyThrows
    @Override
    public Map<String, Object> verifyAndGet(String token) {
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
