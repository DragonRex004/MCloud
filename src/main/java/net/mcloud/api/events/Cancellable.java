package net.mcloud.api.events;

public interface Cancellable {

    boolean isCancelled();
    void setCancelled(boolean value);
}
