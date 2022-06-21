package net.mcloud.api.command;


import net.mcloud.MCloud;
import net.mcloud.api.events.server.ConsoleCommandSendEvent;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.util.ArrayList;

public class ConsoleCommandHandler {

    private CommandMap COMMAND_MAP;
    private String[] command_line;
    private String command_name;
    private ArrayList<String> args;

    public ConsoleCommandHandler(CommandMap commandMap) {
        this.COMMAND_MAP = commandMap;
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

        String prompt = "dragon> ";
        while (MCloud.getCloud().isEnabled()) {
            String line;
            try {
                line = lineReader.readLine(prompt);
                command_line = line.split(" ");
                command_name = command_line[0];
                for (String s : command_line) {
                    if(!s.equals(command_name)) {
                        args.add(s);
                    }
                }
                ConsoleCommandSendEvent event = new ConsoleCommandSendEvent(command_name, args);
                if(!event.isCancelled()) {
                    MCloud.getCloud().getCloudManager().callEvent(event);
                    if (getCommandMap() == null) return;
                    getCommandMap().getMap().get(command_name).execute(command_name, args);
                    args.clear();
                }

            } catch (UserInterruptException | EndOfFileException e){
                e.printStackTrace();
            }
        }
    }

    public CommandMap getCommandMap() {
        return COMMAND_MAP;
    }
}
