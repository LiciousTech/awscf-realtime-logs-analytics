package com.licious.cflogprocessor.datasource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.licious.cflogprocessor.formatter.CloudfrontLogEntry;
import org.springframework.scheduling.annotation.Scheduled;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class S3Writer implements Writer {

    private final S3Client s3Client;
    private final String bucketName;
    private final ObjectMapper objectMapper;
    private final List<CloudfrontLogEntry> buffer;

    public S3Writer(String bucketName, String region) {
        this.s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
        this.bucketName = bucketName;
        this.buffer = new CopyOnWriteArrayList<>();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void write(CloudfrontLogEntry cloudfrontLogEntry) throws Exception {
        buffer.add(cloudfrontLogEntry);
    }

    @Scheduled(fixedRate = 60000)
    public void flush() {
        if (buffer.isEmpty()) {
            return;
        }

        try {
            List<CloudfrontLogEntry> entriesToWrite = new ArrayList<>(buffer);
            buffer.clear();

            StringBuilder sb = new StringBuilder();
            for (CloudfrontLogEntry entry : entriesToWrite) {
                sb.append(objectMapper.writeValueAsString(entry)).append("\n");
            }

            String logEntries = sb.toString();
            InputStream inputStream = new ByteArrayInputStream(logEntries.getBytes(StandardCharsets.UTF_8));
            String objectKey = "logs/" + Instant.now().toString() + ".log"; // Generate a unique key for S3

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build();

            s3Client.putObject(putObjectRequest, software.amazon.awssdk.core.sync.RequestBody.fromInputStream(inputStream, logEntries.length()));
        } catch (S3Exception | IOException e) {
            e.printStackTrace();
        }
    }
}
