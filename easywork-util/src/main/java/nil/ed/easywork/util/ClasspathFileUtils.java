package nil.ed.easywork.util;


import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ClasspathFileUtils {
    public static List<String> readText(String path) throws IOException {
        return IOUtils.readLines(getClasspathInputStream(path), StandardCharsets.UTF_8);
    }

    public static InputStream getClasspathInputStream(String path) {
        return ClasspathFileUtils.class.getResourceAsStream(path);
    }

    public static String getClassPath(String resource) {
        return ClasspathFileUtils.class.getResource(resource).getFile();
    }

}
