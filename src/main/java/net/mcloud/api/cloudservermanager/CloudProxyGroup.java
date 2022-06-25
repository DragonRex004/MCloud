package net.mcloud.api.cloudservermanager;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import lombok.Getter;
import net.mcloud.MCloud;
import net.mcloud.api.cloudservermanager.packets.ProxyAuthPacket;
import net.mcloud.api.cloudservermanager.packets.ProxyAuthResponsePacket;

import java.io.IOException;

public class CloudProxyGroup {
    @Getter
    private Client proxy;
    private final int maxCloudProxyCount = MCloud.getCloud().getJsonConfigBuilder().getInteger("max-cloud-proxy-count", 1);
    private int cloudProxyCount;
    private Kryo kryo;

    public void createCloudProxy() {
        this.proxy = new Client();
        this.cloudProxyCount = 1;
    }

    public void startCloudProxy() {
        MCloud.getCloud().getLogger().info("The Cloud try to start CloudProxy!");
        if(!(this.cloudProxyCount > this.maxCloudProxyCount)) {
            int tcp = MCloud.getCloud().getJsonConfigBuilder().getInteger("tcp-port", 54555);
            int udp = MCloud.getCloud().getJsonConfigBuilder().getInteger("udp-port", 54777);
            this.kryo = this.proxy.getKryo();
            this.kryo.register(ProxyAuthPacket.class);
            this.kryo.register(ProxyAuthResponsePacket.class);
            this.proxy.start();
            try {
                this.proxy.connect(5000, "127.0.0.1", tcp, udp);
                MCloud.getCloud().getLogger().info("The Cloud Proxy is successfully started!");
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.proxy.addListener(new Listener() {
                @Override
                public void connected(Connection connection) {
                    ProxyAuthPacket proxyAuthPacket = new ProxyAuthPacket("Proxy", proxy.getRemoteAddressTCP().getHostString(), proxy.getRemoteAddressTCP().getPort());
                    connection.sendTCP(proxyAuthPacket);
                }
            });
        } else {
            MCloud.getCloud().getLogger().error("You have already one Cloud Proxy Enabled!");
        }
    }

    public void stopCloudProxy() {
        this.proxy.close();
        this.cloudProxyCount = 0;
        MCloud.getCloud().getLogger().info("The CloudServerManager is successfully stopped!");
    }
}
