package com.oskarwiedeweg.cloudwork.feed.post;

import com.oskarwiedeweg.cloudwork.user.UserRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CommentRowMapper implements RowMapper<Comment> {

    private final UserRowMapper userRowMapper = new UserRowMapper(true);

    @Override
    public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Comment(
            rs.getLong("id"),
                userRowMapper.mapRow(rs, rowNum),
                rs.getLong("post_id"),
                rs.getString("content"),
                rs.getTimestamp("published_at").toLocalDateTime()
        );
    }

}
