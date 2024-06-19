package com.licious.cflogprocessor.datasource;

import com.licious.cflogprocessor.formatter.CloudfrontLogEntry;

public interface Writer {
    void write(CloudfrontLogEntry logEntry) throws Exception;
}
