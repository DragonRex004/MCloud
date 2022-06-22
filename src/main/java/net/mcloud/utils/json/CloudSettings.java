package net.mcloud.utils.json;

public class CloudSettings {
    private int UDP_PORT_CLOUD_MANAGER;
    private int TCP_PORT_CLOUD_MANAGER;
    private boolean SETTINGS_DEPRECATED_EVENTS;

    public CloudSettings(int tcp_port, int udp_port, boolean deprecated_events) {
        this.TCP_PORT_CLOUD_MANAGER = tcp_port;
        this.UDP_PORT_CLOUD_MANAGER = udp_port;
        this.SETTINGS_DEPRECATED_EVENTS = deprecated_events;
    }

    public int getTcpPort() {
        return TCP_PORT_CLOUD_MANAGER;
    }

    public int getUdpPort() {
        return UDP_PORT_CLOUD_MANAGER;
    }

    public boolean isSettingsDeprecatedEvents() {
        return SETTINGS_DEPRECATED_EVENTS;
    }

    public void setUdpPort(int value) {
        this.UDP_PORT_CLOUD_MANAGER = value;
    }

    public void setTcpPort(int value) {
        this.TCP_PORT_CLOUD_MANAGER = value;
    }

    public void setSettingsDeprecatedEvents(boolean value) {
        this.SETTINGS_DEPRECATED_EVENTS = value;
    }
}
