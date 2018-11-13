//Melanie Famao

import java.net.HttpURLConnection;
import java.net.URL;

public class PhilipsHueControl {

    private static final int COLOR_RED = 0;
    private static final int COLOR_ORANGE = 0;
    private static final int COLOR_GREEN = 0;
    private static final int COLOR_OUT = 0;

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

    public void getSetColor(int lampNr, DriverStatus status) {


    }




}
