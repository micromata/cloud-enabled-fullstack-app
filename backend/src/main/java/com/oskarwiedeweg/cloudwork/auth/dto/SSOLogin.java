package com.oskarwiedeweg.cloudwork.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SSOLogin {

    @NotBlank
    private String idToken;

}
