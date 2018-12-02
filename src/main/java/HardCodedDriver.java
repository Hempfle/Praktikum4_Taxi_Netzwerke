import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

public class HardCodedDriver {

    Socket client;
    InetAddress serverhost;
    int port = 8005;


    //to change----------------------------------
    int taxi = 1;

    String city = "Berlin";
    String address = "Schlossstrasse";
    int number = 5;
    String status = "active";

    String endCity = "Bonn";
    String endAddress = "Hauptstrasse";
    int endNumber = 20;
    //---------------------------------------------

    public HardCodedDriver() {
        try {
            serverhost = InetAddress.getLocalHost();
            client = new Socket(serverhost, port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void main(String[] args) {
        String ressource = "/update/taxi";

        String json = "{\n" +
                "\"taxi\":";

        try {
            BufferedWriter postToServer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

            postToServer.write("POST " + ressource + " HTTP/1.0" + "\r\n");
            postToServer.write("Content-Length: " + json.length());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
