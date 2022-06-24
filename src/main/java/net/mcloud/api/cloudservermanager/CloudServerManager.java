package net.mcloud.api.cloudservermanager;

import com.esotericsoftware.kryonet.Server;
import net.mcloud.MCloud;

import java.io.IOException;

public class CloudServerManager {
    private Server cloudServer;
    private final int maxCloudServerCount = 1;
    private int cloudServerCount;

    public void createCloudServer() {
        this.cloudServer = new Server();
        this.cloudServerCount = 1;
    }

    public void startCloudServer() {

        if(!(this.cloudServerCount > this.maxCloudServerCount)) {
            this.cloudServer.start();
            int tcp = MCloud.getCloud().getJsonConfigBuilder().getInteger("tcp-port", 54555);
            int udp = MCloud.getCloud().getJsonConfigBuilder().getInteger("udp-port", 54777);
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
