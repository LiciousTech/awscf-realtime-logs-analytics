package com.licious.cflogprocessor.datasource;

import com.licious.cflogprocessor.formatter.CloudfrontLogEntry;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
public class CompositeWriter implements Writer{
    private List<Writer> writers;

    @Autowired
    public CompositeWriter(List<Writer> writers) {
        this.writers = writers;
    }

    @Override
    public void write(CloudfrontLogEntry logEntry) throws Exception {
        for (Writer writer : writers) {
            writer.write(logEntry);
        }
    }

}
