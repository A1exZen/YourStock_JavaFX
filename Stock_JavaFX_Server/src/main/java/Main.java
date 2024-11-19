//import server.utils.Server;

import server.TCP.ClientThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final int PORT = 8080;
    private static ServerSocket serverSocket;
    private static List<Socket> currentSockets = new ArrayList<>();

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Сервер запущен на порту " + PORT);
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    System.out.println("Клиент подключен: " + socket.getInetAddress());
                    currentSockets.add(socket);
                    ClientThread clientThread = new ClientThread(socket);
                    Thread thread = new Thread(clientThread);
                    thread.start();
                } catch (IOException e) {
                    System.err.println("Ошибка при подключении клиента: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Не удалось запустить сервер: " + e.getMessage());
        } finally {
            if (serverSocket != null && !serverSocket.isClosed()) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    System.err.println("Не удалось закрыть серверный сокет: " + e.getMessage());
                }
            }
        }
    }
}