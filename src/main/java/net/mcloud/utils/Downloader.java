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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

@Getter
public class Downloader {

    private final URL url;
    private final String fileName;
    private final String filePath;

    public Downloader(URL url, String fileName, String filePath) {
        this.url = url;
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public String downloadFile(){
        InputStream in = null;
        try {
            in = url.openStream();
            Files.copy(in, Path.of(filePath + "/" + fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return filePath + "/" + fileName;
    }
}
