package com.licious.cflogprocessor.formatter;

import lombok.Data;

// https://docs.aws.amazon.com/AmazonCloudFront/latest/DeveloperGuide/real-time-logs.html

@Data
public class CloudfrontLogEntry {

    private String timestamp;
    private String c_ip;
    private String time_to_first_byte;
    private String sc_status;
    private String sc_bytes;
    private String cs_method;
    private String cs_protocol;
    private String cs_host;
    private String cs_uri_stem;
    private String cs_bytes;
    private String x_edge_location;
    private String x_host_header;
    private String cs_protocol_version;
    private String c_ip_version;
    private String cs_user_agent;
    private String cs_referer;
    private String cs_uri_query;
    private String x_edge_response_result_type;
    private String x_forwarded_for;
    private String ssl_protocol;
    private String x_edge_result_type;
    private String sc_content_type;
    private String c_country;
    private String cs_accept_encoding;
    private String cs_accept;
    private String cache_behavior_path_pattern;
    private String primary_distribution_id;
    private String asn;

    // Constructors
    public CloudfrontLogEntry() {
    }

}
