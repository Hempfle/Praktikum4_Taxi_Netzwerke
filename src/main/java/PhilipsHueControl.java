//Melanie Famao

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class PhilipsHueControl {

    private static final int COLOR_RED = 0;
    private static final int COLOR_ORANGE = 0;
    private static final int COLOR_GREEN = 0;
    private static final int COLOR_OUT = 0;

    //private static final String AUTH_USER = "2217334838210e7f244460f83b42026f";
    private static final String AUTH_USER = "newdeveloper";
    //private static final String LAMP_IP = "10.28.9.122";
    private static final String LAMP_IP = "localhost";

    HttpURLConnection conToLamps;
    BufferedReader fromCon;
    BufferedWriter toCon;


    public PhilipsHueControl() {
        try {
            String url = "http://" + LAMP_IP + "/api/" + AUTH_USER + "/lights/";
            this.conToLamps = (HttpURLConnection) new URL(url).openConnection();
            //fromCon = new BufferedReader(new InputStreamReader(conToLamps.getInputStream()));
            //toCon = new BufferedWriter(new OutputStreamWriter(conToLamps.getOutputStream(), StandardCharsets.ISO_8859_1));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //lampnumber can be 1,2,3
    public int getColor(int lampNr)  {
        String url = "http://" + LAMP_IP + "/api/" + AUTH_USER + "/lights/" + lampNr;
        try {
            this.conToLamps = (HttpURLConnection) new URL(url).openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int colorValue = Controlhub.getJsonObjectFromUrl(url).getJsonObject("state").getInt("hue");
        return colorValue;
    }

    public void setColor(int lampNr, DriverStatus status) {
        int newColorValue = 0;
        boolean lampOn = false;
        String lampEffects = "none";

        if (status == DriverStatus.INACTIVE) {
            newColorValue = 65136;
            lampOn = false;
            lampEffects = "none";
        } else if (status == DriverStatus.LATE) {
            newColorValue = 65136;
            lampOn = true;
            lampEffects = "select";
        } else if (status == DriverStatus.ONTIME) {
            newColorValue = 4444;
            lampOn = true;
            lampEffects = "none";
        } else if (status == DriverStatus.WAITING) {
            newColorValue = 23536;
            lampOn = true;
            lampEffects = "none";
        }

        String url = "http://" + LAMP_IP + "/api/" + AUTH_USER + "/lights/" + lampNr + "/state";
        url = "http://localhost/api/newdeveloper/lights/3/state";
        try {
            this.conToLamps = (HttpURLConnection) new URL(url).openConnection();
            conToLamps.setDoOutput(true);
            conToLamps.setRequestMethod("POST");
            conToLamps.setRequestProperty("Content-Type", "application/json; charset=UTF-8");


            String response = "";

            OutputStreamWriter osw = new OutputStreamWriter(conToLamps.getOutputStream());
            BufferedReader fromClient;

            String json = "{ \"hue\":" + newColorValue + ", \"on\":" + lampOn + " }";
            json = "{\n\t\"hue\": 25500,\n\t\"on\": true\n}";
            System.out.println(json);
            osw.write(json);

            fromClient = new BufferedReader(new InputStreamReader(conToLamps.getInputStream()));
            for (String line = fromClient.readLine(); line != null && line.length() > 0; line = fromClient.readLine()) {
                response += line;
            }
            System.out.println(response);
            osw.flush();
            osw.close();

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
