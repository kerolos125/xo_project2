package xoproject;

import xoproject.player1_gui.GameView;
import xoproject.player2_logic.GameLogic;
import xoproject.player3_connector.GameController;
import xoproject.player4_network.NetworkHandler;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        String[] options = {"Single Player", "Multiplayer - Host", "Multiplayer - Join"};
        int choice = JOptionPane.showOptionDialog(null, "Select Mode", "XO Game",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]);

        SwingUtilities.invokeLater(() -> {
            GameView view = new GameView();
            GameLogic logic = new GameLogic();
            GameController controller = new GameController(view, logic);

            if (choice == 1) { // Host
                try {
                    NetworkHandler network = new NetworkHandler();
                    network.startServer(5000);
                    controller.setNetwork(network, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (choice == 2) { 
                try {
                    String ip = JOptionPane.showInputDialog("Enter server IP:", "localhost");
                    NetworkHandler network = new NetworkHandler();
                    network.connectToServer(ip, 5000);
                    controller.setNetwork(network, false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
        });
    }
}