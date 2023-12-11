package com.oskarwiedeweg.cloudwork.health;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;

public class HealthControllerTest {

    private HealthController underTest;
    private HealthRepository healthRepository;

    @BeforeEach
    public void setUp() {
        healthRepository = Mockito.mock(HealthRepository.class);

        underTest = new HealthController(healthRepository);
    }

    @Test
    @DisplayName("Test Backend Health")
    public void testBackendHealth() {
        // when
        Map<String, Object> response = underTest.backendHealth();

        // then
        Assertions.assertEquals(response, Map.of("status", "UP", "message", "Hello from the backend"));
    }

    @Test
    @DisplayName("Test Database Health")
    public void testDatabaseHealth() {
        // when
        List<HealthEntity> all = List.of(new HealthEntity());
        Mockito.when(healthRepository.findAll()).thenReturn(all);
        Map<String, Object> response = underTest.databaseHealth();

        // then
        Assertions.assertEquals(response, Map.of("status", "UP", "message", "Hello from the database with %s health entities".formatted(all.size())));
    }

}
