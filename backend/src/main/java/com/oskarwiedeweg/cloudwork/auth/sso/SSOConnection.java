package com.oskarwiedeweg.cloudwork.auth.sso;

import com.oskarwiedeweg.cloudwork.user.User;
import lombok.Data;

@Data
public class SSOConnection {

    private final Long id;
    private final String accountId;
    private final String provider;
    private final User user;

}
