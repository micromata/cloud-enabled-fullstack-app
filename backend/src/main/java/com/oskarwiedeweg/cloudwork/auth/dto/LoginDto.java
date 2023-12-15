package com.oskarwiedeweg.cloudwork.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {

    @NotBlank(message = "Is empty.")
    private final String username;

    @NotBlank(message = "Is empty.")
    private final String password;

}
