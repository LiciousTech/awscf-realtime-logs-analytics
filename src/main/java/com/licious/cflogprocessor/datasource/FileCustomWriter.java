package com.licious.cflogprocessor.datasource;

import com.licious.cflogprocessor.formatter.CloudfrontLogEntry;

import java.io.FileWriter;
import java.io.PrintWriter;

public class FileCustomWriter implements Writer {

    private final String filePath;

    public FileCustomWriter(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void write(CloudfrontLogEntry cloudfrontLogEntry) throws Exception {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.println(cloudfrontLogEntry);
        } catch (Exception e) {
            throw new Exception("Failed Writing data to file: ", e);
        }
    }
}
