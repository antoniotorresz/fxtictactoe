package com.antonio;

import java.io.IOException;
import java.sql.SQLException;

import com.antonio.repository.PlayerRepository;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage primaryStage) throws IOException {
        scene = new Scene(loadFXML("primary"));
        primaryStage.setScene(scene);
        primaryStage.setWidth(451);
        primaryStage.setHeight(370);
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(eh -> {
            saveGameResults();
            System.exit(0);
        });
        primaryStage.show();
    }

    private void saveGameResults() {
        try {
            PlayerRepository.saveGameResults();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void openNewScene(String fxml) {
        try {
            Scene newScene = new Scene(loadFXML(fxml));
            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.setWidth(451);
            newStage.setHeight(370);
            newStage.setResizable(false);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}