//Melanie Famao, Christopher Weber


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
import java.util.*;
import java.util.List;
import java.util.Timer;

public class Controlhub extends TimerTask {

    private ControlhubServerAPI server;

    static Driver driverOne = new Driver("Berlin","Schlossstrasse", 5, 1);
    static Driver driverTwo = new Driver("Schweinfurt","Danzigstrasse", 5 ,2);
    static Driver driverThree = new Driver("Bonn", "Hauptstrasse", 20, 3);


    public String status;
    public String city;
    public String address;
    public String number;
    public String endCity;
    public String endAddress;
    public String endNumber;




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

    public static void main(String[] args) {
        Controlhub hub = new Controlhub();
        hub.server = new ControlhubServerAPI();

        BufferedReader taxiReader;
        BufferedWriter taxiWriter;

        //run run() all 5 sec
        Timer timer = new Timer();
        timer.schedule(new Controlhub(), 0, 5000);


        try {
            Socket taxiClient = hub.server.getServerSocket().accept();
            while (true) {
                if (taxiClient.isClosed()) {
                    taxiClient = hub.server.getServerSocket().accept();
                }
                taxiReader = new BufferedReader(new InputStreamReader(taxiClient.getInputStream()));
                taxiWriter = new BufferedWriter(new OutputStreamWriter(taxiClient.getOutputStream()));

                System.out.println("got Connection");

                String jsonBody = "";
                int taxiNr = 0;

                for (String line = taxiReader.readLine(); line != null; line = taxiReader.readLine()) {
                    if (line.contains("\"taxi\"" )) {
                        taxiNr = Integer.parseInt(line.substring(8));
                    }
                        String test = hub.readJsonFromDriver(line);
                        jsonBody += line;
                        System.out.println(test);
                }


                Driver currentDriver = new Driver(hub.city, hub.address, Integer.parseInt(hub.number), taxiNr);
                if (hub.status.equals("active")) {
                    currentDriver.createNewRide(hub.city, hub.address, Integer.parseInt(hub.number), hub.endCity, hub.endAddress, Integer.parseInt(hub.endNumber));
                } else if ( hub.status.equals("pause")) {
                    currentDriver.setDriverStatus(DriverStatus.WAITING);
                } else {
                    currentDriver.setDriverStatus(DriverStatus.INACTIVE);
                }

                if (taxiNr == 1) {
                    driverOne = currentDriver;
                } else if (taxiNr == 2) {
                    driverTwo = currentDriver;
                } else if (taxiNr == 3) {
                    driverThree = currentDriver;
                } else {
                    throw new UnsupportedOperationException("Taxinr is 0");
                }


                taxiWriter.write("HTTP/1.0 200 OK\r\n");
                taxiWriter.write("Content-Length: 0\r\n");
                taxiWriter.write("\r\n");
                taxiWriter.flush();
                taxiWriter.close();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String readJsonFromDriver(String json) {
        if (json.contains("\"taxi\"" )) {
            return json.substring(8);
        } else if (json.contains("\"city\"")) {
            city = json.substring(8);
            return city;
        } else if (json.contains("\"status\"")) {
            status = json.substring(10);
            return status;
        } else if (json.contains("\"number\"")) {
            number = json.substring(10);
            return number;
        } else if (json.contains("\"endCity\"")) {
            endCity = json.substring(11);
            return endCity;
        } else if (json.contains("\"address\"") ) {
            address = json.substring(11);
            return address;
        } else if (json.contains("\"endNumber\"")) {
            endNumber = json.substring(13);
            return endNumber;
        } else if (json.contains("\"endAddress\"")) {
            endAddress = json.substring(14);
            return endAddress;
        } else {
            return "";
        }
    }

    @Override
    public void run() {
        PhilipsHueControl hueAPI = new PhilipsHueControl();
        hueAPI.setColor(driverOne.getDriverNumber(), driverOne.getDriverStatus());
        hueAPI.setColor(driverTwo.getDriverNumber(), driverTwo.getDriverStatus());
        hueAPI.setColor(driverThree.getDriverNumber(), driverThree.getDriverStatus());
    }
}
