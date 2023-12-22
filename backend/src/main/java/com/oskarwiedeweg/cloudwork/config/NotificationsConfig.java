package com.oskarwiedeweg.cloudwork.config;

import nl.martijndwars.webpush.PushService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.GeneralSecurityException;

@Configuration
public class NotificationsConfig {

    @Value("${notifications.keys.private}")
    private String privateKey;

    @Value("${notifications.keys.public}")
    private String publicKey;

    @Bean
    public PushService pushService() throws GeneralSecurityException {
        return new PushService(publicKey, privateKey);
    }

}
