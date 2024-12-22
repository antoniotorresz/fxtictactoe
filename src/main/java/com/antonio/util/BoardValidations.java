package com.antonio.util;

/**
 * The BoardValidations class provides utility methods to validate the state of a Tic-Tac-Toe board.
 * It checks for winning conditions in rows, columns, and diagonals.
 * 
 * The board is represented as a 2D array of Integer where:
 * - 1 represents a move by player X
 * - -1 represents a move by player O
 * - null represents an empty cell
 * 
 * The class defines the following methods:
 * 
 * - {@link #validateRows(Integer[][])}: Checks each row for a winning condition.
 * - {@link #validateColumns(Integer[][])}: Checks each column for a winning condition.
 * - {@link #validateDiagonals(Integer[][])}: Checks both diagonals for a winning condition.
 * 
 * The methods return "X" if player X wins, "O" if player O wins, or null if there is no winner.
 */
public class BoardValidations {

    private static final Integer PLAYER_X_SUM_TO_WIN = 3;
    private static final Integer PLAYER_O_SUM_TO_WIN = -3;

    public static String validateRows(final Integer[][] board) {
        for (Integer[] row : board) {
            Integer sum = 0;
            for (Integer cell : row) {
                if (cell != null) {
                    sum += cell;
                }
            }
            if (sum.equals(PLAYER_X_SUM_TO_WIN)) {
                return "X";
            } else if (sum.equals(PLAYER_O_SUM_TO_WIN)) {
                return "O";
            }
        }
        return null;
    }

    public static String validateColumns(final Integer[][] board) {
        for (Integer i = 0; i < 3; i++) {
            Integer sum = 0;
            for (Integer j = 0; j < 3; j++) {
                if (board[j][i] != null) {
                    sum += board[j][i];
                }
            }
            if (sum.equals(PLAYER_X_SUM_TO_WIN)) {
                return "X";
            } else if (sum.equals(PLAYER_O_SUM_TO_WIN)) {
                return "O";
            }
        }
        return null;
    }

    public static String validateDiagonals(final Integer[][] board) {
        //left diagonal
        Integer sum = 0;
        for (Integer i = 0; i < 3; i++) {
            if (board[i][i] != null) {
                sum += board[i][i];
            }
            if (sum.equals(PLAYER_X_SUM_TO_WIN)) {
                return "X";
            } else if (sum.equals(PLAYER_O_SUM_TO_WIN)) {
                return "O";
            }
        }

        //right diagonal
        sum = 0;
        for (Integer i = 0; i < 3; i++) {
            if (board[i][2 - i] != null) {
                sum += board[i][2 - i];
            }
            if (sum.equals(PLAYER_X_SUM_TO_WIN)) {
                return "X";
            } else if (sum.equals(PLAYER_O_SUM_TO_WIN)) {
                return "O";
            }
        }
        return null;
    }

}
