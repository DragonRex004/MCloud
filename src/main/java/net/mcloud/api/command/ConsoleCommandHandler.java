package net.mcloud.api.command;


import lombok.Getter;
import net.mcloud.MCloud;
import net.mcloud.api.events.server.ConsoleCommandSendEvent;
import net.mcloud.utils.logger.ConsoleColor;
import org.jline.console.ArgDesc;
import org.jline.console.CmdDesc;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.widget.TailTipWidgets;

import java.io.IOException;
import java.util.*;

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

        List<String> completer = new ArrayList<>();
        commandMap.getMap().forEach((s, command) -> {
            completer.add(s);
        });

        LineReaderBuilder builder = LineReaderBuilder.builder();
        builder.completer(new StringsCompleter(completer));
        LineReader lineReader = builder.terminal(terminal).build();

        Map<String, CmdDesc> tailTips = new HashMap<>();

        commandMap.getMap().forEach((s, command) -> {
            tailTips.put(s, new CmdDesc(command.desc(), ArgDesc.doArgNames(List.of("[...]")), command.widgetOpt()));
        });

        TailTipWidgets widgets = new TailTipWidgets(lineReader, tailTips, 10, TailTipWidgets.TipType.TAIL_TIP);
        widgets.enable();

        String prompt = "Cloud> ";
        while (MCloud.getCloud().isEnabled()) {
            String line;
            try {
                lineReader.setAutosuggestion(LineReader.SuggestionType.TAIL_TIP);
                line = readLine(lineReader, prompt);
                command_line = line.split(" ");
                command_name = command_line[0].replaceAll(" ", "");
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
                    } else {
                        Command command = getCommandMap().getMap().get(command_name);
                        if (command == null) {
                            MCloud.getCloud().getLogger().error("Dieser Command wurde nicht gefunden!");
                            args.clear();
                        } else {
                            CommandResponse execute = command.execute(command_name, args);
                            MCloud.getCloud().getLogger().info("Command " + execute.name());
                            args.clear();
                        }
                    }
                }

            } catch (UserInterruptException | EndOfFileException e) {
                e.printStackTrace();
            }
        }
    }

    private String readLine(LineReader lineReader, String prompt) {
        return lineReader.readLine(ConsoleColor.RESET.getColor() + prompt).toLowerCase(Locale.ROOT);
    }
}
