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
    String status = "active";

    String city = "Berlin";
    String address = "Schlossstrasse";
    int number = 5;

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
        String json;
        if (status.equals("active")) {
            json = "{\n" +
                    "\"taxi\": " + taxi + "\n" +
                    "\"status\": " + city + "\n" +
                    "\"city\": " + city + "\n" +
                    "\"address\": " + city + "\n" +
                    "\"number\": " + city + "\n" +
                    "\"endCity\": " + city + "\n" +
                    "\"endAddress\": " + city + "\n" +
                    "\"endNumber\": " + city + "\n" +
                    "}";
        } else {
            json = "{\n" +
                    "\"taxi\": " + taxi + "\n" +
                    "\"status\": " + city + "\n" +
                    "}";
        }


        try {
            BufferedWriter postToServer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

            postToServer.write("POST " + ressource + " HTTP/1.0" + "\r\n");
            postToServer.write("Content-Length: " + json.length());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
