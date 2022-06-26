package net.mcloud.api.cloudservermanager;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import net.mcloud.MCloud;
import net.mcloud.api.cloudservermanager.groups.ServerGroup;
import net.mcloud.api.cloudservermanager.packets.ProxyAuthPacket;
import net.mcloud.api.cloudservermanager.packets.ProxyAuthResponsePacket;

import java.io.IOException;

public class CloudServerManager {
    private Server cloudServer;
    private int cloudServerCount;
    private Kryo kryo;

    public void createCloudServer() {
        this.cloudServer = new Server();
        this.cloudServerCount = 1;
    }

    public void startCloudServer() {

        final int maxCloudServerCount = 1;
        if(!(this.cloudServerCount > maxCloudServerCount)) {
            this.kryo = this.cloudServer.getKryo();
            this.kryo.register(ProxyAuthPacket.class);
            this.kryo.register(ProxyAuthResponsePacket.class);
            this.cloudServer.start();
            int tcp = MCloud.getCloud().getJsonConfigBuilder().getInteger("tcp-port", 54555);
            int udp = MCloud.getCloud().getJsonConfigBuilder().getInteger("udp-port", 54777);
            try {
                this.cloudServer.bind(tcp, udp);
                MCloud.getCloud().getLogger().info("The CloudServerManager is successfully started!");
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.cloudServer.addListener(new Listener() {
                @Override
                public void received(Connection connection, Object object) {
                    if(object instanceof ProxyAuthPacket) {
                        ProxyAuthPacket packet = (ProxyAuthPacket) object;

                        ProxyAuthResponsePacket responsePacket = new ProxyAuthResponsePacket("The ProxyAuthPacket is successfully listened!");
                        connection.sendTCP(responsePacket);
                    }
                }
            });
        } else {
            MCloud.getCloud().getLogger().error("You have already one Cloud Server Enabled!");
        }
    }

    public void stopCloudServer() {
        this.cloudServer.close();
        this.cloudServerCount = 0;
        MCloud.getCloud().getLogger().info("The CloudServerManager is successfully stopped!");
    }
}
