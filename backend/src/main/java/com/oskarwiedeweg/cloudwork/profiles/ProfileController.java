package com.oskarwiedeweg.cloudwork.profiles;

import lombok.Data;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Data
@RestController
@RequestMapping("/v1/profile")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/{userId}")
    public ProfileDto getUserProfile(@PathVariable Long userId, @AuthenticationPrincipal Long viewerId) {
        return profileService.loadProfile(userId, viewerId);
    }

}
