package fr.ariloxe.arena.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.plugin.Plugin;

import com.google.common.io.ByteStreams;

public class FileUtils {

    public static File copy(Plugin pl, String fileName, File f) {
        try (InputStream in = pl.getResource(fileName); OutputStream out = new FileOutputStream(f)) {
            ByteStreams.copy(in, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }

    public static void copyFolder(File src, File dest) throws IOException {
        if (src.isDirectory()) {
            if (!dest.exists()) {
                dest.mkdir();
            }
            String files[] = src.list();
            for (String file : files) {
                File srcFile = new File(src, file);
                File destFile = new File(dest, file);
                FileUtils.copyFolder(srcFile, destFile);
            }
        } else {
            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            in.close();
            out.close();
        }
    }

    public static boolean delete(File path) {
        if (path.exists()) {
            File files[] = path.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    FileUtils.delete(file);
                } else {
                    file.delete();
                }
            }
        }
        return path.delete();
    }
}
