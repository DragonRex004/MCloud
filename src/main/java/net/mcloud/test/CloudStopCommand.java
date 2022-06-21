package net.mcloud.test;

import net.mcloud.MCloud;
import net.mcloud.api.command.Command;
import net.mcloud.api.command.CommandResponse;
import net.mcloud.utils.logger.LoggerType;

import java.util.ArrayList;

public class CloudStopCommand extends Command {
    private MCloud cloud;

    public CloudStopCommand(MCloud cloud) {
        this.cloud = cloud;
    }

    @Override
    public CommandResponse execute(String command_name, ArrayList<String> args) {
        if(args.size() == 0) {
            this.cloud.shutdown();
            return CommandResponse.SUCCESS;
        } else {
            this.cloud.getLogger().log(LoggerType.WARNING, "For this Command it is not allowed to write Arguments behind them! please use stop");
            return CommandResponse.WARNING;
        }
    }
}
