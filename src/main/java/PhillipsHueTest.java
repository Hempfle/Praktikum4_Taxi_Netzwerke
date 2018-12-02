public class PhillipsHueTest {
    public static void main(String[] args) {
        PhilipsHueControl philipsHueControl = new PhilipsHueControl();
        System.out.println("Verbindung hergestellt");
        System.out.println(philipsHueControl.getColor(1));
        System.out.println(philipsHueControl.getColor(2));
        System.out.println(philipsHueControl.getColor(3));
        philipsHueControl.setColor(1, DriverStatus.WAITING);
    }
}
