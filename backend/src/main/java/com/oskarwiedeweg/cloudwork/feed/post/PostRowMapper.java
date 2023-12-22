package com.oskarwiedeweg.cloudwork.feed.post;

import com.oskarwiedeweg.cloudwork.user.User;
import com.oskarwiedeweg.cloudwork.user.UserRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

@Component
public class PostRowMapper implements RowMapper<Post> {

    private final RowMapper<User> userPrefixedRowMapper = new UserRowMapper(true);

    @Override
    public Post mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Timestamp publishedAt = resultSet.getTimestamp("published_at");

        return new Post(
                resultSet.getLong("id"),
                resultSet.getString("title"),
                resultSet.getString("preview"),
                resultSet.getString("description"),
                resultSet.getString("image"),
                resultSet.getString("state"),
                publishedAt.toLocalDateTime(),
                userPrefixedRowMapper.mapRow(resultSet, rowNum)
        );
    }

}
