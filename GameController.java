package xoproject.player3_connector;

import xoproject.player1_gui.GameView;
import xoproject.player2_logic.GameLogic;
import xoproject.player4_network.NetworkHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class GameController {
    private GameView view;
    private GameLogic logic;
    private NetworkHandler network;
    private boolean isMyTurn = true;
    private String mySymbol = "X";
    private String opponentSymbol = "O";
    private boolean isNetworked = false;

    public GameController(GameView view, GameLogic logic) {
        this.view = view;
        this.logic = logic;
        initController();
    }

    public void setNetwork(NetworkHandler network, boolean amIHost) {
        this.network = network;
        isNetworked = true;

        if (amIHost) {
            mySymbol = "X";
            opponentSymbol = "O";
            isMyTurn = true;
        } else {
            mySymbol = "O";
            opponentSymbol = "X";
            isMyTurn = false;
        }

        network.setOnMoveReceivedListener((row, col) -> {
            SwingUtilities.invokeLater(() -> {
                if (logic.makeMove(row, col)) {
                    view.buttons[row][col].setText(opponentSymbol);
                    if (logic.checkWinner() != null) {
                        showWinner(logic.checkWinner());
                    } else {
                        logic.switchPlayer();
                        isMyTurn = true;
                    }
                }
            });
        });

       network.setOnResetReceivedListener((v) -> {
            SwingUtilities.invokeLater(() -> {
                logic.resetGame();
                resetButtons();
                isMyTurn = mySymbol.equals("X");
            });
        });
    }
 
    private void initController() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int row = i;
                int col = j;
                view.buttons[i][j].addActionListener(e -> {
                    if (isNetworked && !isMyTurn) return;

                    if (logic.makeMove(row, col)) {
                        String currentSymbol = isNetworked ? mySymbol : logic.getCurrentPlayer();
                        view.buttons[row][col].setText(currentSymbol);

                        if (isNetworked && network != null) {
                            network.sendMove(row, col);
                        }

                        if (logic.checkWinner() != null) {
                            showWinner(logic.checkWinner());
                        } else {
                            logic.switchPlayer();
                            if (isNetworked) isMyTurn = false;
                        }
                    }
                });
            }
        }

        view.resetButton.addActionListener((ActionEvent e) -> {
            logic.resetGame();
            resetButtons();
            if (isNetworked && network != null) {
                network.sendReset();
                isMyTurn = mySymbol.equals("X");
            }
        });
    }

    private void showWinner(String winner) {
    JOptionPane.showMessageDialog(view,
            winner.equals("Draw") ? "It's a Draw!" : "Player " + winner + " wins!");

    logic.resetGame();
    resetButtons();

    if (isNetworked) {
        network.sendReset();  
        isMyTurn = mySymbol.equals("X");
    }
}

    private void resetButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                view.buttons[i][j].setText("");
            }
        }
    }
}