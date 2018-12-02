//Melanie Famao


import javax.json.Json;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;
import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class Controlhub extends TimerTask {

    private static final int PORT = 8005;
    private ControlhubServerAPI server;

    static Driver driverOne = new Driver("Berlin","Schlossstrasse", 5, 1);
    static Driver driverTwo = new Driver("Schweinfurt","Danzigstrasse", 5 ,2);
    static Driver driverThree = new Driver("Bonn", "Hauptstrasse", 20, 3);

    //ui components
    JPanel pnl_DriverOne;
    JPanel pnl_main;
    JPanel pnl_DriverTwo;
    JPanel pnl_DriverThree;
    JLabel lbl_DriverOne;
    JLabel lbl_DriverTwo;
    JLabel lbl_DriverThree;
    JFrame fr;


    public ControlhubServerAPI getServer() {
        return this.server;
    }

    //get Json from Website
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
    // get geocode from here api
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
    //get traffic time in sec depending on current traffic situation
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

    //get estimated time in sec if nothing goes wrong
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
    //initialize UI with swing
    public void initUI() {

        // Swing UI
        pnl_DriverOne = new JPanel();
        pnl_main = new JPanel(new FlowLayout());
        pnl_DriverTwo = new JPanel();
        pnl_DriverThree = new JPanel();
        lbl_DriverOne = new JLabel("Driver 1 \n Aktueller Standort: " + driverOne.getStartCity() + "\n" + driverOne.getStartAddress() + " " + driverOne.getStartAddressNum() + "\r Aktueller Status" + driverOne.getDriverStatus().toString());
        lbl_DriverTwo = new JLabel("Driver 2 \n Aktueller Standort: " + driverTwo.getStartCity() + "\n" + driverTwo.getStartAddress() + " " + driverTwo.getStartAddressNum() + "\r Aktueller Status" + driverTwo.getDriverStatus().toString());
        lbl_DriverThree = new JLabel("Driver 3 \n Aktueller Standort: " + driverThree.getStartCity() + "\n" + driverThree.getStartAddress() + " " + driverThree.getStartAddressNum() + "\r Aktueller Status" + driverThree.getDriverStatus().toString());


        pnl_main.add(pnl_DriverOne);
        pnl_main.add(pnl_DriverTwo);
        pnl_main.add(pnl_DriverThree);
        pnl_DriverOne.add(lbl_DriverOne);
        pnl_DriverTwo.add(lbl_DriverTwo);
        pnl_DriverThree.add(lbl_DriverThree);

        fr = new JFrame();
        fr.setContentPane(pnl_main);  // Use our pane as the default pane
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit program when frame is closed
        fr.setLocation(300, 300);
        fr.pack();
        fr.setVisible(true);


    }

    public static void main(String[] args) {
        Controlhub hub = new Controlhub();
        hub.server = new ControlhubServerAPI();
        hub.initUI();
        


        BufferedReader taxiReader;
        BufferedWriter taxiWriter;



        //run run() all 5 sec
        Timer timer = new Timer();
        timer.schedule(new Controlhub(), 0, 5000);


        try {
            Socket taxiClient = hub.server.getServerSocket().accept();
            taxiReader = new BufferedReader(new InputStreamReader(taxiClient.getInputStream()));
            taxiWriter = new BufferedWriter(new OutputStreamWriter(taxiClient.getOutputStream()));

            while (!taxiClient.isClosed()) {
                int valid = 0;
                String jason = "";
                boolean isBody = false;
                for (String line = taxiReader.readLine(); line != null; line = taxiReader.readLine()) {
                    isBody = line.equals("\r\n");

                    if (isBody) {
                        jason += line;
                    }
                }
                JsonObject response = getJsonObjectFromUrl("http://localhost:8005");
                if (hub.server.getTheResponse(response).getDriverNumber() == driverOne.getDriverNumber()) {
                    driverOne = hub.server.getTheResponse(response);
                } else if (hub.server.getTheResponse(response).getDriverNumber() == driverTwo.getDriverNumber()) {
                    driverTwo = hub.server.getTheResponse(response);
                } else if (hub.server.getTheResponse(response).getDriverNumber() == driverThree.getDriverNumber()) {
                    driverThree = hub.server.getTheResponse(response);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        PhilipsHueControl hueAPI = new PhilipsHueControl();
        lbl_DriverOne.setText("Driver 1 \n Aktueller Standort: " + driverOne.getStartCity() + "\n" + driverOne.getStartAddress() + " " + driverOne.getStartAddressNum() + "\r Aktueller Status" + driverOne.getDriverStatus().toString());
        lbl_DriverTwo.setText("Driver 2 \n Aktueller Standort: " + driverTwo.getStartCity() + "\n" + driverTwo.getStartAddress() + " " + driverTwo.getStartAddressNum() + "\r Aktueller Status" + driverTwo.getDriverStatus().toString());
        lbl_DriverThree.setText("Driver 3 \n Aktueller Standort: " + driverThree.getStartCity() + "\n" + driverThree.getStartAddress() + " " + driverThree.getStartAddressNum() + "\r Aktueller Status" + driverThree.getDriverStatus().toString());
    }
}
