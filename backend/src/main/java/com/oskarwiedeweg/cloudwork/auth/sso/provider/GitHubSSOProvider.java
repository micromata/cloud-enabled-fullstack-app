package com.oskarwiedeweg.cloudwork.auth.sso.provider;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oskarwiedeweg.cloudwork.auth.dto.GitHubSSOAccessTokenResponse;
import com.oskarwiedeweg.cloudwork.auth.dto.GitHubSSOUserEmail;
import com.oskarwiedeweg.cloudwork.auth.dto.GitHubSSOUserInfo;
import com.oskarwiedeweg.cloudwork.auth.sso.SSODao;
import com.oskarwiedeweg.cloudwork.user.UserService;
import dev.samstevens.totp.secret.SecretGenerator;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Component
public class GitHubSSOProvider extends AbstractSSOProvider{

    @Value("${sso.github.clientId}")
    private String clientId;

    @Value("${sso.github.clientSecret}")
    private String clientSecret;

    private final RestTemplate restTemplate;

    protected GitHubSSOProvider(SSODao ssoDao, UserService userService, SecretGenerator secretGenerator, RestTemplate restTemplate) {
        super(ssoDao, userService, secretGenerator);
        this.restTemplate = restTemplate;
    }

    @Override
    public Map<String, Object> verifyAndGet(String token) {
        String accessToken = getAccessToken(token);
        String userId = getUserId(accessToken);
        String email = getUserEmail(accessToken);

        return Map.of("id", userId, "email", email);
    }

    private String getAccessToken(String code) {
        GitHubSSOAccessTokenResponse gitHubSSOAccessTokenResponse = restTemplate.postForObject(
                "https://github.com/login/oauth/access_token",
                Map.of(
                        "client_id", clientId,
                        "client_secret", clientSecret,
                        "code", code
                ),
                GitHubSSOAccessTokenResponse.class
        );

        if (gitHubSSOAccessTokenResponse == null || gitHubSSOAccessTokenResponse.getError() != null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token.");
        }

        return gitHubSSOAccessTokenResponse.getAccessToken();
    }

    @SneakyThrows
    private String getUserId(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(accessToken));

        ResponseEntity<String> userInfoResponseEntity = restTemplate.exchange(
                "https://api.github.com/user",
                HttpMethod.GET,
                new HttpEntity<>(Map.of("access_token", accessToken), httpHeaders),
                String.class);

        if (!userInfoResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
            System.out.println(userInfoResponseEntity.getBody());
        }

        GitHubSSOUserInfo gitHubSSOUserInfo = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .readValue(userInfoResponseEntity.getBody(), GitHubSSOUserInfo.class);

        return gitHubSSOUserInfo.getId();
    }

    private String getUserEmail(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(accessToken));

        ResponseEntity<List<GitHubSSOUserEmail>> emailsResponseEntity = restTemplate.exchange(
                "https://api.github.com/user/emails",
                HttpMethod.GET,
                new HttpEntity<>(Map.of("access_token", accessToken), httpHeaders),
                new ParameterizedTypeReference<List<GitHubSSOUserEmail>>() {
                });

        return emailsResponseEntity.getBody().stream()
                .filter(GitHubSSOUserEmail::isPrimary)
                .map(GitHubSSOUserEmail::getEmail)
                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Has no primary email!"));
    }

    @Override
    public String getProviderName() {
        return "github";
    }
}
