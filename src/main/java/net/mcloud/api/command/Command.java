package net.mcloud.api.command;

import java.util.ArrayList;

public abstract class Command {

    public abstract CommandResponse execute(String command_name, ArrayList<String> args);
}
