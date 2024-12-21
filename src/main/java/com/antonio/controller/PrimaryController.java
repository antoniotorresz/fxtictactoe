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
            validateBoard();
        }
    }

    private void validateBoard() {
        Integer[][] board = getboard();
        Integer row;
        for (Integer i = 0; i < 3; i++) {
            for (Integer j = 0; j < 3; j++) {

            }
        }
    }
}
