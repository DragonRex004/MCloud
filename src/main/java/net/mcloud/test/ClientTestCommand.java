package net.mcloud.test;

import net.mcloud.MCloud;
import net.mcloud.api.cloudservermanager.groupsmanager.CloudLobbyGroup;
import net.mcloud.api.cloudservermanager.groupsmanager.CloudProxyGroup;
import net.mcloud.api.cloudservermanager.groupsmanager.CloudServerGroup;
import net.mcloud.api.command.Command;
import net.mcloud.api.command.CommandResponse;
import org.jline.utils.AttributedString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientTestCommand extends Command {
    private CloudProxyGroup proxyGroup;
    private CloudLobbyGroup lobbyGroup;
    private CloudServerGroup serverGroup;

    @Override
    public CommandResponse execute(String command_name, ArrayList<String> args) {
        if (args.size() == 5) {
            switch (args.get(0)) {
                case "start-proxy" -> {
                    proxyGroup = new CloudProxyGroup();
                    proxyGroup.createCloudProxy();
                    proxyGroup.startCloudProxy(args.get(1), args.get(2), Integer.parseInt(args.get(3)), args.get(4));
                    return CommandResponse.SUCCESS;
                }
                case "stop-proxy" -> {
                    proxyGroup.stopCloudProxy();
                    return CommandResponse.SUCCESS;
                }
                case "start-lobby" -> {
                    lobbyGroup = new CloudLobbyGroup();
                    lobbyGroup.createCloudLobby();
                    lobbyGroup.startCloudLobby(args.get(1), args.get(2), Integer.parseInt(args.get(3)), args.get(4));
                    return CommandResponse.SUCCESS;
                }
                case "stop-lobby" -> {
                    lobbyGroup.stopCloudLobby();
                    return CommandResponse.SUCCESS;
                }
                case "start-server" -> {
                    serverGroup = new CloudServerGroup();
                    serverGroup.createCloudServer();
                    serverGroup.startCloudServer(args.get(1), args.get(2), Integer.parseInt(args.get(3)), args.get(4));
                    return CommandResponse.SUCCESS;
                }
                case "stop-server" -> {
                    serverGroup.stopCloudServer();
                    return CommandResponse.SUCCESS;
                }
            }
        } else {
            MCloud.getCloud().getLogger().warn("Please use test-client <start-(proxy, lobby, server)>/stop-(proxy, lobby, server)> <ip> <serverName> <port> <startShPath>");
            return CommandResponse.WARNING;
        }
        return CommandResponse.ERROR;
    }

    @Override
    public String usage() {
        return "This is a Client Test Command";
    }

    @Override
    public String name() {
        return "test-client";
    }

    @Override
    public Map<String, List<AttributedString>> widgetOpt() {
        Map<String, List<AttributedString>> map = new HashMap<>();

        map.put("stop-proxy", List.of(new AttributedString("Stops the running Proxy client"), new AttributedString("but crashes if the client is not running")));
        map.put("start-proxy", List.of(new AttributedString("Starts the running Proxy client"), new AttributedString("but crashes if the client is running")));
        map.put("stop-lobby", List.of(new AttributedString("Stops the running Lobby client"), new AttributedString("but crashes if the client is not running")));
        map.put("start-lobby", List.of(new AttributedString("Starts the running Lobby client"), new AttributedString("but crashes if the client is running")));
        map.put("stop-server", List.of(new AttributedString("Stops the running Server client"), new AttributedString("but crashes if the client is not running")));
        map.put("start-server", List.of(new AttributedString("Starts the running Server client"), new AttributedString("but crashes if the client is running")));

        return map;
    }

    @Override
    public List<AttributedString> desc() {
        return List.of(new AttributedString("test-client stop-proxy - Stops the Proxy client"),
                new AttributedString("test-client start-proxy - Starts the Proxy client"),
                new AttributedString("test-client stop-lobby - Stops the Lobby client"),
                new AttributedString("test-client start-lobby - Starts the Lobby client"),
                new AttributedString("test-client stop-server - Stops the Server client"),
                new AttributedString("test-client start-server - Starts the Server client"));
    }
}
