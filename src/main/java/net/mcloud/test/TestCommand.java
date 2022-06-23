package net.mcloud.test;

import net.mcloud.MCloud;
import net.mcloud.api.command.Command;
import net.mcloud.api.command.CommandResponse;

import java.util.ArrayList;

public class TestCommand extends Command {
    @Override
    public CommandResponse execute(String command_name, ArrayList<String> args) {
        MCloud.getCloud().getLogger().info("The Command " + command_name + " is triggered");
        MCloud.getCloud().getLogger().warn("The Command " + command_name + " is triggered");
        MCloud.getCloud().getLogger().error("The Command " + command_name + " is triggered");
        return CommandResponse.SUCCESS;
    }

    @Override
    public String usage() {
        return "only for testing stuff";
    }
}
