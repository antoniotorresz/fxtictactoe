package com.antonio.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.antonio.App;
import com.antonio.model.Player;
import com.antonio.repository.PlayerRepository;
import com.antonio.util.BoardValidations;
import com.antonio.util.CsvUtils;
import com.antonio.util.MessageLoader;
import com.antonio.util.SongLoader;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;

public class PrimaryController {

    private Player playerX;
    private Player playerO;
    private Player activePlayer;
    private static Boolean playersInitialized = false;
    private final HashMap<String, Integer> playerValues = new HashMap<>(
            Map.of("X", 1, "O", -1));
    private final List<String> phrases = MessageLoader.loadPhrasesFromFile();
    private final List<String> songs = SongLoader.loadSongsFromFile();

    @FXML
    private TextField textField00;
    @FXML
    private TextField textField01;
    @FXML
    private TextField textField02;
    @FXML
    private TextField textField10;
    @FXML
    private TextField textField11;
    @FXML
    private TextField textField12;
    @FXML
    private TextField textField20;
    @FXML
    private TextField textField21;
    @FXML
    private TextField textField22;
    @FXML
    private Label labelTurn;
    @FXML
    private TableView<Player> leaderBoardTableView;
    @FXML
    private TableColumn<Player, Integer> winsTableColumn;
    @FXML
    private TableColumn<Player, String> playerNameTableColumn;
    @FXML
    private Button clickToLeaderBoardBtn;
    private AudioClip audioClip;

    @FXML
    public void initialize() throws SQLException {
        if (!playersInitialized) {
            initializePlayers();
            playersInitialized = true;
        }

        // Player initialization
        this.activePlayer = this.playerX;
        this.labelTurn.setText("Turn: " + activePlayer.getName() + " (" + activePlayer.getRole() + ")");

        // Leader board initialization
        winsTableColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        playerNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        updateTable();

        // Board initialization
        textField00.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleTextFieldClick);
        textField01.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleTextFieldClick);
        textField02.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleTextFieldClick);
        textField10.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleTextFieldClick);
        textField11.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleTextFieldClick);
        textField12.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleTextFieldClick);
        textField20.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleTextFieldClick);
        textField21.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleTextFieldClick);
        textField22.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleTextFieldClick);

        // Leader board button
        clickToLeaderBoardBtn.setOnAction(event -> {
            switchToLeaderBoard(event);
        });
    }

    @FXML
    private void switchToLeaderBoard(final ActionEvent event) {
        App.openNewScene("leaderboard");
    }

    private void initializePlayers() throws SQLException {
        this.playerX = createPlayer("Player X", "X");
        this.playerO = createPlayer("Player O", "O");

        while (this.playerX.getName().equals(this.playerO.getName())) {
            showAlert("Repeated Player Names", "Player names must be different. Please enter different names.");
            this.playerX.setName(getPlayerName("Player X"));
            this.playerO.setName(getPlayerName("Player O"));
        }

        this.playerX = PlayerRepository.selectPlayer(playerX);
        this.playerO = PlayerRepository.selectPlayer(playerO);
        resetPlayerScores();
    }

    private Player createPlayer(String defaultName, String role) {
        Player player = new Player();
        player.setName(getPlayerName(defaultName));
        player.setRole(role);
        return player;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void resetPlayerScores() {
        this.playerX.setScore(0);
        this.playerO.setScore(0);
    }

    private void writeCurrentGame() {
        CsvUtils.saveGameResults(this.playerX, this.playerO);
    }

    private void updateTable() {
        ObservableList<Player> leaderBoard = FXCollections.observableArrayList(
                this.playerX,
                this.playerO);
        FXCollections.sort(leaderBoard, (p1, p2) -> p2.getScore().compareTo(p1.getScore()));
        leaderBoardTableView.setItems(leaderBoard);
        leaderBoardTableView.refresh();
    }

    private String getPlayerName(final String defaultName) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(defaultName + " Name");
        dialog.setHeaderText("Enter " + defaultName + " name");
        dialog.setContentText("Name:");

        Optional<String> name = dialog.showAndWait();
        return name.orElse(defaultName);
    }

    private Integer mapPlayerValue(final String role) {
        return this.playerValues.get(role);
    }

    public Integer[][] getboard() {
        Integer[][] board = new Integer[3][3];

        board[0][0] = mapPlayerValue(textField00.getText());
        board[0][1] = mapPlayerValue(textField01.getText());
        board[0][2] = mapPlayerValue(textField02.getText());
        board[1][0] = mapPlayerValue(textField10.getText());
        board[1][1] = mapPlayerValue(textField11.getText());
        board[1][2] = mapPlayerValue(textField12.getText());
        board[2][0] = mapPlayerValue(textField20.getText());
        board[2][1] = mapPlayerValue(textField21.getText());
        board[2][2] = mapPlayerValue(textField22.getText());

        return board;
    }

    private void handleTextFieldClick(MouseEvent event) {
        TextField source = (TextField) event.getSource();
        if (source.getText().isEmpty()) {
            source.setText(activePlayer.getRole());
            activePlayer = activePlayer == playerX ? playerO : playerX;
            this.labelTurn.setText("Turn: " + activePlayer.getName() + " (" + activePlayer.getRole() + ")");
            source.setDisable(true);
            checkIfWinner();
        }
    }

    private void resetBoard() {
        textField00.setText("");
        textField01.setText("");
        textField02.setText("");
        textField10.setText("");
        textField11.setText("");
        textField12.setText("");
        textField20.setText("");
        textField21.setText("");
        textField22.setText("");

        textField00.setDisable(false);
        textField01.setDisable(false);
        textField02.setDisable(false);
        textField10.setDisable(false);
        textField11.setDisable(false);
        textField12.setDisable(false);
        textField20.setDisable(false);
        textField21.setDisable(false);
        textField22.setDisable(false);
    }

    private void checkIfWinner() {
        Integer[][] board = getboard();
        String winner = BoardValidations.validateRows(board);
        Player winnerPlayer = null;

        if (winner == null) {
            winner = BoardValidations.validateColumns(board);
        }
        if (winner == null) {
            winner = BoardValidations.validateDiagonals(board);
        }

        if (winner != null) {
            if (winner.equals("X")) {
                this.playerX.setScore(this.playerX.getScore() + 1);
                winnerPlayer = this.playerX;
            } else if (winner.equals("O")) {
                this.playerO.setScore(this.playerO.getScore() + 1);
                winnerPlayer = this.playerO;
            }
            resetBoard();
            showWinnerDialog(winnerPlayer);
            PlayerRepository.saveWinner(winnerPlayer);
            updateTable();
        }
    }

    private void showWinnerDialog(Player winnerPlayer) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("The winner is " + winnerPlayer.getName());
        dialog.setContentText(winnerPlayer.getName() + " says: " + this.getRandomWinningPhrase());
        dialog.getDialogPane().getButtonTypes().addAll(javafx.scene.control.ButtonType.OK);
        reproduceSong();
        dialog.showAndWait();
        this.audioClip.stop();
    }

    private String getRandomWinningPhrase() {
        if (this.phrases != null) {
            return this.phrases.get((int) (Math.random() * this.phrases.size()));
        }
        return "Good game!";
    }

    private String getRandomSong() {
        if (this.songs != null) {
            return this.songs.get((int) (Math.random() * this.songs.size()));
        }
        return null;
    }

    private void reproduceSong() {
        String songFileName = this.getRandomSong();

        if (songFileName == null) {
            System.err.println("No song available to play.");
            return;
        }

        String resourcePath = "/com/antonio/songs/" + songFileName + ".wav";
        URL resource = getClass().getResource(resourcePath);

        if (resource == null) {
            System.err.println("File not found: " + resourcePath);
            return;
        }

        try {
            this.audioClip = new AudioClip(resource.toExternalForm());
            this.audioClip.setCycleCount(AudioClip.INDEFINITE);
            this.audioClip.play();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error playing media: " + e.getMessage());
        }
    }
}
