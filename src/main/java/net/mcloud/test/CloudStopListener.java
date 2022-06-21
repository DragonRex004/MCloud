package net.mcloud.test;

import net.mcloud.MCloud;
import net.mcloud.api.events.EventHandler;
import net.mcloud.api.events.Listener;
import net.mcloud.api.events.server.MCloudStopEvent;
import net.mcloud.utils.logger.LoggerType;

public class CloudStopListener implements Listener {
    private MCloud mCloud;

    public CloudStopListener(MCloud mCloud) {
        this.mCloud = mCloud;
    }

    @EventHandler
    public void onCloudStop(MCloudStopEvent event) {
        this.mCloud.getLogger().log(LoggerType.INFO, "CloudStop Listener Triggered " + event.getEventName());
    }
}
