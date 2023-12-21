package com.oskarwiedeweg.cloudwork.notifications;

import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Data
@Repository
public class NotificationsDao {

    private final JdbcTemplate jdbcTemplate;
    private final UserNotificationsEndpointRowMapper userNotificationsEndpointRowMapper;

    public Optional<UserNotificationEndpoint> getUserConnection(Long userId) {
        List<UserNotificationEndpoint> query = jdbcTemplate.query("select * from user_notification_endpoints " +
                "where user_id = ?",
                userNotificationsEndpointRowMapper,
                userId
        );
        if (query.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(query.get(0));
    }

    public void saveUserConnection(Long userId, String endpoint, String key, String auth) {
        jdbcTemplate.update("insert into user_notification_endpoints (user_id, endpoint, key, auth) " +
                "values (?, ?, ?, ?) " +
                "on conflict (user_id) do update " +
                "set endpoint = ?, key = ?, auth = ?",
                userId,
                endpoint, key, auth,
                endpoint, key, auth);
    }

    public void deleteUserConnection(Long userId) {
        jdbcTemplate.update("delete from user_notification_endpoints where user_id = ?", userId);
    }
}
