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
    private final CommentRowMapper commentRowMapper;

    public List<Post> getPublicPosts() {
        return jdbcTemplate.query("select posts.*, users.name as user_name from posts " +
                "inner join users on posts.user_id = users.id " +
                "where posts.state = 'public' " +
                "order by posts.published_at desc", rowMapper);
    }

    public List<Post> getUserPosts(Long userId) {
        return jdbcTemplate.query("select posts.*, users.name as user_name from posts " +
                "inner join users on posts.user_id = users.id " +
                "where users.id = ? " +
                "order by posts.published_at desc",
                rowMapper, userId);
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

    public boolean updatePostState(Long postId, String state) {
        return jdbcTemplate.update("update posts set state = ? where id = ?", state, postId) == 1;
    }

    public String getPostState(Long postId) {
        return jdbcTemplate.queryForObject("select posts.state from posts where posts.id = ?", (rs, rn) -> rs.getString("state"), postId);
    }

    public void saveCommentToPost(Long userId, Long postId, String content) {
        jdbcTemplate.update("insert into post_comments(user_id, post_id, content, published_at) values (?, ?, ?, ?)", userId, postId, content, Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC())));
    }

    public List<Comment> getCommentsToPost(Long postId) {
        return jdbcTemplate.query("select post_comments.*, users.name as user_name from post_comments " +
                "left join users on users.id = post_comments.user_id " +
                "where post_comments.post_id = ?", commentRowMapper, postId);
    }
}
