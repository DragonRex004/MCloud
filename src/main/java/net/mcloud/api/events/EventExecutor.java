package net.mcloud.api.events;

import net.mcloud.utils.exceptions.EventException;

public interface EventExecutor {

    void execute(Listener listener, Event event) throws EventException;
}
