//Melanie Famao

import java.io.*;
import java.net.Socket;
import

public class Controlhub {

    private static final int PORT = 8005;
    private ControlhubServerAPI server;

    public Controlhub() {
        this.server = new ControlhubServerAPI(PORT);
    }

    public ControlhubServerAPI getServer() {
        return this.server;
    }




    public static void main(String[] args) {
        try {
            Controlhub hub = new Controlhub();
            ControlhubServerAPI server = hub.getServer();
            PhilipsHueControl hueAPI = new PhilipsHueControl();

            Socket taxiClient = server.openConnection();

            BufferedReader taxiReader = new BufferedReader(new InputStreamReader(taxiClient.getInputStream()));
            BufferedWriter taxiWriter = new BufferedWriter(new OutputStreamWriter(taxiClient.getOutputStream()));


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
