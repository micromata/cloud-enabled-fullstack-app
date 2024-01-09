package com.oskarwiedeweg.cloudwork.feed.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateCommentDto {

    @NotEmpty(message = "Is empty.")
    private String content;

}
