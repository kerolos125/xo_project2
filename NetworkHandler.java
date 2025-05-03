package xoproject.player4_network;

import java.io.*;
import java.net.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class NetworkHandler {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private BiConsumer<Integer, Integer> moveListener;
    private Consumer<Void> resetListener;

    public void setOnMoveReceivedListener(BiConsumer<Integer, Integer> listener) {
        this.moveListener = listener;
    }

    public void setOnResetReceivedListener(Consumer<Void> listener) {
        this.resetListener = listener;
    }

    public void startServer(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        socket = serverSocket.accept();
        setupStreams();
    }

    public void connectToServer(String host, int port) throws IOException {
        socket = new Socket(host, port);
        setupStreams();
    }

    private void setupStreams() throws IOException {
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        new Thread(() -> {
            String line;
            try {
                while ((line = in.readLine()) != null) {
                    if (line.equals("RESET")) {
                        if (resetListener != null) {
                            resetListener.accept(null);
                        }
                    } else {
                        String[] parts = line.split(",");
                        int row = Integer.parseInt(parts[0]);
                        int col = Integer.parseInt(parts[1]);
                        if (moveListener != null) {
                            moveListener.accept(row, col);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void sendMove(int row, int col) {
        if (out != null) {
            out.println(row + "," + col);
        }
    }

    public void sendReset() {
        if (out != null) {
            out.println("RESET");
        }
    }
}