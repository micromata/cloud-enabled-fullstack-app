package com.oskarwiedeweg.cloudwork.auth.dto;

import lombok.Data;

@Data
public class SSOConnectionDto {

    private Long id;
    private String provider;
    private String email;

}
