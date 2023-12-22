package com.oskarwiedeweg.cloudwork.profiles.follower;

import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Data
@Repository
public class FollowerDao {

    private final JdbcTemplate jdbcTemplate;

    public Integer countFollowers(Long userId) {
        return jdbcTemplate.queryForObject("select count(id) as count from user_followers where following_id = ?", (rs, rn) -> rs.getInt("count"), userId);
    }

}
