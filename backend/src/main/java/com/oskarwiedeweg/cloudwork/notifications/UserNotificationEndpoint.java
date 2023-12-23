package com.oskarwiedeweg.cloudwork.notifications;

import lombok.Data;

@Data
public class UserNotificationEndpoint {

    private final Long id;
    private final String endpoint;
    private final String key;
    private final String auth;

}
