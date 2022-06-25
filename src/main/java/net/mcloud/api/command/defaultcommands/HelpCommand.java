/*
    --------------------------
    Project : MCloud
    Package : net.mcloud.api.command.impl
    Date 23.06.2022
    @author ShortException
    --------------------------
*/


package net.mcloud.api.command.defaultcommands;

import net.mcloud.MCloud;
import net.mcloud.api.command.Command;
import net.mcloud.api.command.CommandResponse;
import net.mcloud.utils.logger.ConsoleColor;
import org.jline.reader.impl.completer.ArgumentCompleter;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HelpCommand extends Command {

    @Override
    public CommandResponse execute(String command_name, ArrayList<String> args) {

        logger().info("all commands listed", ConsoleColor.WHITE);

        MCloud.getCloud().getCommandMap().getMap().forEach((s, command) -> {
            logger().info(s + " - " + command.usage(), ConsoleColor.PURPLE);
        });

        return CommandResponse.SUCCESS;
    }

    @Override
    public String usage() {
        return "Zeigt dir eine Liste mit allen Befehlen an.";
    }

    @Override
    public String name() {
        return "help";
    }

    @Override
    public Map<String, List<AttributedString>> widgetOpt() {
        return new HashMap<>();
    }

    @Override
    public List<AttributedString> desc() {
        return List.of(new AttributedString("Get help about the commands"));
    }
}
