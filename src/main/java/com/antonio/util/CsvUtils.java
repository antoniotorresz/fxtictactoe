package com.antonio.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.antonio.model.Game;
import com.antonio.model.Player;

public class CsvUtils {
    public static void saveGameResults(Player playerX, Player playerO) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("src/main/resources/db/current_game.csv", false))) {
            StringBuilder sb = new StringBuilder();
            sb.append("name,role,score\n");
            sb.append(playerX.getName()).append(",").append(playerX.getRole()).append(",").append(playerX.getScore())
                    .append("\n");
            sb.append(playerO.getName()).append(",").append(playerO.getRole()).append(",").append(playerO.getScore())
                    .append("\n");
            writer.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Game getCurrentGame() {
        List<Player> players = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/db/current_game.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Player player = new Player(values[0], values[1], Integer.parseInt(values[2]));
                players.add(player);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if (players.size() == 2) {
            return new Game(players.get(0), players.get(1));
        } else {
            return null;
        }
    }
}
