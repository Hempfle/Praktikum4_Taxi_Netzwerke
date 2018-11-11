import java.util.Date;

public class Driver {
    private DriverStatus driverStatus;
    private String startAdress;
    private String targetAdress;
    private Date estArrival;

    public Driver() {
        this.driverStatus = DriverStatus.INACTIVE;
    }

    public Driver(String startAdress) {
        this.driverStatus = DriverStatus.WAITING;
        this.startAdress = startAdress;
    }

    public void setStatus(DriverStatus newStatus) {
        driverStatus = newStatus;
    }

    public void createNewRide(String targetAdress, Date estArrival) {
        this.targetAdress = targetAdress;
        this.estArrival = estArrival;
    }

    public DriverStatus getStatus() {
        return this.driverStatus;
    }

    public String getTargetAdress() {
        return targetAdress;
    }

    public void setTargetAdress(String targetAdress) {
        this.targetAdress = targetAdress;
    }

    public Date getEstArrival() {
        return estArrival;
    }

    public void setEstArrival(Date estArrival) {
        this.estArrival = estArrival;
    }
}

