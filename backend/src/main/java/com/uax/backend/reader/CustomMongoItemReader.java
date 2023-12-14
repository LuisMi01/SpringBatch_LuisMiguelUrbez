package com.uax.backend.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Iterator;

public class CustomMongoItemReader<T> implements ItemReader<T> {

    private final Iterator<T> dataIterator;

    public CustomMongoItemReader(MongoTemplate mongoTemplate, String collectionName, Class<T> targetType) {
        this.dataIterator = mongoTemplate.find(new Query(), targetType, collectionName).iterator();
    }

    @Override
    public T read() {
        if (this.dataIterator.hasNext()) {
            return this.dataIterator.next();
        } else {
            return null;
        }
    }
}