/*
    --------------------------
    Project : MCloud
    Package : net.mcloud.api.command.impl
    Date 23.06.2022
    @author ShortException
    --------------------------
*/


package net.mcloud.api.command.impl;

import net.mcloud.MCloud;
import net.mcloud.api.command.Command;
import net.mcloud.api.command.CommandResponse;
import net.mcloud.utils.logger.ConsoleColor;

import java.util.ArrayList;

public class HelpCommand extends Command {

    @Override
    public CommandResponse execute(String command_name, ArrayList<String> args) {

        logger().info("Ein Überblick über alle Befehle", ConsoleColor.WHITE);

        MCloud.getCloud().getCommandMap().getMap().forEach((s, command) -> {
            logger().info(s + " - " + command, ConsoleColor.PURPLE);
        });

        return CommandResponse.SUCCESS;
    }

    @Override
    public String usage() {
        return "Zeigt dir eine Liste mit allen Befehlen an.";
    }
}
