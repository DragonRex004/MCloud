package net.mcloud.test;

import net.mcloud.MCloud;
import net.mcloud.api.events.EventHandler;
import net.mcloud.api.events.Listener;
import net.mcloud.api.events.server.ConsoleCommandSendEvent;
import net.mcloud.utils.logger.LoggerType;

public class TestListener implements Listener {

    @EventHandler
    public void onConsoleCommandSend(ConsoleCommandSendEvent event) {
        MCloud.getCloud().getLogger().log(LoggerType.INFO, "Command Listener Triggered " + event.getEventName());
    }
}
