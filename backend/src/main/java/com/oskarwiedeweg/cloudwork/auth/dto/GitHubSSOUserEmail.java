package com.oskarwiedeweg.cloudwork.auth.dto;

import lombok.Data;

@Data
public class GitHubSSOUserEmail {

    private final String email;
    private final boolean primary;

}
