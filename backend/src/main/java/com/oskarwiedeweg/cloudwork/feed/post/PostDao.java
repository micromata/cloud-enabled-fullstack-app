package com.oskarwiedeweg.cloudwork.feed.post;

import lombok.Data;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Data
@Repository
public class PostDao {
    private final JdbcTemplate jdbcTemplate;
    private final PostRowMapper rowMapper;

    public List<Post> getPublicPosts() {
        return jdbcTemplate.query("select posts.*, users.name as user_name from posts inner join users on posts.user_id = users.id where posts.state = 'public' order by posts.published_at desc", rowMapper);
    }

    public void savePost(Long userId, String title, String preview, String description, String image) {
        jdbcTemplate.update("insert into posts (user_id, title, preview, description, image, published_at) values (?, ?, ?, ?, ?, ?)", userId, title, preview, description, image, Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC())));
    }

    public Optional<Post> findPostById(Long postId) {
        try {
            Post post = jdbcTemplate.queryForObject("select posts.*, users.name as user_name from posts inner join users on posts.user_id = users.id where posts.id = ?", rowMapper, postId);
            return Optional.ofNullable(post);
        } catch (IncorrectResultSizeDataAccessException ok) {
            return Optional.empty();
        }
    }

    public boolean deletePostById(Long postId) {
        return jdbcTemplate.update("delete from posts where id = ?", postId) == 1;
    }

    public boolean updatePost(Long postId, String title, String preview, String description, String image) {
        return jdbcTemplate.update("update posts set title = ?, preview = ?, description = ?, image = ? where id = ?",
                title,
                preview,
                description,
                image,
                postId
        ) == 1;
    }
}
