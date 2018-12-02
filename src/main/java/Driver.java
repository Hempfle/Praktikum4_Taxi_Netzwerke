import java.util.Calendar;

public class Driver {
    private DriverStatus driverStatus;
    private String startAddress;
    private int startAddressNum;
    private int targetAddressNum;
    private String targetAddress;
    private String startCity;
    private String targetCity;
    private Calendar estArrival;


    public Driver() {
        this.driverStatus = DriverStatus.INACTIVE;
    }

    public Driver(String startCity, String startAddress, int startAddressNum) {
        this.driverStatus = DriverStatus.WAITING;
        this.startCity = startCity;
        this.startAddress = startAddress;
        this.startAddressNum = startAddressNum;
    }

    public void createNewRide(String targetAddress, int targetAddressNum) {
        this.driverStatus = DriverStatus.ONTIME;
        this.targetAddress = targetAddress;
        this.targetAddressNum = targetAddressNum;
        this.estArrival = Calendar.getInstance();
        int estimatedDuration = Controlhub.getEstimatedTime(Controlhub.getGeoCode(this.startCity, this.startAddress, this.startAddressNum)[0], Controlhub.getGeoCode(this.startCity, this.startAddress, this.startAddressNum)[1], Controlhub.getGeoCode(this.targetCity, this.targetAddress, this.targetAddressNum)[0], Controlhub.getGeoCode(this.targetCity, this.targetAddress, this.targetAddressNum)[1]);
        this.estArrival.add(Calendar.SECOND, estimatedDuration);
    }


    //getter & setter

    public void setStatus(DriverStatus newStatus) {
        driverStatus = newStatus;
    }



    public String getTargetAddress() {
        return targetAddress;
    }

    public void setTargetAddress(String targetAddress) {
        this.targetAddress = targetAddress;
    }

    public Calendar getEstArrival() {
        return estArrival;
    }

    public void setEstArrival(Calendar estArrival) {
        this.estArrival = estArrival;
    }

    public DriverStatus getDriverStatus() {
        return driverStatus;
    }

    public void setDriverStatus(DriverStatus driverStatus) {
        this.driverStatus = driverStatus;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public int getStartAddressNum() {
        return startAddressNum;
    }

    public void setStartAddressNum(int startAddressNum) {
        this.startAddressNum = startAddressNum;
    }

    public int getTargetAddressNum() {
        return targetAddressNum;
    }

    public void setTargetAddressNum(int targetAddressNum) {
        this.targetAddressNum = targetAddressNum;
    }

    public String getTargetCity() {
        return targetCity;
    }

    public void setTargetCity(String targetCity) {
        this.targetCity = targetCity;
    }

    public String getStartCity() {
        return startCity;
    }

    public void setStartCity(String startCity) {
        this.startCity = startCity;
    }
}

