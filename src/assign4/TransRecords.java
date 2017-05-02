package assign4;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransRecords implements Serializable {

    private List<Transaction> transactions;
    private final Dealership dealership;

    public TransRecords(Dealership dealership) {
        this.dealership = dealership;
        transactions = new ArrayList<>();
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * Completes a sale transaction and adds a new sale transaction object to
     * the set of completed transactions.
     *
     * @param vin
     * @param date
     * @param empId
     * @param custId
     * @param salePrice
     */
    public void sellVehicle(String vin, Date date, int empId, int custId, float salePrice) {
        Sale saleTrans = new Sale(vin, date, empId, (int) (Math.random() * 1000000), custId, salePrice);
        transactions.add(saleTrans);

        //Sold vehicles are automatically removed from the inventory.
        dealership.getVehInv().deleteVehicle(dealership.getVehInv().searchInventory(vin));
    }

    /**
     *
     * @param vin
     * @param date
     * @param empId
     * @param supplierId
     * @param purchasePrice
     */
    public void buyVehicle(String vin, Date date, int empId, int supplierId, float purchasePrice) {
        // TODO - implement TransRecords.buyVehicle
        throw new UnsupportedOperationException();
    }

    /**
     * Method for purchasing a passenger car. Creates a transaction record and
     * adds the vehicle to the inventory.
     * @param vin
     * @param date
     * @param empId
     * @param supplierId
     * @param make
     * @param model
     * @param year
     * @param mileage
     * @param purchasePrice
     * @param bodyStyle 
     */
    public void buyPassengerCar(String vin, Date date, int empId, int supplierId,
            String make, String model, int year, int mileage, float purchasePrice, String bodyStyle) {

        Purchase purchase = new Purchase(vin, date, empId, (int) (Math.random() * 1000000), supplierId, purchasePrice);
        transactions.add(purchase);

        //Purchased vehicles are automatically added to the inventory database
        dealership.getVehInv().addVehicle(new PassengerCar(vin, make, model, year, mileage, purchasePrice, bodyStyle));
    }

    
    /**
     * 
     * @param vin
     * @param date
     * @param empId
     * @param supplierId
     * @param make
     * @param model
     * @param year
     * @param mileage
     * @param purchasePrice
     * @param maxLoad
     * @param tLength 
     */
    public void buyTruck(String vin, Date date, int empId, int supplierId,
            String make, String model, int year, int mileage, float purchasePrice, float maxLoad, float tLength) {

        Purchase purchase = new Purchase(vin, date, empId, (int) (Math.random() * 1000000), supplierId, purchasePrice);
        transactions.add(purchase);

        dealership.getVehInv().addVehicle(new Truck(vin, make, model, year, mileage, purchasePrice, maxLoad, tLength));
    }

    
    /**
     * 
     * @param vin
     * @param date
     * @param empId
     * @param supplierId
     * @param make
     * @param model
     * @param year
     * @param mileage
     * @param purchasePrice
     * @param type
     * @param displacement 
     */
    public void buyMotorcycle(String vin, Date date, int empId, int supplierId,
            String make, String model, int year, int mileage, float purchasePrice, String type, int displacement) {

        Purchase purchase = new Purchase(vin, date, empId, (int) (Math.random() * 1000000), supplierId, purchasePrice);
        transactions.add(purchase);

        dealership.getVehInv().addVehicle(new Motorcycle(vin, make, model, year, mileage, purchasePrice, type, displacement));
    }

    /**
     *
     * @param vin
     */
    public void updateInventory(String vin) {
        // TODO - implement TransRecords.updateInventory
        throw new UnsupportedOperationException();
    }

}
