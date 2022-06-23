/*
    --------------------------
    Project : MCloud
    Package : net.mcloud.api.redis
    Date 23.06.2022
    @author ShortException
    --------------------------
*/


package net.mcloud.api.redis;

import com.lambdaworks.redis.BaseRedisConnection;
import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisConnection;
import com.lambdaworks.redis.RedisURI;

import java.util.ArrayList;

public class RedisManager {

    private final RedisURI uri;
    private final ArrayList<RedisConnection<String, String>> connectionList;
    private RedisClient client;

    public RedisManager(RedisURI uri) {
        connectionList = new ArrayList<>();
        this.uri = uri;
        Runtime.getRuntime().addShutdownHook(new Task());
    }

    public RedisClient getClient() {
        return client;
    }

    public ArrayList<RedisConnection<String, String>> getConnectionList() {
        return connectionList;
    }

    public void connect() {
        client = new RedisClient(uri);
    }

    public RedisConnection<String, String> redisConnection() {
        RedisConnection<String, String> collection = client.connect();
        connectionList.add(collection);
        return collection;
    }

    public void disConnect() {
        if (client != null)
            client.shutdown();
    }

    private class Task extends Thread {
        @Override
        public void run() {
            connectionList.forEach(BaseRedisConnection::close);
            disConnect();
        }
    }
}
