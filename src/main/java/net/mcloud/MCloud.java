package net.mcloud;

import lombok.Getter;
import net.mcloud.api.command.CommandMap;
import net.mcloud.api.command.ConsoleCommandHandler;
import net.mcloud.api.command.defaultcommands.CloudStopCommand;
import net.mcloud.api.command.defaultcommands.HelpCommand;
import net.mcloud.api.events.HandlerList;
import net.mcloud.api.events.server.MCloudStopEvent;
import net.mcloud.test.CloudStopListener;
import net.mcloud.test.TestCommand;
import net.mcloud.test.TestListener;
import net.mcloud.utils.CloudManager;
import net.mcloud.utils.Downloader;
import net.mcloud.utils.json.CloudSettings;
import net.mcloud.utils.json.JsonConfigBuilder;
import net.mcloud.utils.logger.ConsoleColor;
import net.mcloud.utils.logger.Logger;

import java.io.IOException;
import java.net.URL;

@Getter
public class MCloud {
    private static MCloud mCloud;
    private final CloudManager cloudManager;
    private final CommandMap commandMap;
    private final Logger logger;
    private final JsonConfigBuilder jsonConfigBuilder;
    private boolean isEnabled;
    private ConsoleCommandHandler commandHandler;
    private CloudSettings cloudSettings;


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
        this.cloudManager = new CloudManager(this.jsonConfigBuilder);
        this.commandHandler = new ConsoleCommandHandler(new CommandMap());
        this.commandMap = commandHandler.getCommandMap();
        registerListener();
        registerCommand();
        this.commandHandler.startConsoleInput();
        setDefaultSettings();
    }

    public static void main(String[] args) {
        new MCloud();
    }

    public static MCloud getCloud() {
        return mCloud;
    }

    public void setDefaultSettings() {
        this.cloudSettings = new CloudSettings(54555, 54777, true);
        this.jsonConfigBuilder.getObject("cloud", this.cloudSettings);
    }

    public void shutdown() {
        MCloudStopEvent event = new MCloudStopEvent("The System Shutdown Normal");
        this.cloudManager.callEvent(event);
        this.getLogger().warn("The cloud is trying to shutdown");
        isEnabled = false;
        HandlerList.unregisterAll();
    }

    private void registerListener() {
        CloudManager cloudManager = MCloud.getCloud().getCloudManager();
        cloudManager.registerEvents(new TestListener(), this);
        cloudManager.registerEvents(new CloudStopListener(this), this);
    }

    private void registerCommand() {
        CommandMap commandMap = getCommandMap();
        commandMap.register("help", new HelpCommand());
        commandMap.register("test", new TestCommand());
        commandMap.register("stop", new CloudStopCommand());
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    private class ShutdownTask extends Thread {
        @Override
        public void run() {
            shutdown();
        }
    }

}
