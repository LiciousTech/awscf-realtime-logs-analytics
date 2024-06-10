package com.licious.cflogprocessor.datasource;

import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.xcontent.XContentType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.licious.cflogprocessor.formatter.CloudfrontLogEntry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElasticsearchWriter {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchWriter.class);
    private final String host;
    private final int port;
    private final String scheme;

    public ElasticsearchWriter(String host, int port, String scheme) {
            this.host = host;
            this.port = port;
            this.scheme = scheme;
    }

    public void write(CloudfrontLogEntry logEntry) throws Exception {

        // Serialize LogEntry to JSON
        String json = serialize(logEntry);

        // Setup Elasticsearch client - http://10.1.3.216:9200
        try (RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost(host, port, scheme)))) {
            
            IndexRequest request = new IndexRequest("cloudfrontlogs");
            request.source(json, XContentType.JSON);

            // Send the document to Elasticsearch
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);

        } catch (Exception e) {

            logger.error(e.getMessage(), e);

        }
    }

    public String serialize(CloudfrontLogEntry logEntry) throws Exception {
        return objectMapper.writeValueAsString(logEntry);
    }
}

