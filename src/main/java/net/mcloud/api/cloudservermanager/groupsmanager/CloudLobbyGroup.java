package net.mcloud.api.cloudservermanager.groupsmanager;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import lombok.Getter;
import net.mcloud.MCloud;
import net.mcloud.api.cloudservermanager.groups.ServerType;
import net.mcloud.api.cloudservermanager.packets.AuthPacket;
import net.mcloud.api.cloudservermanager.packets.AuthResponsePacket;

import java.io.IOException;

public class CloudLobbyGroup {
    @Getter
    private Client lobby;
    private int cloudLobbyCount = 0;
    private Kryo kryo;

    public void createCloudLobby() {
        this.lobby = new Client();
        this.cloudLobbyCount++;
    }

    public void startCloudLobby(String ip, String serverName, int port, String startShPath) {
        MCloud.getCloud().getLogger().info("The Cloud try to start CloudLobby!");
            int tcp = MCloud.getCloud().getJsonConfigBuilder().getInteger("tcp-port", 54555);
            int udp = MCloud.getCloud().getJsonConfigBuilder().getInteger("udp-port", 54777);
            this.kryo = this.lobby.getKryo();
            this.kryo.register(AuthPacket.class);
            this.kryo.register(AuthResponsePacket.class);
            this.lobby.addListener(new Listener() {
                @Override
                public void connected(Connection connection) {
                    AuthPacket proxyAuthPacket = new AuthPacket(ServerType.LOBBY.getName(), serverName, ip, port, startShPath);
                    connection.sendTCP(proxyAuthPacket);
                }
            });
            this.lobby.addListener(new Listener() {
                @Override
                public void received(Connection connection, Object object) {
                    if(object instanceof AuthResponsePacket responsePacket) {
                        MCloud.getCloud().getLogger().info("The CloudLobbyGroup has received " + responsePacket.getResponse());
                    }
                }
            });
            this.lobby.start();
            try {
                this.lobby.connect(5000, "127.0.0.1", tcp, udp);
                MCloud.getCloud().getLogger().info("The Cloud Lobby is successfully started!");
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public void stopCloudLobby() {
        this.lobby.close();
        this.cloudLobbyCount = 0;
        MCloud.getCloud().getLogger().info("The CloudServerManager is successfully stopped!");
    }
}
