package net.mcloud.api.cloudservermanager.groups;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class ProxyGroup {
    private ServerType serverType;
    private String ip;
    private int port;
    private String startShPath;
}
