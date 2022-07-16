package fr.ariloxe.arena.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author Blendman974
 */
public class ZipUtils {
    public static void unzip(Path source, Path targetDir) throws IOException {
        try (FileInputStream is = new FileInputStream(source.toFile())) {
            targetDir = targetDir.toAbsolutePath();
            try (ZipInputStream zipIn = new ZipInputStream(is)) {
                for (ZipEntry ze; (ze = zipIn.getNextEntry()) != null; ) {
                    Path resolvedPath = targetDir.resolve(ze.getName()).normalize();
//                    if (!resolvedPath.startsWith(targetDir)) {
//                        throw new RuntimeException("Entry with an illegal path: " + ze.getName());
//                    }
                    if (ze.isDirectory()) {
                        Files.createDirectories(resolvedPath);
                    } else {
                        Files.createDirectories(resolvedPath.getParent());
                        Files.copy(zipIn, resolvedPath);
                    }
                }
            }
        }
    }
}
