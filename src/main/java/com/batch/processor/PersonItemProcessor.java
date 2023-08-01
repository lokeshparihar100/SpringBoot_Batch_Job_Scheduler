package com.batch.processor;


import org.springframework.batch.item.ItemProcessor;

public class PersonItemProcessor<T> implements ItemProcessor<T, T> {

    @Override
    public T process(T item) throws Exception {
        // Perform any data processing or transformations if required
        return item;
    }
}


