package net.mcloud.api.command;


import lombok.Getter;
import net.mcloud.MCloud;
import net.mcloud.api.events.server.ConsoleCommandSendEvent;
import net.mcloud.utils.logger.ConsoleColor;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.util.ArrayList;

public class ConsoleCommandHandler {

    @Getter
    private CommandMap commandMap;
    private String[] command_line;
    private String command_name;
    private ArrayList<String> args;

    public ConsoleCommandHandler(CommandMap commandMap) {
        this.commandMap = commandMap;
    }

    public void startConsoleInput() {
        this.args = new ArrayList<>();
        Terminal terminal = null;
        try {
            terminal = TerminalBuilder.builder()
                    .system(true)
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LineReader lineReader = LineReaderBuilder.builder()
                .terminal(terminal)
                .build();

        String prompt = "Cloud> ";
        while (MCloud.getCloud().isEnabled()) {
            String line;
            try {
                line = lineReader.readLine(ConsoleColor.RESET.getColor() + prompt);
                command_line = line.split(" ");
                command_name = command_line[0];
                for (String s : command_line) {
                    if (!s.equals(command_name)) {
                        args.add(s);
                    }
                }
                ConsoleCommandSendEvent event = new ConsoleCommandSendEvent(command_name, args);
                if (!event.isCancelled()) {
                    MCloud.getCloud().getCloudManager().callEvent(event);
                    if (getCommandMap() == null) {
                        MCloud.getCloud().getLogger().error("CommandMap is null");
                        args.clear();
                        return;
                    }
                    Command command = getCommandMap().getMap().get(command_name);
                    if (command == null) {
                        MCloud.getCloud().getLogger().error("Dieser Command wurde nicht gefunden!");
                        args.clear();
                        return;
                    }
                    CommandResponse execute = command.execute(command_name, args);
                    MCloud.getCloud().getLogger().info("Command " + execute.name());
                    args.clear();
                }

            } catch (UserInterruptException | EndOfFileException e) {
                e.printStackTrace();
            }
        }
    }
}
