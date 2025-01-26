package com.antonio.controller;

import java.sql.SQLException;

import com.antonio.model.Player;
import com.antonio.repository.PlayerRepository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class LeaderBoardController {
    @FXML
    private TableColumn<Player, Integer> winsTableColumn;
    @FXML
    private TableColumn<Player, String> playerNameTableColumn;
    @FXML
    private TableView<Player> leaderBoardTableView;

    @FXML
    public void initialize() throws SQLException {
        winsTableColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        playerNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        updateTable();
    }

    public void updateTable() {
        try {
            ObservableList<Player> leaderBoard = FXCollections.observableArrayList(
                    PlayerRepository.getLeaderBoard());
            FXCollections.sort(leaderBoard, (p1, p2) -> p2.getScore().compareTo(p1.getScore()));
            leaderBoardTableView.setItems(leaderBoard);
            leaderBoardTableView.refresh();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
