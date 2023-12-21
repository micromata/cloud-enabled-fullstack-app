package com.oskarwiedeweg.cloudwork.feed.dto;

import com.oskarwiedeweg.cloudwork.user.UserDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SinglePostDto {

    private Long id;
    private String title;
    private String preview;
    private String description;
    private String image;
    private LocalDateTime timestamp;
    private UserDto user;

}
