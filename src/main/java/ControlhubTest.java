
import org.json.simple.parser.JSONParser;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ControlhubTest {


    public static void main(String[] args) {
        String response = "";
        HttpsURLConnection conn;
        BufferedReader fromConnection;
        String routeURL = "https://route.api.here.com/routing/7.2/calculateroute.json?app_id=IcoZews0wmkTvlM45NiO&app_code=InPlGS-2hW44OdmkanRp8w&waypoint0=geo!52.5,13.4&waypoint1=geo!52.5,13.45&mode=fastest;car;traffic:disabled";

        try {
            conn = (HttpsURLConnection) new URL(routeURL).openConnection();
            System.out.println("Verbindung mit Kartendienst hergestellt.");
            fromConnection = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.ISO_8859_1));
            for (String line = fromConnection.readLine(); line != null; line = fromConnection.readLine()) {
                response += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getGeoCode(String city, String street, int houseNum) {
        String geocodeURL = "https://geocoder.api.here.com/6.2/geocode.json?app_id=IcoZews0wmkTvlM45NiO&app_code=InPlGS-2hW44OdmkanRp8w&searchtext="+city+"+"+street+"+"+houseNum;
        JSONParser parser = new JSONParser();
        int geoCode = -1;
        try {
            File response = new File("geocodeResponse.json");
            HttpsURLConnection geocodeCon = (HttpsURLConnection) new URL(geocodeURL).openConnection();
            BufferedReader fromGeocode = new BufferedReader(new InputStreamReader(geocodeCon.getInputStream(), StandardCharsets.ISO_8859_1));
            for (String line = fromGeocode.readLine(); line != null; line = fromGeocode.readLine()) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return geoCode;

    }

    public static void getEstimatedTime(int latitude, int longitude) {
        //TODO read JSON
    }


}
