package com.licious.cflogprocessor.config;

import com.licious.cflogprocessor.datasource.FileCustomWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "writer.destination.file", havingValue = "true")
public class FileCustomWriterConfig {
    @Value("${file.path}")
    private String filePath;

    @Bean
    public FileCustomWriter fileCustomWriter(){
        return new FileCustomWriter(filePath);
    }
}
