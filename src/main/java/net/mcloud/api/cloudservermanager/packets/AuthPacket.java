package net.mcloud.api.cloudservermanager.packets;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuthPacket {
    private String serverType;
    private String ip;
    private int port;
    private String startShPath;

    public AuthPacket(String serverType, String ip, int port, String startShPath) {
        this.serverType = serverType;
        this.ip = ip;
        this.port = port;
        this.startShPath = startShPath;
    }

    public AuthPacket() {

    }
}