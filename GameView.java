package xoproject.player1_gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameView extends JFrame {
    public JButton[][] buttons = new JButton[3][3];
    public JButton resetButton = new JButton("Reset");

    public GameView() {
        setTitle("XO Game");
        setSize(400, 480);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        
        getContentPane().setBackground(new Color(235, 230, 255));

        JPanel gridPanel = new JPanel(new GridLayout(3, 3));
        gridPanel.setBackground(new Color(235, 230, 255)); 

        Font btnFont = new Font("Arial", Font.BOLD, 40);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton btn = new JButton("");
                btn.setFont(btnFont);
                btn.setFocusPainted(false);
                btn.setBackground(Color.WHITE);
                btn.setOpaque(true);
                btn.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 255), 3));

                
                btn.addMouseListener(new MouseAdapter() {
                    Color original = btn.getBackground();

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        if (btn.getText().equals("")) {
                            btn.setBackground(new Color(220, 220, 220));
                        }
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        if (btn.getText().equals("")) {
                            btn.setBackground(original);
                        }
                    }
                });

                
                btn.addActionListener(e -> {
                    if (btn.getText().equals("X")) {
                        btn.setForeground(Color.BLACK);
                    } else if (btn.getText().equals("O")) {
                        btn.setForeground(new Color(0, 0, 139)); 
                    }
                });

                buttons[i][j] = btn;
                gridPanel.add(btn);
            }
        }

        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(235, 230, 255));

        resetButton.setBackground(new Color(200, 190, 255));
        resetButton.setForeground(Color.BLACK);
        resetButton.setFocusPainted(false);
        resetButton.setFont(new Font("Arial", Font.BOLD, 16));
        resetButton.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 200), 1));
        resetButton.setPreferredSize(new Dimension(200, 40)); 

        bottomPanel.add(resetButton);

        add(gridPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        setVisible(true);
    }
}