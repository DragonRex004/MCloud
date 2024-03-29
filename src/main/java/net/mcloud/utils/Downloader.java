/*
    --------------------------
    Project : MCloud
    Package : net.mcloud.utils
    Date 23.06.2022
    @author ShortException
    --------------------------
*/


package net.mcloud.utils;

import lombok.Getter;
import net.mcloud.MCloud;
import net.mcloud.utils.logger.ConsoleColor;
import net.mcloud.utils.logger.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

@Getter
public class Downloader {

    private final URL url;
    private final String fileName;

    public Downloader(URL url, String fileName) {
        this.url = url;
        this.fileName = fileName;
    }

    public void downloadFile() {
        Logger logger = MCloud.getCloud().getLogger();
        logger.info("Starting downloading file " + fileName, ConsoleColor.YELLOW);
        try (InputStream in = url.openStream()) {
            Path path = Path.of(fileName);
            Files.copy(in, path);
            logger.info("Download finished!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
