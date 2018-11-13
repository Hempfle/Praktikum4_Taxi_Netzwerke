//Melanie Famao

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.

public class ControlhubServerAPI {

    private ServerSocket serverSocket;

    public ControlhubServerAPI(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            this.serverSocket = serverSocket;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ServerSocket getServerSocket() {
        return this.serverSocket;
    }

    public Socket openConnection() {
        try (Socket socket = this.serverSocket.accept()) {
            return socket;


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getTheResponse(String request) {
        return null;
    }

}
