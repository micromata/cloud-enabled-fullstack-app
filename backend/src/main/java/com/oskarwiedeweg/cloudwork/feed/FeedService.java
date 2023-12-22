package com.oskarwiedeweg.cloudwork.feed;

import com.oskarwiedeweg.cloudwork.feed.dto.*;
import com.oskarwiedeweg.cloudwork.feed.post.Comment;
import com.oskarwiedeweg.cloudwork.feed.post.Post;
import com.oskarwiedeweg.cloudwork.feed.post.PostDao;
import com.oskarwiedeweg.cloudwork.user.UserDto;
import lombok.Data;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Service
public class FeedService {

    private final PostDao postDao;
    private final ModelMapper modelMapper;

    public FeedDto getFeed() {
        Map<Long, UserDto> users = new HashMap<>();
        List<PostDto> posts = postDao.getPublicPosts().stream()
                .peek(post -> users.put(post.getUser().getId(), modelMapper.map(post.getUser(), UserDto.class)))
                .map(post -> modelMapper.map(post, PostDto.class))
                .toList();

        return new FeedDto(posts, users);
    }

    public SinglePostDto getFeedById(Long postId) {
        Post post = postDao.findPostById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found!"));

        List<CommentPostDto> comments = postDao.getCommentsToPost(postId)
                .stream()
                .map(c -> modelMapper.map(c, CommentPostDto.class))
                .toList();

        return new SinglePostDto(
                post.getId(),
                post.getTitle(),
                post.getPreview(),
                post.getDescription(),
                post.getImage(),
                post.getTimestamp(),
                modelMapper.map(post.getUser(), UserDto.class),
                comments
        );
    }

    public FeedDto getMyFeeds(Long userId){
        Map<Long, UserDto> users = new HashMap<>();
        List<PostDto> posts = postDao.getUserPosts(userId).stream()
                .peek(post -> users.put(post.getUser().getId(), modelMapper.map(post.getUser(), UserDto.class)))
                .map(post -> modelMapper.map(post, PostDto.class))
                .toList();
        return new FeedDto(posts, users);
    }

    public void createComment(Long userId, Long postId, CreateCommentDto body) {
        postDao.saveCommentToPost(userId,
                postId,
                body.getContent()
        );
    }

    public void createPost(Long userId, CreatePostDto body) {
        postDao.savePost(userId,
                body.getTitle(),
                body.getPreview(),
                body.getDescription(),
                body.getImage()
        );
    }

    public void deletePost(Long postId) {
        if (!postDao.deletePostById(postId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post with id '%s' not found!".formatted(postId));
        }
    }

    public void updatePost(Long postId, CreatePostDto body) {
        if (!postDao.updatePost(postId,
                body.getTitle(),
                body.getPreview(),
                body.getDescription(),
                body.getImage())
        ) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post with id '%s' not found!".formatted(postId));
        }
    }

    public void changePostsState(Long postId) {
        if (postDao.getPostState(postId).equals(Post.PUBLIC_STATE)) {
            postDao.updatePostState(postId, Post.DRAFT_STATE);
        }
        else if(postDao.getPostState(postId).equals(Post.DRAFT_STATE)) {
            postDao.updatePostState(postId, Post.PUBLIC_STATE);
        }
    }
}
