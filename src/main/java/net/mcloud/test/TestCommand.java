package net.mcloud.test;

import net.mcloud.MCloud;
import net.mcloud.api.command.Command;
import net.mcloud.api.command.CommandResponse;
import net.mcloud.utils.logger.LoggerType;

import java.util.ArrayList;

public class TestCommand extends Command {
    @Override
    public CommandResponse execute(String command_name, ArrayList<String> args) {
        MCloud.getCloud().getLogger().log(LoggerType.INFO, "The Command " + command_name + " is triggered");
        return CommandResponse.SUCCESS;
    }
}