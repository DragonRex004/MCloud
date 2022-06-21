package net.mcloud.utils.logger;

public class Logger {
    public void log(LoggerType type, String logMessage) {
        switch (type) {
            case INFO -> System.out.println("§2[INFO] " + logMessage);
            case WARNING -> System.out.println("§6[WARNING] " + logMessage);
            case ERROR -> System.out.println("§4[ERROR] " + logMessage);
        }
    }
}
