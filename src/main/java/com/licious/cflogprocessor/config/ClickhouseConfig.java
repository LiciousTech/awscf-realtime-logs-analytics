package com.licious.cflogprocessor.config;

import com.licious.cflogprocessor.datasource.ClickhouseWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "writer.destination", havingValue = "CLICKHOUSE")
//@ConfigurationProperties(prefix = "clickhouse.datasource")
public class ClickhouseConfig {

    @Value("${clickhouse.datasource.user}")
    private String user;

    @Value("${clickhouse.datasource.password}")
    private String password;

    @Value("${clickhouse.datasource.url}")
    private String url;

    @Bean
    public ClickhouseWriter clickhouseWriter() {
        return new ClickhouseWriter(user, password, url);
    }
}
