package com.licious.cflogprocessor.config;

import com.licious.cflogprocessor.datasource.StdoutWriter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "writer.destination.stdout", havingValue = "true")
public class StdoutConfig {
    @Bean
    public StdoutWriter stdoutWriter() {
        return new StdoutWriter();
    }
}
