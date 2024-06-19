package com.licious.cflogprocessor.datasource;

import com.licious.cflogprocessor.formatter.CloudfrontLogEntry;

public class StdoutWriter implements Writer {

    public StdoutWriter() {

    }

    @Override
    public void write(CloudfrontLogEntry logEntry) throws Exception {
        System.out.println(logEntry);
    }
}
