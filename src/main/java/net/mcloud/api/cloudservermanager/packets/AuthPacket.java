package net.mcloud.api.cloudservermanager.packets;

import lombok.Getter;
import lombok.Setter;
import net.mcloud.api.cloudservermanager.groups.ServerType;

@Getter @Setter
public class AuthPacket {
    private String serverType;
    private String serverName;
    private String ip;
    private int port;
    private String startShPath;

    public AuthPacket(String serverType, String serverName, String ip, int port, String startShPath) {
        this.serverName = serverName;
        this.serverType = serverType;
        this.ip = ip;
        this.port = port;
        this.startShPath = startShPath;
    }

    public AuthPacket() {

    }
}