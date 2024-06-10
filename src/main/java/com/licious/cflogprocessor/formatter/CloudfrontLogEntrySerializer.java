package com.licious.cflogprocessor.formatter;

import java.util.Map;
import java.util.HashMap;

public class CloudfrontLogEntrySerializer {

    public static CloudfrontLogEntry parseRecord(String recordData) {
        String[] parts = recordData.split("\\t");

        CloudfrontLogEntry logEntry = new CloudfrontLogEntry();

        logEntry.setTimestamp(parts[0]);
        logEntry.setC_ip(parts[1]);
        logEntry.setTime_to_first_byte(parts[2]);
        logEntry.setSc_status(parts[3]);
        logEntry.setSc_bytes(parts[4]);
        logEntry.setCs_method(parts[5]);
        logEntry.setCs_protocol(parts[6]);
        logEntry.setCs_host(parts[7]);
        logEntry.setCs_uri_stem(parts[8]);
        logEntry.setCs_bytes(parts[9]);
        logEntry.setX_edge_location(parts[10]);
        logEntry.setX_host_header(parts[11]);
        logEntry.setCs_protocol_version(parts[12]);
        logEntry.setC_ip_version(parts[13]);
        logEntry.setCs_user_agent(parts[14]);
        logEntry.setCs_referer(parts[15]);
        logEntry.setCs_uri_query(parts[16]);
        logEntry.setX_edge_response_result_type(parts[17]);
        logEntry.setX_forwarded_for(parts[18]);
        logEntry.setSsl_protocol(parts[19]);
        logEntry.setX_edge_result_type(parts[20]);
        logEntry.setSc_content_type(parts[21]);
        logEntry.setC_country(parts[22]);
        logEntry.setCs_accept_encoding(parts[23]);
        logEntry.setCs_accept(parts[24]);
        logEntry.setCache_behavior_path_pattern(parts[25]);
        logEntry.setPrimary_distribution_id(parts[26]);
        logEntry.setAsn(parts[27]);

        return logEntry;
    }

    public static Map<String, String> toMap(CloudfrontLogEntry logEntry) {
        Map<String, String> dataMap = new HashMap<>();

        dataMap.put("timestamp", logEntry.getTimestamp());
        dataMap.put("c_ip", logEntry.getC_ip());
        dataMap.put("time_to_first_byte", logEntry.getTime_to_first_byte());
        dataMap.put("sc_status", logEntry.getSc_status());
        dataMap.put("sc_bytes", logEntry.getSc_bytes());
        dataMap.put("cs_method", logEntry.getCs_method());
        dataMap.put("cs_protocol", logEntry.getCs_protocol());
        dataMap.put("cs_host", logEntry.getCs_host());
        dataMap.put("cs_uri_stem", logEntry.getCs_uri_stem());
        dataMap.put("cs_bytes", logEntry.getCs_bytes());
        dataMap.put("x_edge_location", logEntry.getX_edge_location());
        dataMap.put("x_host_header", logEntry.getX_host_header());
        dataMap.put("cs_protocol_version", logEntry.getCs_protocol_version());
        dataMap.put("c_ip_version", logEntry.getC_ip_version());
        dataMap.put("cs_user_agent", logEntry.getCs_user_agent());
        dataMap.put("cs_referer", logEntry.getCs_referer());
        dataMap.put("cs_uri_query", logEntry.getCs_uri_query());
        dataMap.put("x_edge_response_result_type", logEntry.getX_edge_response_result_type());
        dataMap.put("x_forwarded_for", logEntry.getX_forwarded_for());
        dataMap.put("ssl_protocol", logEntry.getSsl_protocol());
        dataMap.put("x_edge_result_type", logEntry.getX_edge_result_type());
        dataMap.put("sc_content_type", logEntry.getSc_content_type());
        dataMap.put("c_country", logEntry.getC_country());
        dataMap.put("cs_accept_encoding", logEntry.getCs_accept_encoding());
        dataMap.put("cs_accept", logEntry.getCs_accept());
        dataMap.put("cache_behavior_path_pattern", logEntry.getCache_behavior_path_pattern());
        dataMap.put("primary_distribution_id", logEntry.getPrimary_distribution_id());
        dataMap.put("asn", logEntry.getAsn());

        return dataMap;
    }
}
