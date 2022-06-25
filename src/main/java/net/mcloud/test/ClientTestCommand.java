package net.mcloud.test;

import net.mcloud.MCloud;
import net.mcloud.api.cloudservermanager.CloudProxyGroup;
import net.mcloud.api.command.Command;
import net.mcloud.api.command.CommandResponse;
import org.jline.utils.AttributedString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientTestCommand extends Command {
    private CloudProxyGroup proxyGroup = new CloudProxyGroup();

    @Override
    public CommandResponse execute(String command_name, ArrayList<String> args) {
        if (args.size() == 1) {
            if (args.get(0).equalsIgnoreCase("start")) {
                proxyGroup.createCloudProxy();
                proxyGroup.startCloudProxy();
                return CommandResponse.SUCCESS;
            }
            if (args.get(0).equalsIgnoreCase("stop")) {
                proxyGroup.stopCloudProxy();
                return CommandResponse.SUCCESS;
            }
        } else {
            MCloud.getCloud().getLogger().warn("Please use test_client <start/stop>");
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

        map.put("stop", List.of(new AttributedString("Stops the running client"), new AttributedString("but crashes if the client is not running")));
        map.put("start", List.of(new AttributedString("Starts the running client"), new AttributedString("but crashes if the client is running")));

        return map;
    }

    @Override
    public List<AttributedString> desc() {
        return List.of(new AttributedString("test-client stop - Stops the client"),
                new AttributedString("test-client start - Starts the client"));
    }
}
