package com.oskarwiedeweg.cloudwork.feed;

import com.oskarwiedeweg.cloudwork.feed.dto.CreatePostDto;
import com.oskarwiedeweg.cloudwork.feed.dto.FeedDto;
import com.oskarwiedeweg.cloudwork.feed.dto.PostDto;
import com.oskarwiedeweg.cloudwork.feed.post.Post;
import com.oskarwiedeweg.cloudwork.feed.post.PostDao;
import com.oskarwiedeweg.cloudwork.user.User;
import com.oskarwiedeweg.cloudwork.user.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FeedServiceTest {

    private PostDao postDao;
    private ModelMapper modelMapper;

    private FeedService underTest;

    @BeforeEach
    public void setUp() {
        postDao = mock(PostDao.class);
        modelMapper = new ModelMapper();
        underTest = new FeedService(postDao, modelMapper);
    }

    @Test
    public void testGetFeed() {
        //given
        User testUser1 = User.builder()
                .id(1L)
                .build();
        User testUser2 = User.builder()
                .id(2L)
                .build();

        Post post1 = new Post(1L, "Test", "Wow", "Test", "Image", LocalDateTime.now(), testUser1);
        Post post2 = new Post(2L, "Tes1", "Wow", "Test", "Image", LocalDateTime.now(), testUser1);
        Post post3 = new Post(3L, "Tes2", "Wow", "Test", "Image", LocalDateTime.now(), testUser2);

        List<Post> posts = List.of(post1, post2, post3);

        //when
        when(postDao.getPublicPosts()).thenReturn(posts);

        //then
        FeedDto feed = underTest.getFeed();

        assertEquals(3, feed.getPosts().size());
        assertEquals(2, feed.getAuthors().size());

        assertTrue(feed.getPosts().contains(modelMapper.map(post1, PostDto.class)));
        assertTrue(feed.getPosts().contains(modelMapper.map(post2, PostDto.class)));
        assertTrue(feed.getPosts().contains(modelMapper.map(post3, PostDto.class)));

        assertTrue(feed.getAuthors().containsKey(testUser1.getId()));
        assertTrue(feed.getAuthors().containsKey(testUser2.getId()));

        assertEquals(modelMapper.map(testUser1, UserDto.class), feed.getAuthors().get(testUser1.getId()));
        assertEquals(modelMapper.map(testUser2, UserDto.class), feed.getAuthors().get(testUser2.getId()));

        verify(postDao).getPublicPosts();
    }

    @Test
    public void testCreatePost() {
        //given
        CreatePostDto createPostDto = new CreatePostDto("title", "preview", "description", "image");
        Long userId = new Random().nextLong();

        //then
        underTest.createPost(userId, createPostDto);

        verify(postDao).savePost(userId, createPostDto.getTitle(), createPostDto.getPreview(), createPostDto.getDescription(), createPostDto.getImage());
    }

    @Test
    public void testSuccessfulUpdatePost() {
        //given
        CreatePostDto createPostDto = new CreatePostDto("title", "preview", "description", "image");
        Long postId = new Random().nextLong();

        //when
        when(postDao.updatePost(postId, createPostDto.getTitle(), createPostDto.getPreview(), createPostDto.getDescription(), createPostDto.getImage())).thenReturn(true);

        //then
        underTest.updatePost(postId, createPostDto);

        verify(postDao).updatePost(postId, createPostDto.getTitle(), createPostDto.getPreview(), createPostDto.getDescription(), createPostDto.getImage());
    }

    @Test
    public void testInvalidUpdatePost() {
        //given
        CreatePostDto createPostDto = new CreatePostDto("title", "preview", "description", "image");
        Long postId = new Random().nextLong();

        //when
        when(postDao.updatePost(postId, createPostDto.getTitle(), createPostDto.getPreview(), createPostDto.getDescription(), createPostDto.getImage())).thenReturn(false);

        //then
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> underTest.updatePost(postId, createPostDto));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

        verify(postDao).updatePost(postId, createPostDto.getTitle(), createPostDto.getPreview(), createPostDto.getDescription(), createPostDto.getImage());
    }

    @Test
    public void testSuccessfulDeletePost() {
        //given
        Long postId = new Random().nextLong();

        //when
        when(postDao.deletePostById(postId)).thenReturn(true);

        //then
        underTest.deletePost(postId);

        verify(postDao).deletePostById(postId);
    }

    @Test
    public void testInvalidDeletePost() {
        //given
        Long postId = new Random().nextLong();

        //when
        when(postDao.deletePostById(postId)).thenReturn(false);

        //then
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> underTest.deletePost(postId));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

        verify(postDao).deletePostById(postId);
    }

}