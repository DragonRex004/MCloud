package net.mcloud.test;

import jdk.jfr.consumer.RecordedThread;
import net.mcloud.MCloud;
import net.mcloud.api.cloudservermanager.CloudProxyGroup;
import net.mcloud.api.command.Command;
import net.mcloud.api.command.CommandResponse;

import java.util.ArrayList;

public class ClientTestCommand extends Command {
    private CloudProxyGroup proxyGroup = new CloudProxyGroup();

    @Override
    public CommandResponse execute(String command_name, ArrayList<String> args) {
        if(args.size() == 1) {
            if(args.get(0).equalsIgnoreCase("start")) {
                proxyGroup.createCloudProxy();
                proxyGroup.startCloudProxy();
                return CommandResponse.SUCCESS;
            }
            if(args.get(0).equalsIgnoreCase("stop")) {
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
}
