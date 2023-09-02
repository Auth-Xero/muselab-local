package com.authxero.muselablocal.helpers;
import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileHelper {
    private static final String DATA_FOLDER = "data";

    public static String readFile(String filePath) throws IOException {
        byte[] encodedBytes = Files.readAllBytes(Paths.get(filePath));
        return new String(encodedBytes);
    }

    public static void writeFile(String filePath, String content) throws IOException {
        Path path = Paths.get(DATA_FOLDER, filePath);
        Files.write(path, content.getBytes());
    }

    public static void createFolder(String subfolder) throws IOException {
        Path path = Paths.get(DATA_FOLDER, subfolder);
        if (!Files.exists(path) || !Files.isDirectory(path)) {
            Files.createDirectories(path);
        }
    }

    public static List<String> listFilesInDataFolder() throws IOException {
        Path path = Paths.get(DATA_FOLDER);
        try (Stream<Path> walk = Files.walk(path)) {
            return walk
                    .filter(Files::isRegularFile)
                    .map(p -> DATA_FOLDER + File.separator + path.relativize(p).toString())
                    .collect(Collectors.toList());
        }
    }

    public static List<String> listFoldersInDataFolder() throws IOException {
        Path path = Paths.get(DATA_FOLDER);
        try (Stream<Path> walk = Files.walk(path)) {
            return walk
                    .filter(Files::isDirectory)
                    .map(p -> DATA_FOLDER + File.separator + path.relativize(p).toString())
                    .collect(Collectors.toList());
        }
    }
}