package com.oskarwiedeweg.cloudwork.profiles;

import com.oskarwiedeweg.cloudwork.feed.dto.PostDto;
import com.oskarwiedeweg.cloudwork.user.UserDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProfileDto {

    private UserDto userInfo;
    private String bio;
    private List<PostDto> posts;

}
