package com.java.asms.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "dev")
public record DevProperties(boolean debug) {
}
