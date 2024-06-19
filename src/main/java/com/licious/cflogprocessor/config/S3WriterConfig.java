package com.licious.cflogprocessor.config;

import com.licious.cflogprocessor.datasource.S3Writer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "writer.destination.s3", havingValue = "true")
public class S3WriterConfig {

    @Value("${s3.datasource.bucket.name}")
    private String bucketName;

    @Value("${s3.datasource.region}")
    private String region;

    @Bean
    public S3Writer s3Writer() {
        return new S3Writer(bucketName, region);
    }
}
