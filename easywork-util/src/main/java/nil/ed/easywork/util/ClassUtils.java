package nil.ed.easywork.util;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Set;

/**
 * @author lidelin.
 */
public class ClassUtils {

    public static void loadAllClass(String basePackage, Visitor visitor) throws Exception {
        Path path = Paths.get(ClassUtils.class.getResource("/").getFile(),
                basePackage.replace(".", "/"));
        String basePath = path.toString();
        Set<String> clazzNameSet = new HashSet<>();
        Files.walkFileTree(path, new FileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                String f = file.toString();
                String suffix = f.substring(basePath.length() + 1, f.length() - ".class".length()).replace("/", ".");
                clazzNameSet.add(basePackage + "." + suffix);
                return  FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                return  FileVisitResult.CONTINUE;
            }
        });

            for (String clazz : clazzNameSet) {
                try {
                    Class<?> c = Class.forName(clazz);
                    visitor.visit(c);
                } catch (ClassNotFoundException e) {
                    System.out.println(clazz + " Not Found!");
                }
            }

    }

    /**
     * @author lidelin.
     */
    public interface Visitor {
        /**
         * 访问clazz.
         * @param clazz clazz.
         * @throws Exception 方法体中包含的异常.
         */
        void visit(Class<?> clazz) throws Exception;
    }
}
