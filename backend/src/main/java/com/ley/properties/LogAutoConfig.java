package com.ley.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(LogConfigProperty.class)
@ConditionalOnClass(LogConfigBean.class)
public class LogAutoConfig {
    @Autowired
    private LogConfigProperty logConfigProperties;

    @Bean
    @ConditionalOnMissingBean(LogConfigBean.class)
    public LogConfigBean logConfigBean() {
        LogConfigBean logConfigBean = new LogConfigBean();
        logConfigBean.setLinePattern(logConfigProperties.getLinePattern());
        return logConfigBean;
    }
}
