package com.licious.cflogprocessor.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.yml")
@Getter
public class WriterProperties {

    @Value("${writer.destination}")
    private WriterDestination logDestination;

    public enum WriterDestination {
        CLICKHOUSE,
        ELASTICSEARCH,
        STDOUT
    }





}
