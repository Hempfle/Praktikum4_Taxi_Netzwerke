import java.util.Date;

public class Driver {
    private DriverStatus driverStatus;
    private String startAddress;
    private String targetAddress;
    private Date estArrival;

    public Driver() {
        this.driverStatus = DriverStatus.INACTIVE;
    }

    public Driver(String startAddress) {
        this.driverStatus = DriverStatus.WAITING;
        this.startAddress = startAddress;
    }

    public void createNewRide(String targetAddress, Date estArrival) {
        this.driverStatus = DriverStatus.ONTIME;
        this.targetAddress = targetAddress;
        this.estArrival = estArrival;
    }



    //getter & setter

    public void setStatus(DriverStatus newStatus) {
        driverStatus = newStatus;
    }

    public DriverStatus getStatus() {
        return this.driverStatus;
    }

    public String getTargetAddress() {
        return targetAddress;
    }

    public void setTargetAddress(String targetAddress) {
        this.targetAddress = targetAddress;
    }

    public Date getEstArrival() {
        return estArrival;
    }

    public void setEstArrival(Date estArrival) {
        this.estArrival = estArrival;
    }
}

