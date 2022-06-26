package net.mcloud.api.cloudservermanager.groups;

public enum ServerType {
    PROXY("Proxy"),
    LOBBY("Lobby"),
    SERVER("Server");

    private String name;

    ServerType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
