package com.antonio.util;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class SongLoader {
    public static List<String> loadSongsFromFile() {
        try {
            return Files.readAllLines(Paths.get("src/main/resources/winning-songs.txt"));
        } catch (Exception e) {
            return null;
        }
    }
}
