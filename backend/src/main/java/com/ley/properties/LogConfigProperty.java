package com.ley.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "logviewer")
public class LogConfigProperty {
    private String linePattern;
}
