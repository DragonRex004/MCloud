/*
    --------------------------
    Project : MCloud
    Package : net.mcloud.utils.logger
    Date 23.06.2022
    @author ShortException
    --------------------------
*/


package net.mcloud.utils.logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    public void error(String error) {
        System.out.println(ConsoleColor.BLUE.getColor() +
                getDate() + ConsoleColor.RED.getColor() + " [ERROR] " + ConsoleColor.RED.getColor() + error);
    }

    public void warn(String warn) {
        System.out.println(ConsoleColor.BLUE.getColor() +
                getDate() + ConsoleColor.YELLOW.getColor() + " [WARN] " + ConsoleColor.YELLOW.getColor() + warn);
    }

    public void info(String info) {
        System.out.println(ConsoleColor.BLUE.getColor() +
                getDate() + ConsoleColor.WHITE.getColor() + " [INFO] " + ConsoleColor.RESET.getColor() + info);
    }

    public void info(String info, ConsoleColor consoleColor) {
        System.out.println(ConsoleColor.BLUE.getColor() +
                getDate() + ConsoleColor.WHITE.getColor() + " [INFO] " + consoleColor.getColor() + info);
    }

    private String getDate() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis()));
    }
}
