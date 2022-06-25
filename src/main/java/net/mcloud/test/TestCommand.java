package net.mcloud.test;

import net.mcloud.api.command.Command;
import net.mcloud.api.command.CommandResponse;
import net.mcloud.utils.Downloader;
import org.jline.utils.AttributedString;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestCommand extends Command {
    @Override
    public String name() {
        return "test";
    }

    @Override
    public Map<String, List<AttributedString>> widgetOpt() {
        return new HashMap<>();
    }

    @Override
    public List<AttributedString> desc() {
        return List.of(new AttributedString("only for test like checking if Downloader.java works!"));
    }

    @Override
    public CommandResponse execute(String command_name, ArrayList<String> args) {
        logger().info("Download started!");
        try {
            new Downloader(new URL("https://workupload.com/file/a3dY3Lnacec"), "cloudsettings/video.mp4").downloadFile();
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
