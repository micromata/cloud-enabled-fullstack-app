package com.oskarwiedeweg.cloudwork.feed.dto;

import com.oskarwiedeweg.cloudwork.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class SinglePostDto {

    private Long id;
    private String title;
    private String preview;
    private String description;
    private String image;
    private LocalDateTime timestamp;
    private UserDto user;

    private List<CommentPostDto> comments;

}
