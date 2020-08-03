package softuni.WatchUSeek.utils.impl;

import softuni.WatchUSeek.utils.FileUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtilImpl implements FileUtil {
    @Override
    public void writeFile(String content, String path) throws IOException {
        File file = new File(path);
        FileWriter writer = new FileWriter(file);
        writer.write(content);
        writer.close();
    }
}