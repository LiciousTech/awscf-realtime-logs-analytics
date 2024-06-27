package com.licious.cflogprocessor.service;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.InitialPositionInStream;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.Worker;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Value;

@Service
public class KinesisConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(KinesisConsumerService.class);

    @Value("${aws.region}")
    private String awsRegion;

    @Value("${kinesis.streamName}")
    private String streamName;

    @Value("${kinesis.applicationName}")
    private String applicationName;

    @Autowired
    private SimpleRecordProcessor recordProcessorFactory;

    @PostConstruct
    public void init() {
        KinesisClientLibConfiguration kinesisClientLibConfiguration =
                new KinesisClientLibConfiguration(
                        applicationName,
                        streamName,
                        new DefaultAWSCredentialsProviderChain(),
                        java.util.UUID.randomUUID().toString()
                )
                        .withRegionName(awsRegion)
                        .withInitialPositionInStream(InitialPositionInStream.LATEST);

        AmazonKinesisClientBuilder clientBuilder = AmazonKinesisClientBuilder.standard()
                .withCredentials(new DefaultAWSCredentialsProviderChain());
        clientBuilder.setRegion(awsRegion);


        logger.debug("Worker Starting:");
        Worker worker = new Worker.Builder()
                .recordProcessorFactory(() -> recordProcessorFactory)
                .config(kinesisClientLibConfiguration)
                .kinesisClient(clientBuilder.build())
                .build();

        Thread workerThread = new Thread(worker);
        workerThread.start();
    }


}

