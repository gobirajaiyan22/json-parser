package com.example.jsonparser.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("parser")
@Getter
@Setter
public class ParserConfig {
    private String combinedField;
    private String field;

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
