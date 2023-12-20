package com.oskarwiedeweg.cloudwork.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.oskarwiedeweg.cloudwork.auth.sso.provider.SSOProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class SSOConfig {

    @Bean
    public Map<String, SSOProvider> ssoProviders(Map<String, SSOProvider> ssoProvidersInContext) {
        Map<String, SSOProvider> ssoProviders = new HashMap<>();
        for (SSOProvider value : ssoProvidersInContext.values()) {
            ssoProviders.put(value.getProviderName(), value);
        }
        return ssoProviders;
    }

    @Bean
    public GoogleIdTokenVerifier googleIdTokenVerifier(HttpTransport httpTransport, @Value("${sso.google.clientId}") String googleClientId) {
        return new GoogleIdTokenVerifier.Builder(httpTransport, GsonFactory.getDefaultInstance())
                .setAudience(List.of(googleClientId))
                .build();
    }

    @Bean
    public HttpTransport httpTransport() throws GeneralSecurityException, IOException {
        return GoogleNetHttpTransport.newTrustedTransport();
    }

}
