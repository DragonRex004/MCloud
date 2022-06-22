package net.mcloud.test;

import net.mcloud.api.command.Command;
import net.mcloud.api.command.CommandResponse;
import net.mcloud.utils.json.CloudSettings;

import java.util.ArrayList;

public class ConfigSettingsCommand extends Command {
    private CloudSettings cloudSettings;

    public ConfigSettingsCommand(CloudSettings cloudSettings) {
        this.cloudSettings = cloudSettings;
    }

    @Override
    public CommandResponse execute(String command_name, ArrayList<String> args) {
        return null;
    }
}
