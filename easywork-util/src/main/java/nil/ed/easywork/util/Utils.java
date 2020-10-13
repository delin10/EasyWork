package nil.ed.easywork.util;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import org.apache.commons.io.IOUtils;

/**
 * @author delin10
 * @since 2020/6/1
 **/
public class Utils {

    public static String readText(String file, Charset charset) throws IOException {
        return readText(file, lines -> String.join(System.lineSeparator(), lines), charset);
    }

    public static String readText(String file, Function<List<String>, String> lineMapper, Charset charset) throws IOException {
        File f = new File(file);
        try (InputStream in = Files.newInputStream(Paths.get(file))) {
            List<String> lines = IOUtils.readLines(in, charset);
            return lineMapper.apply(lines);
        }
    }

    public static void listLines(String file, Consumer<String> consumer, Charset charset) throws IOException {
        File f = new File(file);
        try (InputStream in = Files.newInputStream(Paths.get(file))) {
            List<String> lines = IOUtils.readLines(in, charset);
            lines.forEach(consumer);
        }
    }

    public static void listFiles(String baseDir, Consumer<File> consumer) throws FileNotFoundException {
        listFiles(baseDir, f -> true, consumer);
    }

    public static void listFiles(String baseDir, FileFilter filter, Consumer<File> consumer) throws FileNotFoundException {
        File f = new File(baseDir);

        if (!f.exists()) {
            throw new FileNotFoundException(baseDir + "不存在");
        }

        if (f.isDirectory()) {
            File[] files = f.listFiles(filter);
            if (files != null) {
                Arrays.stream(files)
                        .forEach(consumer);
            }
        } else {
            if (filter.accept(f)) {
                consumer.accept(f);
            }
        }
    }

    public static boolean writeToFile(String basePath, String name, String text) {
        Path pth = Paths.get(basePath, name);

        Optional.ofNullable(pth.toFile().getParentFile())
                .filter(f -> !f.exists())
                .ifPresent(File::mkdirs);

        try (OutputStream out = Files.newOutputStream(pth, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            IOUtils.write(text, out, StandardCharsets.UTF_8);
            return true;
        } catch (IOException e) {
            throw new RuntimeException(MessageFormat.format("Failed to writeToFile! basePath = {}, name = {}, text = {}!{}", basePath, name, text, e));
        }
    }

}
