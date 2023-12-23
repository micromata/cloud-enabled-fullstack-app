package com.oskarwiedeweg.cloudwork.notifications;

import com.oskarwiedeweg.cloudwork.notifications.dto.EnableNotificationsDto;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Data
@RestController
@RequestMapping("/v1/notifications")
public class NotificationsController {

    @Value("${notifications.keys.public}")
    private String publicKey;

    private final NotificationsService notificationsService;

    @GetMapping("/key")
    public Map<String, String> key() {
        return Map.of("public", publicKey);
    }

    @PostMapping("/subscribe")
    @PreAuthorize("isAuthenticated()")
    public void subscribe(@AuthenticationPrincipal Long userId, @RequestBody EnableNotificationsDto body) {
        notificationsService.subscribe(userId, body);
    }

    @DeleteMapping("/unsubscribe")
    @PreAuthorize("isAuthenticated()")
    public void unsubscribe(@AuthenticationPrincipal Long userId) {
        notificationsService.unsubscribe(userId);
    }

}
