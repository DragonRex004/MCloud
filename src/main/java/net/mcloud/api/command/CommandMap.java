package net.mcloud.api.command;

import java.util.HashMap;
import java.util.Map;

public class CommandMap {

    private Map<String, Command> COMMAND_MAP;

    public CommandMap() {
        COMMAND_MAP = new HashMap<>();
    }

    public void register(String command, Command command_clazz) {
        COMMAND_MAP.put(command, command_clazz);
    }

    public void unregister(String command) {
        COMMAND_MAP.remove(command);
    }

    public Map<String, Command> getMap() {
        return COMMAND_MAP;
    }
}
