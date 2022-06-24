package net.mcloud.test;

import net.mcloud.api.command.Command;
import net.mcloud.api.command.CommandResponse;
import net.mcloud.utils.Downloader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class TestCommand extends Command {
    @Override
    public CommandResponse execute(String command_name, ArrayList<String> args) {
        logger().info("Download started!");
        try {
            new Downloader(new URL("https://workupload.com/file/a3dY3Lnacec"), "cloudsettings/").downloadFile();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return CommandResponse.SUCCESS;
    }

    @Override
    public String usage() {
        return "only for testing stuff";
    }
}
