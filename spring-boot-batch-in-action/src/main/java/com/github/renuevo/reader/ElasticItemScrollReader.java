package com.github.renuevo.reader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.data.AbstractPaginatedDataItemReader;

import java.util.Iterator;

@Slf4j
public class ElasticItemScrollReader<T> extends AbstractPaginatedDataItemReader<T> {

    @Override
    protected void doOpen() throws Exception {
    }

    @Override
    protected Iterator<T> doPageRead() {
        return null;
    }
}
