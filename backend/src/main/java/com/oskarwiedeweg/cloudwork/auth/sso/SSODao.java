package com.oskarwiedeweg.cloudwork.auth.sso;

import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Data
@Repository
public class SSODao {

    private final JdbcTemplate jdbcTemplate;
    private final SSOConnectionRowMapper ssoConnectionRowMapper;

    public Optional<SSOConnection> findByAccountId(String accountId, String provider) {
        List<SSOConnection> query = jdbcTemplate.query("select users_sso.*, users.name as user_name, users.email as user_email from users_sso left join users on users.id = users_sso.user_id where acc_id = ? and provider = ?", ssoConnectionRowMapper,
                accountId,
                provider
        );
        if (query.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(query.get(0));
    }

    public void createSSOConnection(Long userId, String accountId, String provider) {
        jdbcTemplate.update("insert into users_sso (user_id, acc_id, provider) values (?, ?, ?)", userId, accountId, provider);
    }
}
