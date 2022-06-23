package net.mcloud.api.command;

import net.mcloud.MCloud;
import net.mcloud.utils.logger.Logger;

import java.util.ArrayList;

public abstract class Command {

    public Logger logger() {
        return MCloud.getCloud().getLogger();
    }

    public abstract CommandResponse execute(String command_name, ArrayList<String> args);

    public abstract String usage();
}
