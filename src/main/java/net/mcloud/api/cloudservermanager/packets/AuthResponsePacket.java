package net.mcloud.api.cloudservermanager.packets;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuthResponsePacket {
    private String response;

    public AuthResponsePacket(String response) {
        this.response = response;
    }

    public AuthResponsePacket() {

    }
}
