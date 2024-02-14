package app;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {

    private static final int PORT = 9002;
    public static Map<String, ClientHandler> clients = new HashMap<>();

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server is running on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // add client and handler to the clients map
    public static void addClient(String username, ClientHandler clientHandler) {
        clients.put(username, clientHandler);
    }

    // send message to relevant client
    public static void sendMessage(String sender, String receiver, String message) {
        // get handler object from clients map
        ClientHandler clientHandler = clients.get(receiver);
        if (clientHandler != null) {
            // pass message to receiving client
            clientHandler.sendMessage(sender + ": " + message);
        } else {
            System.out.println("User '" + receiver + "' not found.");
        }
    }
}
