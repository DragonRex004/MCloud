package net.mcloud;

import net.mcloud.api.command.CommandMap;
import net.mcloud.api.command.ConsoleCommandHandler;

public class MCloud {
    private static MCloud mCloud;
    private ConsoleCommandHandler commandHandler;
    private CommandMap commandMap;

    public void mCloud() {
        mCloud = this;
        assert commandHandler != null;
        this.commandHandler = new ConsoleCommandHandler();
        this.commandHandler.startConsoleInput();
        this.commandMap = commandHandler.getCommandMap();

    }

    public static void main(String[] args) {
        MCloud mCloud = new MCloud();
        mCloud.mCloud();
    }

    public static MCloud getMCloud() {
        return mCloud;
    }

    public CommandMap getCommandMap() {
        return commandMap;
    }
}
