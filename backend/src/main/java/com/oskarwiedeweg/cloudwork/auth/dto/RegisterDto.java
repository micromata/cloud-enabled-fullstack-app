package com.oskarwiedeweg.cloudwork.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterDto {

    @NotBlank(message = "Is empty.")
    @Pattern(regexp = "([A-Za-z0-9-._])*", message = "Other character than A-Z, a-z, 0-9, -, ., or _")
    @Size(min = 5, message = "Less characters than 5.")
    private final String username;

    @Email(message = "Invalid email.")
    @NotBlank(message = "Is empty.")
    private final String email;

    @NotBlank(message = "Is empty.")
    @Pattern.List(value = {
            @Pattern(regexp = ".*([A-Z]).*", message = "No capital letter."),
            @Pattern(regexp = ".*([a-z]).*", message = "No lowercase letter."),
            @Pattern(regexp = ".*(\\d).*", message = "No digit.")
    })
    @Size(min = 8, message = "Less thant 8 characters.")
    private final String password;

}
