package com.oskarwiedeweg.cloudwork.notifications.dto;

import lombok.Data;

@Data
public class EnableNotificationsDto {

    private final String endpoint;
    private final String key;
    private final String auth;

}
