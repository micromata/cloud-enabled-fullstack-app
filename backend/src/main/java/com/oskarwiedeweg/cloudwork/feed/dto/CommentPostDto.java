package com.oskarwiedeweg.cloudwork.feed.dto;

import com.oskarwiedeweg.cloudwork.user.UserDto;

import java.time.LocalDateTime;

public class CommentPostDto {
    private Long id;
    private UserDto author;
    private String content;
    private LocalDateTime timestamp;
}
