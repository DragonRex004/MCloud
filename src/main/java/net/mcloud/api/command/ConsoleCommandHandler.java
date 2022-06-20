package net.mcloud.api.command;


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

    public void startConsoleInput() {
        this.COMMAND_MAP = new CommandMap();
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
        while (true) {
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
                COMMAND_MAP.getMap().get(command_name).execute(command_name, args);
                args.clear();

            } catch (UserInterruptException | EndOfFileException e){
                e.printStackTrace();
            }
        }
    }

    public CommandMap getCommandMap() {
        return COMMAND_MAP;
    }
}
