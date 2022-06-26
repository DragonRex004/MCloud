package net.mcloud.api.cloudservermanager;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import net.mcloud.MCloud;
import net.mcloud.api.cloudservermanager.groups.LobbyGroup;
import net.mcloud.api.cloudservermanager.groups.ProxyGroup;
import net.mcloud.api.cloudservermanager.groups.ServerGroup;
import net.mcloud.api.cloudservermanager.groups.ServerType;
import net.mcloud.api.cloudservermanager.packets.AuthPacket;
import net.mcloud.api.cloudservermanager.packets.AuthResponsePacket;

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
            this.kryo.register(AuthPacket.class);
            this.kryo.register(AuthResponsePacket.class);
            this.cloudServer.start();
            int tcp = MCloud.getCloud().getJsonConfigBuilder().getInteger("tcp-port", 54555);
            int udp = MCloud.getCloud().getJsonConfigBuilder().getInteger("udp-port", 54777);
            this.cloudServer.addListener(new Listener() {
                @Override
                public void received(Connection connection, Object object) {
                    if(object instanceof AuthPacket packet) {
                        MCloud.getCloud().getLogger().info("The CloudServerManager has received " + packet.getServerType() + " " + packet.getServerName() + " " + packet.getIp() + ":" + packet.getPort());
                        switch (packet.getServerType()) {
                            case "Proxy" -> {
                                ProxyGroup proxyGroup = new ProxyGroup(ServerType.PROXY, packet.getIp(), packet.getPort(), packet.getStartShPath());
                                MCloud.getCloud().getCloudGroupLists().getProxyGroups().add(proxyGroup);
                            }
                            case "Lobby" -> {
                                LobbyGroup lobbyGroup = new LobbyGroup(ServerType.LOBBY, packet.getIp(), packet.getPort(), packet.getStartShPath());
                                MCloud.getCloud().getCloudGroupLists().getLobbyGroups().add(lobbyGroup);
                            }
                            case "Server" -> {
                                ServerGroup serverGroup = new ServerGroup(ServerType.SERVER, packet.getIp(), packet.getPort(), packet.getStartShPath());
                                MCloud.getCloud().getCloudGroupLists().getServerGroups().add(serverGroup);
                            }
                            default -> {
                                MCloud.getCloud().getLogger().error("The selected Server Type is not available!");
                            }
                        }
                        AuthResponsePacket responsePacket = new AuthResponsePacket("The AuthPacket is successfully listened!");
                        connection.sendTCP(responsePacket);
                    }
                }
            });
            try {
                this.cloudServer.bind(tcp, udp);
                MCloud.getCloud().getLogger().info("The CloudServerManager is successfully started!");
            } catch (IOException e) {
                e.printStackTrace();
            }
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
