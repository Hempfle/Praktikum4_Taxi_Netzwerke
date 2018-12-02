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

    boolean wantToClose = false;
    //---------------------------------------------

    public HardCodedDriver() {
        try {
            serverhost = InetAddress.getLocalHost();
            client = new Socket(serverhost, port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
            BufferedWriter postToServer = new BufferedWriter(new OutputStreamWriter(driver.client.getOutputStream()));

            postToServer.write("POST " + ressource + " HTTP/1.0" + "\r\n");
            postToServer.write("Content-Type: application/json\r\n");
            postToServer.write("Content-Length: " + json.length() + "\r\n");
            postToServer.write("\r\n");
            postToServer.write(json);
            postToServer.close();
            if (driver.wantToClose) {
                driver.client.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
