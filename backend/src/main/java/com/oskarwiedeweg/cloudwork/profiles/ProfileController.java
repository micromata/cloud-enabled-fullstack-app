package com.oskarwiedeweg.cloudwork.profiles;

import com.oskarwiedeweg.cloudwork.profiles.follower.FollowerService;
import lombok.Data;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Data
@RestController
@RequestMapping("/v1/profile")
public class ProfileController {

    private final ProfileService profileService;
    private final FollowerService followerService;

    @GetMapping("/{userId}")
    public ProfileDto getUserProfile(@PathVariable Long userId, @AuthenticationPrincipal Long viewerId) {
        return profileService.loadProfile(userId, viewerId);
    }

    @PostMapping("/{userId}/follow")
    @PreAuthorize("isAuthenticated()")
    public void followUser(@PathVariable Long userId, @AuthenticationPrincipal Long follower) {
        followerService.follow(follower, userId);
    }

    @DeleteMapping("/{userId}/follow")
    @PreAuthorize("isAuthenticated()")
    public void unfollowUser(@PathVariable Long userId, @AuthenticationPrincipal Long follower) {
        followerService.unfollow(follower, userId);
    }

}
