package com.oskarwiedeweg.cloudwork.profiles;

import com.oskarwiedeweg.cloudwork.feed.FeedService;
import com.oskarwiedeweg.cloudwork.feed.dto.PostDto;
import com.oskarwiedeweg.cloudwork.user.User;
import com.oskarwiedeweg.cloudwork.user.UserDto;
import com.oskarwiedeweg.cloudwork.user.UserService;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Data
@Service
public class ProfileService {

    private final UserService userService;
    private final FeedService feedService;
    private final ModelMapper modelMapper;

    public ProfileDto loadProfile(Long userId, Long viewerId) {
        User user = userService.getUserDao()
                .findUserById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<PostDto> userPosts = feedService.getUserPosts(userId, userId.equals(viewerId));

        return ProfileDto.builder()
                .userInfo(modelMapper.map(user, UserDto.class))
                .bio(user.getBio())
                .posts(userPosts)
                .build();
    }

}
