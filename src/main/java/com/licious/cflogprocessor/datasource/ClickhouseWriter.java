package com.licious.cflogprocessor.datasource;

import com.licious.cflogprocessor.service.SimpleRecordProcessor;
import org.springframework.core.env.Environment;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.licious.cflogprocessor.formatter.CloudfrontLogEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClickhouseWriter implements Writer {

    private final String clickHouseUrl;
    private final String clickHouseUsername;
    private final String clickHousePassword;
    private static final int BATCH_SIZE = 50000;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final List<CloudfrontLogEntry> buffer = new ArrayList<>();


    public ClickhouseWriter(String user, String password, String url) {
        this.clickHouseUsername = user;
        this.clickHousePassword = password;
        this.clickHouseUrl = url;
    }

    @Override
    public synchronized void write(CloudfrontLogEntry logEntry) throws Exception {
        try {
            buffer.add(logEntry);
            if (buffer.size() >= BATCH_SIZE) {
                writeBatchData();
            }
        } catch (Exception e) {
            throw new Exception("Error occurred while writing log entry");
        }
    }

    private void writeBatchData() {

        try (Connection conn = DriverManager.getConnection(clickHouseUrl, clickHouseUsername, clickHousePassword)) {
            conn.setAutoCommit(false); // Disable auto-commit for batching
            String query = "INSERT INTO distributed_cloudfront_logs VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                int count = 0;
                for (CloudfrontLogEntry data : buffer) {
                    pstmt.setLong(1, convertEpochSeconds(data.getTimestamp()));
                    pstmt.setString(2, data.getC_ip());
                    pstmt.setString(3, data.getTime_to_first_byte());
                    pstmt.setString(4, data.getSc_status());
                    pstmt.setString(5, data.getSc_bytes());
                    pstmt.setString(6, data.getCs_method());
                    pstmt.setString(7, data.getCs_protocol());
                    pstmt.setString(8, data.getCs_host());
                    pstmt.setString(9, data.getCs_uri_stem());
                    pstmt.setString(10, data.getCs_bytes());
                    pstmt.setString(11, data.getX_edge_location());
                    pstmt.setString(12, data.getX_host_header());
                    pstmt.setString(13, data.getCs_protocol_version());
                    pstmt.setString(14, data.getC_ip_version());
                    pstmt.setString(15, data.getCs_user_agent());
                    pstmt.setString(16, data.getCs_referer());
                    pstmt.setString(17, data.getCs_uri_query());
                    pstmt.setString(18, data.getX_edge_response_result_type());
                    pstmt.setString(19, data.getX_forwarded_for());
                    pstmt.setString(20, data.getSsl_protocol());
                    pstmt.setString(21, data.getX_edge_result_type());
                    pstmt.setString(22, data.getSc_content_type());
                    pstmt.setString(23, data.getC_country());
                    pstmt.setString(24, data.getCs_accept_encoding());
                    pstmt.setString(25, data.getCs_accept());
                    pstmt.setString(26, data.getCache_behavior_path_pattern());
                    pstmt.setString(27, data.getPrimary_distribution_id());
                    pstmt.setString(28, data.getAsn());
                    pstmt.addBatch();
                    count++;
                    if (count % BATCH_SIZE == 0) {
                        pstmt.executeBatch();
                    }
                }
                pstmt.executeBatch(); // Execute any remaining batch
                conn.commit(); // Commit the transaction
                buffer.clear();
            }
        } catch (Exception e) {
            // Handle exception
            e.printStackTrace();
        }
    }

    public String serialize(CloudfrontLogEntry logEntry) throws Exception {
        return objectMapper.writeValueAsString(logEntry);
    }

    private Long convertEpochSeconds(String epochString) throws Exception {
        try {
            double epochSeconds = Double.parseDouble(epochString);
            long milliseconds = (long) (epochSeconds * 1000);
            Date date = new Date(milliseconds);
            return date.getTime() / 1000;
        } catch (NumberFormatException e) {
            throw new Exception("Error parsing epoch string: " + e.getMessage());
        }
    }
}
