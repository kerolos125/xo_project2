package xoproject.player2_logic;

public class GameLogic {
    private String[][] board;
    private String currentPlayer;

    public GameLogic() {
        board = new String[3][3];
        resetGame();
    }

    public void resetGame() {
        currentPlayer = "X";
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = "";
            }
        }
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean makeMove(int row, int col) {
        if (board[row][col].equals("")) {
            board[row][col] = currentPlayer;
            return true;
        }
        return false;
    }

    public void switchPlayer() {
        currentPlayer = currentPlayer.equals("X") ? "O" : "X";
    }

    public String checkWinner() {
        
        for (int i = 0; i < 3; i++) {
            if (!board[i][0].equals("") &&
                board[i][0].equals(board[i][1]) &&
                board[i][0].equals(board[i][2])) {
                return board[i][0];
            }

            if (!board[0][i].equals("") &&
                board[0][i].equals(board[1][i]) &&
                board[0][i].equals(board[2][i])) {
                return board[0][i];
            }
        }

        
        if (!board[0][0].equals("") &&
            board[0][0].equals(board[1][1]) &&
            board[0][0].equals(board[2][2])) {
            return board[0][0];
        }

        if (!board[0][2].equals("") &&
            board[0][2].equals(board[1][1]) &&
            board[0][2].equals(board[2][0])) {
            return board[0][2];
        }

        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].equals("")) {
                    return null; 
                }
            }
        }

        return "Draw";
    }
}
