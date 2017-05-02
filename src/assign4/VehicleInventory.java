package assign4;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VehicleInventory implements Serializable {

    private LinkedList<Vehicle> vehicles;

    public VehicleInventory() {
        vehicles = new LinkedList<>();
    }

    public LinkedList<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     *
     * @param v
     */
    public void addVehicle(Vehicle v) {
        vehicles.add(v);
    }

    /**
     *
     * @param v
     */
    public void deleteVehicle(Vehicle v) {
        vehicles.remove(v);
    }

    /**
     *
     * @param v
     */
    public void updateVehicle(Vehicle v) {
        // TODO - implement VehicleInventory.updateVehicle
        throw new UnsupportedOperationException();
    }

    /**
     * Search vehicle by VIN.
     * @param vin
     * @return v:Vehicle
     */
    public Vehicle searchInventory(String vin) {
        
        try {
            Thread.sleep((int)(Math.random()*3000));
        } catch (InterruptedException ex) {
            Logger.getLogger(VehicleInventory.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (Vehicle v : vehicles) {
            if (v.getVin().equalsIgnoreCase(vin))
                return v;
        }
        
        return null;
    }

    /**
     * Search vehicle by properties.
     * @param vehType
     * @param make
     * @param model
     * @param yearStr
     * @param lowPriceStr
     * @param highPriceStr
     * @return
     * @throws BadInputException 
     */
    public Set<Vehicle> searchInventory(int vehType, String make, String model,
            String yearStr, String lowPriceStr, String highPriceStr) throws BadInputException {

        Set<Vehicle> matchingVehicles = new HashSet<>();
        try {
            for (Vehicle v : vehicles) {
                if (vehicleTypeMatches(vehType, v)
                        && (make.equals("") || make.equals(v.getMake()))
                        && (model.equals("") || model.equals(v.getModel()))
                        && (yearStr.equals("") || Integer.parseInt(yearStr) == v.getYear())
                        && (lowPriceStr.equals("") || Float.parseFloat(lowPriceStr) <= v.getPrice())
                        && (highPriceStr.equals("") || Float.parseFloat(highPriceStr) >= v.getPrice())) {
                    matchingVehicles.add(v);
                }
            }
        } catch (NumberFormatException ex) {
            throw new BadInputException("NumberFormatException");
        }
        
        try {
            Thread.sleep((int)(Math.random()*10000));
        } catch (InterruptedException ex) {
            Logger.getLogger(VehicleInventory.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return matchingVehicles;
    }
    
    private boolean vehicleTypeMatches(int vehType, Vehicle v) {
        if (vehType == 1 && v instanceof PassengerCar ||
                vehType == 2 && v instanceof Truck ||
                vehType == 3 && v instanceof Motorcycle)
            return true;
        return false;
    }

}
