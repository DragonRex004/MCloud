package net.mcloud.api.cloudservermanager.packets;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProxyAuthPacket {
    private String serverType;
    private String ip;
    private int port;

    public ProxyAuthPacket(String serverType, String ip, int port) {
        this.serverType = serverType;
        this.ip = ip;
        this.port = port;
    }
}