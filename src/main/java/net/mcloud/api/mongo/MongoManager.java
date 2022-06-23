/*
    --------------------------
    Project : MCloud
    Package : net.mcloud.api.mongo
    Date 23.06.2022
    @author ShortException
    --------------------------
*/


package net.mcloud.api.mongo;

import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;

import java.util.function.Consumer;

public class MongoManager {

    private final String url;
    private MongoDatabase database = null;
    private MongoClient client = null;

    public MongoManager(String url) {
        this.url = url;
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public MongoClient getClient() {
        return client;
    }

    public void connect(String database) {
        client = MongoClients.create(url);
        this.database = client.getDatabase(database);
    }

    public <T> void mongoCollection(String collectionName, Class<T> collectionType, Consumer<MongoCollection<T>> consumer) {
        consumer.accept(this.database.getCollection(collectionName, collectionType));
    }

    public void disConnect() {
        client.close();
    }


}