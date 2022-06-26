package net.mcloud.api.cloudservermanager;

import lombok.Getter;
import net.mcloud.MCloud;
import net.mcloud.utils.json.JsonConfigBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CloudGroupLists {
    @Getter
    private List<Object> proxyGroups;
    @Getter
    private List<Object> lobbyGroups;
    @Getter
    private List<Object> serverGroups;

    public CloudGroupLists(JsonConfigBuilder jsonConfigBuilder) {

        if(jsonConfigBuilder.getList("PROXY_GROUPS") == null) {
            this.proxyGroups = new ArrayList<>();
        } else {
            this.proxyGroups = jsonConfigBuilder.getList("PROXY_GROUPS");
        }
        if(jsonConfigBuilder.getList("LOBBY_GROUPS") == null) {
            this.lobbyGroups = new ArrayList<>();
        } else {
            this.lobbyGroups = jsonConfigBuilder.getList("LOBBY_GROUPS");
        }
        if(jsonConfigBuilder.getList("SERVER_GROUPS") == null) {
            this.serverGroups = new ArrayList<>();
        } else {
            this.serverGroups = jsonConfigBuilder.getList("SERVER_GROUPS");
        }
    }

    public void saveConfig(JsonConfigBuilder jsonConfigBuilder) {
        MCloud.getCloud().getLogger().info("CloudGroupListSystem try to save the Group Lists");
        jsonConfigBuilder.setList("PROXY_GROUPS", getProxyGroups());
        MCloud.getCloud().getLogger().info("CloudGroupListSystem has successfully saved the ProxyGroup List in the Config");
        jsonConfigBuilder.setList("LOBBY_GROUPS", getLobbyGroups());
        MCloud.getCloud().getLogger().info("CloudGroupListSystem has successfully saved the LobbyGroup List in the Config");
        jsonConfigBuilder.setList("SERVER_GROUPS", getServerGroups());
        MCloud.getCloud().getLogger().info("CloudGroupListSystem has successfully saved the ServerGroup List in the Config");
    }

    private Timer configSaveTimer;

    public void startConfigSaveTimer() {
        this.configSaveTimer = new Timer();
        this.configSaveTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                saveConfig(MCloud.getCloud().getJsonConfigBuilder());
            }
        }, 0, 1800000);
    }

    public void stopConfigSaveTimer() {
        this.configSaveTimer.cancel();
    }
}
