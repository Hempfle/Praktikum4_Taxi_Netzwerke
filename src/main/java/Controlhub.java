//Melanie Famao


import javax.json.Json;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.TimerTask;

public class Controlhub extends TimerTask {

    private static final int PORT = 8005;
    private ControlhubServerAPI server;

    static Driver driverOne = new Driver("Schlossstrasse", 5);
    static Driver driverTwo = new Driver();
    static Driver driverThree = new Driver();



    public Controlhub() {
        this.server = new ControlhubServerAPI(PORT);
    }

    public ControlhubServerAPI getServer() {
        return this.server;
    }

    public static JsonObject getJsonObjectFromUrl( String url ) {
        try {
            HttpURLConnection conn = (HttpURLConnection) (new URL(url)).openConnection();
            // set request method (could also be POST or PUT, depending on the webservice to be used)
            conn.setRequestMethod("GET");
            // remember: for PUT, POST you need to set conn.setDoOutput(true);
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Exception occured while accessing url " + url + ": " + conn.getResponseMessage());
            }
            try (JsonReader jsonRdr = Json.createReader((InputStream) conn.getContent())) {
                return jsonRdr.readObject();
            }
        } catch (IOException ex) {
            throw new RuntimeException("Exception occured while accessing url: " + url, ex);
        }
    }
    public static double[] getGeoCode(String city, String street, int houseNum) {
        String geocodeURL = "https://geocoder.api.here.com/6.2/geocode.json?app_id=IcoZews0wmkTvlM45NiO&app_code=InPlGS-2hW44OdmkanRp8w&searchtext="+city+"+"+street+"+"+houseNum;
        HttpsURLConnection conn;
        BufferedReader fromConnection;
        String geoCodeText ="";

        JsonObject jsonObj = getJsonObjectFromUrl(geocodeURL);
        JsonNumber longitude = jsonObj.getJsonObject("Response").getJsonArray("View").get(0).asJsonObject().getJsonArray("Result").get(0).asJsonObject().getJsonObject("Location").getJsonObject("DisplayPosition").getJsonNumber("Longitude");
        JsonNumber latitude = jsonObj.getJsonObject("Response").getJsonArray("View").get(0).asJsonObject().getJsonArray("Result").get(0).asJsonObject().getJsonObject("Location").getJsonObject("DisplayPosition").getJsonNumber("Latitude");

        double []geoCode = new double[2];
        geoCode[0] = latitude.doubleValue();
        geoCode[1] = longitude.doubleValue();
        return geoCode;

    }
    //get traffic time in sec
    public static int getRealTime(double latStart, double lonStart, double latGoal, double lonGoal) {
        String geocodeURL = "https://route.api.here.com/routing/7.2/calculateroute.json?app_id=IcoZews0wmkTvlM45NiO&app_code=InPlGS-2hW44OdmkanRp8w&waypoint0=geo!" + latStart + "," + lonStart + "&waypoint1=geo!" + latGoal +"," + lonGoal + "&mode=fastest;car;traffic:enabled";
        HttpsURLConnection conn;
        BufferedReader fromConnection;
        String geoCodeText ="";

        JsonObject jsonObj = getJsonObjectFromUrl(geocodeURL);
        int trafficTime = jsonObj.getJsonObject("response").getJsonArray("route").get(0).asJsonObject().getJsonObject("summary").getJsonNumber("trafficTime").intValue();
        System.out.println("Reale Traffic Time bestimmt");
        return trafficTime;
    }

    //get traffic time in sec
    public static int getEstimatedTime(double latStart, double lonStart, double latGoal, double lonGoal) {
        String geocodeURL = "https://route.api.here.com/routing/7.2/calculateroute.json?app_id=IcoZews0wmkTvlM45NiO&app_code=InPlGS-2hW44OdmkanRp8w&waypoint0=geo!52.5,13.4&waypoint0=geo!" + latStart + "," + lonStart + "&waypoint1=geo!" + latGoal +"," + lonGoal + "&mode=fastest;car;traffic:enabled";
        HttpsURLConnection conn;
        BufferedReader fromConnection;
        String geoCodeText ="";

        JsonObject jsonObj = getJsonObjectFromUrl(geocodeURL);
        int trafficTime = jsonObj.getJsonObject("response").getJsonArray("route").get(0).asJsonObject().getJsonObject("summary").getJsonNumber("travelTime").intValue();
        System.out.println("Erwartete Traffic Time bestimmt");
        return trafficTime;
    }


    public static void main(String[] args) {
        // Let's make a button first
        JButton btn = new JButton("Click Me!");

        // Let's make the panel with a flow layout.
        // Flow layout allows the components to be
        // their preferred size.
        JPanel pane = new JPanel(new FlowLayout());
        pane.add(btn);  // Add the button to the pane

        // Now for the frame
        JFrame fr = new JFrame();
        fr.setContentPane(pane);  // Use our pane as the default pane
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit program when frame is closed
        fr.setLocation(200, 200); // located at (200, 200)
        fr.pack();                // Frame is ready. Pack it up for display.
        fr.setVisible(true);      // Make it visible


        Controlhub hub = new Controlhub();
        ControlhubServerAPI server = hub.getServer();


        Socket taxiClient = server.openConnection();

        try {
            BufferedReader taxiReader = new BufferedReader(new InputStreamReader(taxiClient.getInputStream()));
            BufferedWriter taxiWriter = new BufferedWriter(new OutputStreamWriter(taxiClient.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }




        driverOne.createNewRide("Planeggerstrasse", 20);
        String response = "";
        HttpsURLConnection conn;
        BufferedReader fromConnection;
        String routeURL = "https://route.api.here.com/routing/7.2/calculateroute.json?app_id=IcoZews0wmkTvlM45NiO&app_code=InPlGS-2hW44OdmkanRp8w&waypoint0=geo!52.5,13.4&waypoint1=geo!52.5,13.45&mode=fastest;car;traffic:disabled";
        double lat = getGeoCode("Munich", "Planeggerstrasse", 20)[0];
        double lon = getGeoCode("Munich", "Planeggerstrasse", 20)[1];
    }

    @Override
    public void run() {
        PhilipsHueControl hueAPI = new PhilipsHueControl();
        hueAPI.getColor(1);
    }
}