package com.antonio.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.antonio.model.Player;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;

public class PrimaryController {

    private final Player playerX = new Player();
    private final Player playerO = new Player();
    private Player activePlayer;
    private final Integer playerXSumToWin = 3;
    private final Integer playerOSumToWin = -3;
    private final HashMap<String, Integer> playerValues = new HashMap<>(
            Map.of("X", 1, "O", -1)
    );

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
    public void initialize() {
        this.playerX.setName(getPlayerName("Player X"));
        this.playerX.setRole("X");
        this.playerO.setRole("O");
        this.playerO.setName(getPlayerName("Player O"));
        activePlayer = playerX;

        textField00.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleTextFieldClick);
        textField01.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleTextFieldClick);
        textField02.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleTextFieldClick);
        textField10.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleTextFieldClick);
        textField11.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleTextFieldClick);
        textField12.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleTextFieldClick);
        textField20.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleTextFieldClick);
        textField21.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleTextFieldClick);
        textField22.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleTextFieldClick);
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

        String winner = validateRows(board);
        if (winner == null) {
            winner = validateColumns(board);
        }
        if (winner == null) {
            winner = validateDiagonals(board);
        }

        if (winner != null) {
            if (winner.equals("X")) {
                this.playerX.setScore(this.playerX.getScore() + 1);
            } else if (winner.equals("O")) {
                this.playerO.setScore(this.playerO.getScore() + 1);
            }
            resetBoard();
        }
    }

    private String validateRows(final Integer[][] board) {
        for (Integer[] row : board) {
            Integer sum = 0;
            for (Integer cell : row) {
                if (cell != null) {
                    sum += cell;
                }
            }
            if (sum.equals(this.playerXSumToWin)) {
                return "X";
            } else if (sum.equals(this.playerOSumToWin)) {
                return "O";
            }
        }
        return null;
    }

    private String validateColumns(final Integer[][] board) {
        for (Integer i = 0; i < 3; i++) {
            Integer sum = 0;
            for (Integer j = 0; j < 3; j++) {
                if (board[j][i] != null) {
                    sum += board[j][i];
                }
            }
            if (sum.equals(this.playerXSumToWin)) {
                return "X";
            } else if (sum.equals(this.playerOSumToWin)) {
                return "O";
            }
        }
        return null;
    }

    private String validateDiagonals(final Integer[][] board) {
        //right diagonal
        Integer sum = 0;
        for (Integer i = 0; i < 3; i++) {
            if (board[i][i] != null) {
                sum += board[i][i];
            }
            if (sum.equals(this.playerXSumToWin)) {
                return "X";
            } else if (sum.equals(this.playerOSumToWin)) {
                return "O";
            }
        }

        //left diagonal
        sum = 0;
        for (Integer i = 0; i < 3; i++) {
            if (board[i][2 - i] != null) {
                sum += board[i][2 - i];
            }
            if (sum.equals(this.playerXSumToWin)) {
                return "X";
            } else if (sum.equals(this.playerOSumToWin)) {
                return "O";
            }
        }
        return null;
    }

}
