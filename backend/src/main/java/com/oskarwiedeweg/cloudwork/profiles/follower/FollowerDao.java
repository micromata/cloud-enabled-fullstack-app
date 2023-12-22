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

    public void createFollow(Long userId, Long following) {
        jdbcTemplate.update("insert into user_followers (follower_id, following_id) values (?, ?) " +
                "on conflict (follower_id, following_id) do nothing", userId, following);
    }

    public void deleteFollow(Long userId, Long following) {
        jdbcTemplate.update("delete from user_followers where follower_id = ? and following_id = ?", userId, following);
    }
}
