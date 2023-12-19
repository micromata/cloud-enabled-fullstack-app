package com.oskarwiedeweg.cloudwork.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GitHubSSOAccessTokenResponse {

    private final String error;

    @JsonProperty("access_token")
    private final String accessToken;

}
