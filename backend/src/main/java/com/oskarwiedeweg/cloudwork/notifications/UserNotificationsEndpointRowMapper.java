package com.oskarwiedeweg.cloudwork.notifications;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserNotificationsEndpointRowMapper implements RowMapper<UserNotificationEndpoint> {
    @Override
    public UserNotificationEndpoint mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new UserNotificationEndpoint(
                rs.getLong("id"),
                rs.getString("endpoint"),
                rs.getString("key"),
                rs.getString("auth")
        );
    }

}
