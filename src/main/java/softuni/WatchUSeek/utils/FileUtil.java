package softuni.WatchUSeek.utils;

import java.io.IOException;

public interface FileUtil {
    void writeFile(String content, String path) throws IOException;
}