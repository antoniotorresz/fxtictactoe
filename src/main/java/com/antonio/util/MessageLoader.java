package com.antonio.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class MessageLoader {
    public static List<String> loadPhrasesFromFile() {
        try {
            return Files.readAllLines(Paths.get("src/main/resources/phrases.txt"));
        } catch (IOException e) {
            return null;
        }
    }
}
