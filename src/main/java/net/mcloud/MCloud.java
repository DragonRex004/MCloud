package net.mcloud;

import net.mcloud.api.command.CommandMap;
import net.mcloud.api.command.ConsoleCommandHandler;
import net.mcloud.api.events.HandlerList;
import net.mcloud.api.events.server.MCloudStopEvent;
import net.mcloud.test.CloudStopCommand;
import net.mcloud.test.CloudStopListener;
import net.mcloud.test.TestCommand;
import net.mcloud.test.TestListener;
import net.mcloud.utils.CloudManager;
import net.mcloud.utils.json.CloudSettings;
import net.mcloud.utils.json.JsonConfigBuilder;
import net.mcloud.utils.logger.Logger;
import net.mcloud.utils.logger.LoggerType;

public class MCloud {
    private static MCloud mCloud;
    private boolean isEnabled;
    private CloudManager cloudManager;
    private ConsoleCommandHandler commandHandler;
    private CommandMap commandMap;
    private Logger logger;
    private JsonConfigBuilder jsonConfigBuilder;
    private CloudSettings cloudSettings;

    public void mCloud() {
        mCloud = this;
        isEnabled = true;
        this.jsonConfigBuilder = new JsonConfigBuilder("cloudsettings", "settings");
        this.cloudManager = new CloudManager(this.jsonConfigBuilder);
        this.commandHandler = new ConsoleCommandHandler(new CommandMap());
        this.commandMap = commandHandler.getCommandMap();
        this.logger = new Logger();
        registerListener();
        registerCommand();
        this.commandHandler.startConsoleInput();
        setDefaultSettings();
    }

    public void setDefaultSettings() {
        this.cloudSettings = new CloudSettings(54555, 54777, true);
        this.jsonConfigBuilder.getObject("cloud", this.cloudSettings);
    }

    public void shutdown() {
        MCloudStopEvent event = new MCloudStopEvent("The System Shutdown Normal");
            this.cloudManager.callEvent(event);
            this.getLogger().log(LoggerType.INFO, "The MCloud is try to shutdown");
            isEnabled = false;
            HandlerList.unregisterAll();
    }

    private void registerListener() {
        CloudManager cloudManager = MCloud.getCloud().getCloudManager();
        cloudManager.registerEvents(new TestListener(), this);
        cloudManager.registerEvents(new CloudStopListener(this), this);
    }

    private void registerCommand() {
        CommandMap commandMap = MCloud.getCloud().getCommandMap();
        commandMap.register("test", new TestCommand());
        commandMap.register("stop", new CloudStopCommand(this));
    }

    public static void main(String[] args) {
        MCloud mCloud = new MCloud();
        mCloud.mCloud();
    }

    public Logger getLogger() {
        return logger;
    }

    public CloudManager getCloudManager() {
        return cloudManager;
    }

    public static MCloud getCloud() {
        return mCloud;
    }

    public CommandMap getCommandMap() {
        return commandMap;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public JsonConfigBuilder getJsonConfigBuilder() {
        return jsonConfigBuilder;
    }

    public CloudSettings getCloudSettings() {
        return cloudSettings;
    }
}
