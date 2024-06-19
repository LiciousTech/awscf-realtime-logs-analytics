package com.licious.cflogprocessor.config;

import com.licious.cflogprocessor.datasource.ElasticsearchWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "writer.destination.elasticsearch", havingValue = "true")
public class ElasticsearchConfig {

    @Value("${elasticsearch.datasource.host}")
    private String host;

    @Value("${elasticsearch.datasource.port}")
    private int port;

    @Value("${elasticsearch.datasource.scheme}")
    private String scheme;

    @Bean
    public ElasticsearchWriter elasticsearchWriter() {
        return new ElasticsearchWriter(host, port, scheme);
    }
}

