package com.oskarwiedeweg.cloudwork.feed.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDto {
    private Long id;
    private String title;
    private String preview;
    private String description;
    private String image;
    private LocalDateTime timestamp;
    private Long authorId;

}
