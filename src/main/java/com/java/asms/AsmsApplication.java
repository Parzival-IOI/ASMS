package com.java.asms;

import com.java.asms.configs.JwtTimeProperties;
import com.java.asms.configs.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableConfigurationProperties({RsaKeyProperties.class, JwtTimeProperties.class})
@EnableJpaAuditing
public class AsmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsmsApplication.class, args);
    }

}
