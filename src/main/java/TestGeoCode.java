public class TestGeoCode {
    public static void main(String[] args) {
        double[] geoCode = Controlhub.getGeoCode("Berlin", "Schlossstrasse", 5);
        System.out.println(geoCode[0] + " " + geoCode[1]);
    }
}
