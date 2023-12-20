package com.oskarwiedeweg.cloudwork.feed.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreatePostDto {

    @Pattern(regexp = "([A-Za-z0-9-._ ])*", message = "Other character than A-Z, a-z, 0-9, -, ., or _")
    @Size(min = 5, message = "Less than 5 characters.")
    @Size(max = 30, message = "More than 30 characters.")
    private final String title;

    @Pattern(regexp = "([A-Za-z0-9-._ ])*", message = "Other character than A-Z, a-z, 0-9, -, ., or _")
    @Size(max = 300, message = "More than 300 characters.")
    private final String preview;

    @Pattern(regexp = "([A-Za-z0-9-._ ])*", message = "Other character than A-Z, a-z, 0-9, -, ., or _")
    @Size(max = 5000, message = "More than 5000 characters.")
    private final String description;

    private final String image;

}
