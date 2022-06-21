package net.mcloud.api.events;

import net.mcloud.MCloud;
import net.mcloud.utils.exceptions.EventException;

public class RegisteredListener {

    private final Listener listener;

    private final EventPriority eventPriority;

    private final MCloud mCloud;

    private final EventExecutor eventExecutor;

    private  final boolean ignoreCancelled;

    public RegisteredListener(Listener listener, EventExecutor eventExecutor, EventPriority eventPriority, MCloud mCloud, boolean ignoreCancelled) {
        this.listener = listener;
        this.eventPriority = eventPriority;
        this.eventExecutor = eventExecutor;
        this.ignoreCancelled = ignoreCancelled;
        this.mCloud = mCloud;
    }

    public void callEvent(Event event) throws EventException {
        if(event instanceof Cancellable) {
            if( event.isCancelled() && isIgnoreCancelled()) {
                return;
            }
        }
        eventExecutor.execute(listener, event);
    }

    public Listener getListener() {
        return listener;
    }

    public MCloud getMCloud() {
        return mCloud;
    }

    public EventPriority getEventPriority() {
        return eventPriority;
    }

    public boolean isIgnoreCancelled() {
        return ignoreCancelled;
    }
}
