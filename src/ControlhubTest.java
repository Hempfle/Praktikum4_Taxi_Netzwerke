import sun.net.www.http.HttpClient;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ControlhubTest {


    public static void main(String[] args) {
        String response = "";
        HttpsURLConnection conn;
        BufferedReader fromConnection;
        String targetUrl = "https://route.api.here.com/routing/7.2/calculateroute.json?app_id=IcoZews0wmkTvlM45NiO&app_code=InPlGS-2hW44OdmkanRp8w&waypoint0=geo!52.5,13.4&waypoint1=geo!52.5,13.45&mode=fastest;car;traffic:disabled";

        try {
            conn = (HttpsURLConnection) new URL(targetUrl).openConnection();
            fromConnection = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.ISO_8859_1));
            for (String line = fromConnection.readLine(); line != null; line = fromConnection.readLine()) {
                response += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(response);
    }


}
