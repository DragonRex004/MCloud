package net.mcloud.test;

import net.mcloud.MCloud;
import net.mcloud.api.events.EventHandler;
import net.mcloud.api.events.Listener;
import net.mcloud.api.events.server.ConsoleCommandSendEvent;

public class TestListener implements Listener {

    @EventHandler
    public void onConsoleCommandSend(ConsoleCommandSendEvent event) {
        MCloud.getCloud().getLogger().info("Command Listener Triggered " + event.getEventName());
    }
}
