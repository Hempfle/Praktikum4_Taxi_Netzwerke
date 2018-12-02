//Melanie Famao

import javax.json.JsonObject;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ControlhubServerAPI {

    private ServerSocket serverSocket;

    public ControlhubServerAPI(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            this.serverSocket = serverSocket;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ServerSocket getServerSocket() {
        return this.serverSocket;
    }

    public Socket openConnection() {
        try (Socket socket = this.serverSocket.accept()) {
            return socket;


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Driver getTheResponse(JsonObject request) {
        int taxiNum = request.getInt("taxi");
        String startCity = request.getString("city");
        String startStreet = request.getString("address");
        int startNum = request.getInt("number");
        String targetCity = request.getString("endCity");
        String targetStreet = request.getString("endAddress");
        int targetNum = request.getInt("endNumber");
        String status = request.getString("status");
        Driver newDriver = new Driver(startCity, startStreet, startNum);
        newDriver.createNewRide(startCity, startStreet, startNum, targetCity, targetStreet, targetNum);
        if (status.equals("active")) {
            newDriver.setDriverStatus(DriverStatus.ONTIME);
        }
        else if(status.equals("pause")){
            newDriver.setDriverStatus(DriverStatus.INACTIVE);
        }
        else {
            newDriver.setDriverStatus(DriverStatus.WAITING);
        }
        return newDriver;
    }

}
