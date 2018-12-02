import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

public class HardCodedDriver {

    private Socket client;
    private InetAddress serverhost;
    private int port = 8005;

    //to change----------------------------------
    private int taxi = 1;
    private String status = "active";

    private String city = "Berlin";
    private String address = "Schlossstrasse";
    private int number = 5;

    private String endCity = "Bonn";
    private String endAddress = "Hauptstrasse";
    private int endNumber = 20;
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
                    "\"status\": " + status + "\n" +
                    "\"city\": " + city + "\n" +
                    "\"address\": " + address + "\n" +
                    "\"number\": " + number + "\n" +
                    "\"endCity\": " + endCity + "\n" +
                    "\"endAddress\": " + endAddress + "\n" +
                    "\"endNumber\": " + endNumber + "\n" +
                    "}";
        } else {
            json = "{\n" +
                    "\"taxi\": " + taxi + "\n" +
                    "\"status\": " + status + "\n" +
                    "}";
        }


        try {
            BufferedWriter postToServer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

            postToServer.write("POST " + ressource + " HTTP/1.0" + "\r\n");
            postToServer.write("Content-Length: " + json.length() + "\r\n");
            postToServer.write("\r\n");
            postToServer.write(json);
            postToServer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
