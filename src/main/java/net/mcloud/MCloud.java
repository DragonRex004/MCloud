package net.mcloud;

import com.lambdaworks.redis.RedisURI;
import lombok.Getter;
import net.mcloud.api.cloudservermanager.CloudServerManager;
import net.mcloud.api.command.CommandMap;
import net.mcloud.api.command.ConsoleCommandHandler;
import net.mcloud.api.command.defaultcommands.CloudStopCommand;
import net.mcloud.api.command.defaultcommands.HelpCommand;
import net.mcloud.api.events.HandlerList;
import net.mcloud.api.events.server.MCloudStopEvent;
import net.mcloud.api.module.MCloudSubModule;
import net.mcloud.api.module.ModuleManager;
import net.mcloud.api.redis.RedisManager;
import net.mcloud.test.ClientTestCommand;
import net.mcloud.test.CloudStopListener;
import net.mcloud.test.TestCommand;
import net.mcloud.test.TestListener;
import net.mcloud.utils.CloudManager;
import net.mcloud.utils.json.JsonConfigBuilder;
import net.mcloud.utils.logger.ConsoleColor;
import net.mcloud.utils.logger.Logger;

@Getter
public class MCloud {
    private static MCloud mCloud;
    private final CloudManager cloudManager;
    private final CommandMap commandMap;
    private final Logger logger;
    private final JsonConfigBuilder jsonConfigBuilder;
    private boolean isEnabled;
    private ConsoleCommandHandler commandHandler;
    private ModuleManager subModuleModuleManager;
    private CloudServerManager cloudServerManager;

    private RedisManager redisManager;


    public MCloud() {
        mCloud = this;
        this.logger = new Logger();
        Runtime.getRuntime().addShutdownHook(new ShutdownTask());

        logger.info("""          
                                
                ___  ___         _____  _                    _\s
                |  \\/  |        /  __ \\| |                  | |
                | .  . | ______ | /  \\/| |  ___   _   _   __| |
                | |\\/| ||______|| |    | | / _ \\ | | | | / _` |
                | |  | |        | \\__/\\| || (_) || |_| || (_| |
                \\_|  |_/         \\____/|_| \\___/  \\__,_| \\__,_|
                                                              \s
                """, ConsoleColor.CYAN);

        logger.info("Cloud starting... ", ConsoleColor.GREEN);
        isEnabled = true;

        this.jsonConfigBuilder = new JsonConfigBuilder("cloudsettings", "settings");

        logger.info("Loading Settings");
        setDefaultSettings();

        logger.info("Starting Redis Client");
        redisManager = new RedisManager(RedisURI.create(jsonConfigBuilder.getString("redisConnectionString", "redis://redispw@localhost:49153")));

        this.cloudManager = new CloudManager(mCloud);
        this.commandHandler = new ConsoleCommandHandler(new CommandMap());
        this.commandMap = commandHandler.getCommandMap();
        this.cloudServerManager = new CloudServerManager();

        logger.info("Register Listeners");
        registerListener();

        logger.info("Register Commands");
        registerCommand();

        subModuleModuleManager = new ModuleManager();

        logger.info("Loading Modules");
        subModuleModuleManager.getModules().forEach(MCloudSubModule::onLoad);
        logger.info("Starting Modules");
        subModuleModuleManager.getModules().forEach(MCloudSubModule::onStart);

        logger.info("Starting CloudServerManager");
        this.cloudServerManager.createCloudServer();
        this.cloudServerManager.startCloudServer();
        logger.info("Finished! CloudServerManager ready.");

        logger.info("Starting ConsoleInput");
        this.commandHandler.startConsoleInput();
    }

    public static void main(String[] args) {
        new MCloud();
    }

    public static MCloud getCloud() {
        return mCloud;
    }

    public void setDefaultSettings() {
        jsonConfigBuilder.setInteger("udp-port", 54777, 54777);
        jsonConfigBuilder.setInteger("tcp-port", 54555, 54555);
        jsonConfigBuilder.setBoolean("deprecated-events", true, true);
        jsonConfigBuilder.setString("redisConnectionString", "redis://redispw@localhost:49153", "redis://redispw@localhost:49153");
        jsonConfigBuilder.setInteger("max-cloud-proxy-count", 1, 1);
    }

    private void registerListener() {
        CloudManager cloudManager = MCloud.getCloud().getCloudManager();
        cloudManager.registerEvents(new TestListener(), this);
        cloudManager.registerEvents(new CloudStopListener(this), this);
    }

    private void registerCommand() {
        CommandMap commandMap = getCommandMap();
        commandMap.register(new HelpCommand());
        commandMap.register(new TestCommand());
        commandMap.register(new ClientTestCommand());
        commandMap.register(new CloudStopCommand());
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    private class ShutdownTask extends Thread {
        @Override
        public void run() {
            getLogger().warn("The cloud is trying to shutdown");
            MCloudStopEvent event = new MCloudStopEvent("The System Shutdown Normal");
            cloudManager.callEvent(event);
            getLogger().info("Stopping Modules ...");
            getSubModuleModuleManager().getModules().forEach(MCloudSubModule::onStop);
            getLogger().warn("Stopped Modules!");
            isEnabled = false;
            HandlerList.unregisterAll();
            cloudServerManager.stopCloudServer();
            getLogger().info("Good bye!", ConsoleColor.PURPLE);
        }
    }

}
