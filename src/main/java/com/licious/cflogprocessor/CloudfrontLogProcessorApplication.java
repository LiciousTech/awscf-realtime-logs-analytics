package com.licious.cflogprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CloudfrontLogProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudfrontLogProcessorApplication.class, args);
	}

}
