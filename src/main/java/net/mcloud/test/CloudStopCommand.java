package net.mcloud.test;

import net.mcloud.api.command.Command;
import net.mcloud.api.command.CommandResponse;

import java.util.ArrayList;

public class CloudStopCommand extends Command {

    @Override
    public String usage() {
        return "Stoppt die Cloud";
    }

    @Override
    public CommandResponse execute(String command_name, ArrayList<String> args) {
        if (args.size() == 0) {
            System.exit(0);
            logger().warn("Die Cloud wird nun gestoppt!");
            return CommandResponse.SUCCESS;
        } else {
            logger().warn("For this Command it is not allowed to write Arguments behind them! please use stop");
            return CommandResponse.WARNING;
        }
    }
}
