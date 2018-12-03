//Melanie Famao

import java.io.*;
import java.net.HttpURLConnection;

import java.net.URL;


public class PhilipsHueControl {

    private static final String AUTH_USER = "2217334838210e7f244460f83b42026f";
    private static final String LAMP_IP = "10.28.9.122";

    HttpURLConnection conToLamps;


    public PhilipsHueControl() {
        try {
            String url = "http://" + LAMP_IP + "/api/" + AUTH_USER + "/lights/";
            this.conToLamps = (HttpURLConnection) new URL(url).openConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //lampnumber can be 1,2,3
    public int getColor(int lampNr)  {
        String url = "http://" + LAMP_IP +  "/api/" + AUTH_USER + "/lights/" + lampNr;
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
            lampOn = false;
            lampEffects = "none";
        } else if (status == DriverStatus.LATE) {
            newColorValue = 65136;
            lampOn = true;
            lampEffects = "lselect";
        } else if (status == DriverStatus.ONTIME) {
            newColorValue = 4444;
            lampOn = true;
            lampEffects = "none";
        } else if (status == DriverStatus.WAITING) {
            newColorValue = 23536;
            lampOn = true;
            lampEffects = "none";
        }

        //String url = "http://localhost:8000/api/newdeveloper/lights/3/state";
        String url = "http://" + LAMP_IP + "/api/" + AUTH_USER + "/lights/" + lampNr + "/state";
        try {
            this.conToLamps = (HttpURLConnection) new URL(url).openConnection();
            conToLamps.setDoOutput(true);
            conToLamps.setRequestMethod("PUT");

            String response = "";

            BufferedWriter osw = new BufferedWriter(new OutputStreamWriter(conToLamps.getOutputStream()));
            BufferedReader fromClient;

            String json;
            if (status == DriverStatus.INACTIVE) {
                json = "{ \"on\": " + lampOn + ", \"alert\": \"" + lampEffects + "\"" + " }";
            } else {
                json = "{ \"hue\": " + newColorValue + ", \"on\": " + lampOn + ", \"alert\": \"" + lampEffects + "\"" + ", \"bri\": 200 " + " }";
            }



            System.out.println(json);
            osw.write(json);
            osw.flush();
            osw.close();


            fromClient = new BufferedReader(new InputStreamReader(conToLamps.getInputStream()));
            for (String line = fromClient.readLine(); line != null && line.length() > 0; line = fromClient.readLine()) {
                response += line;
            }
            System.out.println(response);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
