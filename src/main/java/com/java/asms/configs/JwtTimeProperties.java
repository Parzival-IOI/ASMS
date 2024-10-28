package com.java.asms.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtTimeProperties (int access, int refresh, int retries) {
}
