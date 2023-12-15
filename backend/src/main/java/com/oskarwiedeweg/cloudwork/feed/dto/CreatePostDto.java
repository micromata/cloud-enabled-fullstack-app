package com.oskarwiedeweg.cloudwork.feed.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreatePostDto {

    @NotBlank(message = "Is empty.")
    @Pattern(regexp = "([A-Za-z0-9-._ ])", message = "Other character than A-Z, a-z, 0-9, -, ., or _")
    @Size(min = 5, message = "Less than 5 characters.")
    @Size(min = 30, message = "More than 30 characters.")
    private final String title;

    @NotBlank(message = "Is empty.")
    @Pattern(regexp = "([A-Za-z0-9-._ ])", message = "Other character than A-Z, a-z, 0-9, -, ., or _")
    @Size(min = 5000, message = "More than 5000 characters.")
    private final String description;

}
