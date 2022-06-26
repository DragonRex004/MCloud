package net.mcloud.api.cloudservermanager.packets;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProxyAuthResponsePacket {
    private String response;

    public ProxyAuthResponsePacket(String response) {
        this.response = response;
    }
}
