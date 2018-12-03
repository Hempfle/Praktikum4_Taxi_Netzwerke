//Melanie Famao, christopher Weber

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.*;
import java.time.Instant;

public class HardCodedDriver {

     int port = 8005;

     //status is active, pause, stop
    //to change----------------------------------
     private int taxi = 1;
    private String status = "pause";

    private String city = "Berlin";
    private String address = "Schlossstrasse";
    private int number = 5;

    private String endCity = "Bonn";
    private String endAddress = "Hauptstrasse";
    private int endNumber = 20;

    //---------------------------------------------


    public static void main(String[] args) {
        HardCodedDriver driver = new HardCodedDriver();
        String ressource = "/update/taxi";
        String json;
        if (driver.status.equals("active")) {
            json = "{\n" +
                    "\"taxi\": " + driver.taxi + "\n" +
                    "\"status\": " + driver.status + "\n" +
                    "\"city\": " + driver.city + "\n" +
                    "\"address\": " + driver.address + "\n" +
                    "\"number\": " + driver.number + "\n" +
                    "\"endCity\": " + driver.endCity + "\n" +
                    "\"endAddress\": " + driver.endAddress + "\n" +
                    "\"endNumber\": " + driver.endNumber + "\n" +
                    "}";
        } else {
            json = "{\n" +
                    "\"taxi\": " + driver.taxi + "\n" +
                    "\"status\": " + driver.status + "\n" +
                    "}";
        }

        try {
            Socket client = new Socket(InetAddress.getLocalHost(), 8005);


            String jsonL = "" + json.length();
            BufferedWriter postToServer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));


            postToServer.write("Content-Type: application/json\r\n");
            postToServer.write("Content-Length: " + json.length() + "\r\n");
            postToServer.write("\r\n");
            postToServer.write(json);
            postToServer.flush();
            postToServer.close();
            client.close();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
