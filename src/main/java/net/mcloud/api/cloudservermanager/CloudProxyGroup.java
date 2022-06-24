package net.mcloud.api.cloudservermanager;

import com.esotericsoftware.kryonet.Client;
import net.mcloud.MCloud;

import java.io.IOException;

public class CloudProxyGroup {
    private Client proxy;
    private final int maxCloudProxyCount = MCloud.getCloud().getJsonConfigBuilder().getInteger("max-cloud-proxy-count", 1);
    private int cloudProxyCount;

    public void createCloudProxy() {
        this.proxy = new Client();
        this.cloudProxyCount = 1;
    }

    public void startCloudProxy() {
        MCloud.getCloud().getLogger().info("The Cloud try to start CloudProxy!");
        if(!(this.cloudProxyCount > this.maxCloudProxyCount)) {
            int tcp = MCloud.getCloud().getJsonConfigBuilder().getInteger("tcp-port", 54555);
            int udp = MCloud.getCloud().getJsonConfigBuilder().getInteger("udp-port", 54777);
            this.proxy.start();
            try {
                this.proxy.connect(5000, "127.0.0.1", tcp, udp);
                MCloud.getCloud().getLogger().info("The Cloud Proxy is successfully started!");
            } catch (IOException e) {
                e.printStackTrace();
            }
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
