package com.licious.cflogprocessor.service;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.v2.IRecordProcessor;
import com.amazonaws.services.kinesis.clientlibrary.types.InitializationInput;
import com.amazonaws.services.kinesis.clientlibrary.types.ProcessRecordsInput;
import com.amazonaws.services.kinesis.clientlibrary.types.ShutdownInput;
import com.amazonaws.services.kinesis.model.Record;
import com.licious.cflogprocessor.datasource.CompositeWriter;
import com.licious.cflogprocessor.datasource.ElasticsearchWriter;
import com.licious.cflogprocessor.datasource.Writer;
import com.licious.cflogprocessor.formatter.CloudfrontLogEntry;
import com.licious.cflogprocessor.formatter.CloudfrontLogEntrySerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.script.Compilable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class SimpleRecordProcessor implements IRecordProcessor {

    @Autowired
    private CompositeWriter compositeWriter;

    public SimpleRecordProcessor(CompositeWriter compositeWriter) {
        this.compositeWriter = compositeWriter;
    }

    @Override
    public void initialize(InitializationInput initializationInput) {
        // Initialization logic if needed
    }

    @Override
    public void processRecords(ProcessRecordsInput processRecordsInput) {
        List<Record> records = processRecordsInput.getRecords();
        System.out.println("Registered Writers");
        for(Writer writer : compositeWriter.getWriters()){
            System.out.println("Writer Name: " + writer.getClass().getSimpleName());
        }
        for (Record record : records) {
            // Assuming the data in the record is UTF-8 encoded
            String data = new String(record.getData().array(), StandardCharsets.UTF_8);

            CloudfrontLogEntry logEntry = CloudfrontLogEntrySerializer.parseRecord(data);

            try {
                compositeWriter.write(logEntry);
//
//                if (writerProperties.getLogDestination() == WriterProperties.WriterDestination.CLICKHOUSE) {
//                    if (clickhouseWriter != null) {
//                        clickhouseWriter.write(logEntry);
//                    }
//                } else if (writerProperties.getLogDestination() == WriterProperties.WriterDestination.ELASTICSEARCH) {
//                    if (elasticsearchWriter != null) {
//                        elasticsearchWriter.write(logEntry);
//                    }
//                } else {
//                    // Handle STDOUT or other destinations
//                    System.out.println("Writing to logs");
//                    System.out.println(logEntry);
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        // Consider implementing checkpoint logic here
    }

    @Override
    public void shutdown(ShutdownInput shutdownInput) {
        // Shutdown logic if needed
    }

}

