package com.oskarwiedeweg.cloudwork.health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/health")
public record HealthController(HealthRepository healthRepository) {

    @GetMapping
    public Map<String, Object> backendHealth() {
        return Map.of("status", "UP", "message", "Hello from the backend");
    }

    @GetMapping("database")
    public Map<String, Object> databaseHealth() {
        List<HealthEntity> all = healthRepository.findAll();
        return Map.of("status", "UP", "message", "Hello from the database with %s health entities".formatted(all.size()));
    }

}
