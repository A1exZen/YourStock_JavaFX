package client.TCP;

import client.TCP.enums.RequestType;
import client.TCP.enums.ResponseType;
import client.models.UserDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.List;

@Setter
@Getter
public class ClientSocket {
    private static final ClientSocket INSTANCE = new ClientSocket();
    private UserDTO user;
    private static Socket socket;
    @Getter
    private BufferedReader in;
    @Getter
    private PrintWriter out;
    private static final Gson gson = new Gson();

    private ClientSocket() {
        try {
            socket = new Socket("localhost", 8080);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static ClientSocket getInstance() {
        return INSTANCE;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        ClientSocket.socket = socket;
    }
}

