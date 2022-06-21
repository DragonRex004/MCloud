package net.mcloud.utils.json;

public class CloudSettings {
    private JsonConfigBuilder settings;

    public CloudSettings(JsonConfigBuilder jsonConfigBuilder) {
        this.settings = jsonConfigBuilder;
    }

    public JsonSettingsResponseType setDefaultSettings() {
        settings.getBoolean("settings-deprecated-events", true);
        settings.getInteger("tcp-port-cloud-manager", 54555);
        settings.getInteger("udp-port-cloud-manager", 54777);
        settings.getBoolean("settings-edited" , false);
        return JsonSettingsResponseType.SUCCESS;
    }

    public JsonConfigBuilder getSettings() {
        return settings;
    }
}
