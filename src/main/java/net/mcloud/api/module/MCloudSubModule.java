/*
    --------------------------
    Project : MCloud
    Package : net.mcloud.api.module
    Date 23.06.2022
    @author ShortException
    --------------------------
*/


package net.mcloud.api.module;

import com.lambdaworks.redis.RedisURI;
import lombok.Getter;
import net.mcloud.MCloud;
import net.mcloud.api.mongo.MongoManager;
import net.mcloud.api.redis.RedisManager;
import net.mcloud.utils.logger.Logger;

public abstract class MCloudSubModule {

    public MCloudSubModule() {
        MCloud.getCloud().getSubModuleModuleManager().registerModule(this);
    }

    public abstract String getModuleName();
    public abstract void onLoad();
    public abstract void onStart();
    public abstract void onStop();

    public MongoManager getNewMongoManager(String connectionString) {
        return new MongoManager(connectionString);
    }

    public RedisManager getNewRedisManager(String connectionString) {
        return new RedisManager(RedisURI.create(connectionString));
    }

    @Getter
    public Logger logger = new Logger();

}
