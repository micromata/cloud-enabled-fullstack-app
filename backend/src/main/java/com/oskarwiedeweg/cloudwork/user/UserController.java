package com.oskarwiedeweg.cloudwork.user;

import com.oskarwiedeweg.cloudwork.auth.dto.SSOLogin;
import com.oskarwiedeweg.cloudwork.auth.sso.SSOService;
import com.oskarwiedeweg.cloudwork.user.dto.SettingsDto;
import com.oskarwiedeweg.cloudwork.user.dto.Setup2FADto;
import lombok.Data;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Data
@RestController
@RequestMapping("/v1/user")
public class UserController {

    private final UserService userService;
    private final SSOService ssoService;

    @GetMapping("/2fa/setup")
    @PreAuthorize("isAuthenticated()")
    public Setup2FADto setup2FA(@AuthenticationPrincipal Long userId) {
        return userService.setup2FA(userId);
    }

    @PostMapping("/2fa/verify/{code}")
    @PreAuthorize("isAuthenticated()")
    public Map<String, Boolean> verify2FA(@AuthenticationPrincipal Long userId, @PathVariable("code") Long code, @RequestParam("tempToken") String tempToken) {
        return Map.of("valid", userService.setup2FA(userId, code, tempToken));
    }

    @PostMapping("/sso/new/{provider}")
    @PreAuthorize("isAuthenticated()")
    public void addProvider(@PathVariable("provider") String provider, @AuthenticationPrincipal Long userId, @RequestBody SSOLogin ssoLogin) {
        ssoService.addSSOProvider(userId, provider, ssoLogin);
    }

    @GetMapping("/settings")
    @PreAuthorize("isAuthenticated()")
    public SettingsDto getSettings(@AuthenticationPrincipal Long userId){
        return userService.getSettings(userId);
    }

}
