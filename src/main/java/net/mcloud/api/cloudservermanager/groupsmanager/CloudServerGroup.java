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

public class CloudServerGroup {
    @Getter
    private Client server;
    private int cloudServerCount = 0;
    private Kryo kryo;

    public void createCloudServer() {
        this.server = new Client();
        this.cloudServerCount++;
    }

    public void startCloudServer(String ip, String serverName, int port, String startShPath) {
        MCloud.getCloud().getLogger().info("The Cloud try to start CloudServer!");
            int tcp = MCloud.getCloud().getJsonConfigBuilder().getInteger("tcp-port", 54555);
            int udp = MCloud.getCloud().getJsonConfigBuilder().getInteger("udp-port", 54777);
            this.kryo = this.server.getKryo();
            this.kryo.register(AuthPacket.class);
            this.kryo.register(AuthResponsePacket.class);
            this.server.addListener(new Listener() {
                @Override
                public void connected(Connection connection) {
                    AuthPacket proxyAuthPacket = new AuthPacket(ServerType.SERVER.getName(), serverName, ip, port, startShPath);
                    connection.sendTCP(proxyAuthPacket);
                }
            });
            this.server.addListener(new Listener() {
                @Override
                public void received(Connection connection, Object object) {
                    if(object instanceof AuthResponsePacket responsePacket) {
                        MCloud.getCloud().getLogger().info("The CloudServerGroup has received " + responsePacket.getResponse());
                    }
                }
            });
            this.server.start();
            try {
                this.server.connect(5000, "127.0.0.1", tcp, udp);
                MCloud.getCloud().getLogger().info("The Cloud Server is successfully started!");
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public void stopCloudServer() {
        this.server.close();
        this.cloudServerCount = 0;
        MCloud.getCloud().getLogger().info("The CloudServerManager is successfully stopped!");
    }
}
