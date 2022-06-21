package net.mcloud.api.events.server;

import net.mcloud.api.events.Event;
import net.mcloud.api.events.HandlerList;

public class MCloudStopEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private String reason;

    public MCloudStopEvent(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public static HandlerList getHandlers() {
        return handlers;
    }
}
