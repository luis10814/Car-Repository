package assign4;

import static assign4.ConsoleUserInterface.sc;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeeView extends ConsoleUserInterface {
    
    private static final Logger logger;
    private Employee currentEmployee;

    static { //Initialize static class fields
        logger = Logger.getLogger(CustomerView.class.getName());
        logger.setUseParentHandlers(true);
    }

    protected void printMenu() {
        System.out.println(
                "\n1. Respond to customer message.  \n"
                + "2. Sell vehicle. \n"
                + "3. Purchase vehicle. \n"
                + "4. Show all vehicles. \n"
                + "5. Add new customer. \n"
                + "6. Add new supplier. \n"
                + "7. Show all users. \n"
                + "8. Exit employee view.");
    }
    
    /**
     * Main user interaction method of employee view.
     */
    public void run() {
        
        System.out.print("\nEnter your user id: ");
        int id = sc.nextInt();
        
        System.out.print("\nEnter your PIN: ");
        int pin = sc.nextInt();
        
        User u = dealership.getUserRecords().getUsers().get(id);
        if (u == null || !(u instanceof Employee)  || ((Employee)u).getPin()!=pin) {
            System.out.println("Employee user with given credentials not found in the database. Exiting...");
            return;
        } else {
            currentEmployee = (Employee) u;
        }
        
        logger.log(Level.FINE, "EmployeeView started.");
        int choice = 0;
        boolean exitView = false;
        do {
            printMenu();
            try {
                choice = sc.nextInt();
                sc.nextLine(); //clear the new line character from the input
            
                switch (choice) {
                    case 1: respondMsg(); break;
                    case 2: sellVehicle(); break;
                    case 3: purchaseVehicle(); break;
                    case 4: showAllVehicles(); break;
                    case 5: addCusotmer(); break;
                    case 6: addSuplier(); break;
                    case 7: showAllUsers(); break;
                    case 8: exitView = true; break;
                    default: System.err.println("Please select a number between 1 and 8.");
                }
            } catch (InputMismatchException ex) {
                logger.log(Level.WARNING, "Input missmatch. Please Try again.", ex);
                sc.nextLine(); //clear the new line character from the input
            } catch (UnsupportedOperationException ex) {
                ex.printStackTrace(); //For debugging purposes only.
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "An unknown error has occured. Check log file for details.", ex);
            }
        } while (!exitView);
        logger.log(Level.FINE, "EmployeeView exited.");
    }

    /**
     * Method used by employees to respond to customer messages, one at a time.
     */
    private void respondMsg() {
        if (dealership.getComm().getNewMessages().size() > 0) {
            Message msg = dealership.getComm().getNewMessages().poll();
            System.out.println("\nNew message:");
            msg.print();
            
            System.out.println("Enter response: ");
            String response = sc.nextLine();

            BusyThread busy = new BusyThread();
            busy.start();

            dealership.getComm().respondToMessage(msg, currentEmployee, response);

            busy.setBusy(false);
        } else {
            System.out.println("\nThere are no new messages.");
        }
    }

    /**
     * This method is used to complete a vehicle sale transaction.
     * @throws cardealershipsoftware.BadInputException
     */
    public void sellVehicle() throws BadInputException {
        System.out.print("\nEnter customer ID (int): ");
        int customerId = sc.nextInt();
        sc.nextLine(); //clear the new line character from the input
        
        //Check that the customer exists in database
        if (!dealership.getUserRecords().getUsers().containsKey(customerId)) {
            logger.log(Level.WARNING, "\nThe customer ID you have entered does not exist in the database.\n"
                    + "Please add the customer to the database first and then try again.");
            return;
        }
        
        System.out.print("\nEnter VIN (string): ");
        String vin = sc.nextLine();
        //Check that the vehicle exists in database
        BusyThread busy = new BusyThread();
        busy.start();
        Vehicle v = dealership.getVehInv().searchInventory(vin);
        busy.setBusy(false);
        
        if (v == null) {
            logger.log(Level.WARNING, "\nThe vehicle with the VIN you are trying to sell "
                    + "does not exist in the database. Aborting transaction.");
            return;
        }
        
        Date currentDate = new Date(System.currentTimeMillis());
        
        System.out.print("\nEnter sale price (float): ");
        float price = sc.nextFloat();
        sc.nextLine(); //clear the new line character from the input
        if (price < 0.0f)
            throw new BadInputException("Sale price cannot be negative.");
        
        dealership.getTransRec().sellVehicle(vin, currentDate, currentEmployee.getId(), customerId, price);
        
        System.out.println("\nTransaction Completed!");
    }

    /**
     * Method used by employee view for purchasing a new vehicle.
     * @throws BadInputException 
     */
    private void purchaseVehicle() throws BadInputException {
        System.out.print("\nEnter supplier ID (int): ");
        int suplierId = sc.nextInt();
        sc.nextLine(); //clear the new line character from the input
        
        //Check that the customer exists in database
        if (!dealership.getUserRecords().getUsers().containsKey(suplierId)) {
            logger.log(Level.WARNING, "\nThe supplier ID you have entered does not exist in the database.\n"
                    + "Please add the customer to the database first and then try again.");
            return;
        }
        
        
        System.out.println("Select vehicle type:\n"
                + "1. Passenger car\n"
                + "2. Truck\n"
                + "3. Motorcycle");
        int vehicleType = sc.nextInt();
        sc.nextLine(); //clear the new line character from the input
        if (vehicleType < 1 || vehicleType > 3)
            throw new BadInputException("Legal vehicle type values: 1-3.");
        
        System.out.print("\nEnter VIN (string): ");
        String vin = sc.nextLine();
        if (vin.length() > 10)
            throw new BadInputException("VIN should not be more that 10 characters long.");
        
        System.out.print("\nEnter Make (string): ");
        String make = sc.nextLine();
        
        System.out.print("\nEnter Model (string): ");
        String model = sc.nextLine();
        
        System.out.print("\nEnter Year (int): ");
        int year = sc.nextInt();
        
        System.out.print("\nEnter Mileage (int): ");
        int mileage = sc.nextInt();
        if (mileage < 0)
            throw new BadInputException("Mileage cannot be negative.");
        
        System.out.print("\nEnter Price (float): ");
        float price = sc.nextFloat();
        if (price < 0.0f)
            throw new BadInputException("Price cannot be negative.");
        
        if (vehicleType == 1) {
            sc.nextLine();
            System.out.print("\nEnter body style (string): ");
            String bodyStyle = sc.nextLine();
            
            dealership.getTransRec().buyPassengerCar(vin, new Date(System.currentTimeMillis()), currentEmployee.getId(),
                    suplierId, make, model, year, mileage, price, bodyStyle);
        } else if (vehicleType == 2) {
            System.out.print("\nEnter max load weight (lb), (float): ");
            float maxLoad = sc.nextFloat();
            if (maxLoad < 0.0f)
                throw new BadInputException("Max load cannot be negative.");
            
            System.out.print("\nEnter truck length (ft), (float): ");
            float tLength = sc.nextFloat();
            if (tLength < 0.0f)
                throw new BadInputException("Truck length cannot be negative.");
            
            dealership.getTransRec().buyTruck(vin, new Date(System.currentTimeMillis()), currentEmployee.getId(),
                    suplierId, make, model, year, mileage, price, maxLoad, tLength);
        } else if (vehicleType == 3) {
            sc.nextLine();
            System.out.print("\nEnter motorcycle type (string): ");
            String type = sc.nextLine();
            
            System.out.print("\nEnter engine displacement: ");
            int displacement = sc.nextInt();
            if (displacement < 0.0f)
                throw new BadInputException("Displacement cannot be negative.");
            
            dealership.getTransRec().buyMotorcycle(vin, new Date(System.currentTimeMillis()), currentEmployee.getId(),
                    suplierId, make, model, year, mileage, price, type, displacement);
        } else {
            logger.log(Level.WARNING, "Unknown vehicle type entered. Please try again.");
        }
        
        System.out.println("Transaction completed!");
    }

    /**
     * This method prints out all the vehicle currently in the inventory, in a
     * formatted manner.
     */
    public void showAllVehicles() {
        
        System.out.println("---------------------------------------------------"
                + "-------------------------------------------------------");
        System.out.format("| %12s | %12s | %8s | %8s | %6s | %9s | %10s | %17s | %n", 
                "VEHICLE TYPE", "VIN", "MAKE", "MODEL", "YEAR", "MILEAGE", "PRICE", "EXTRA DETAILS");
        System.out.println("---------------------------------------------------"
                + "-------------------------------------------------------");
        for (Vehicle v : dealership.getVehInv().getVehicles()) {
            v.print();
        }
        System.out.println("---------------------------------------------------"
                + "-------------------------------------------------------");
    }

    private void addCusotmer() throws BadInputException {
        System.out.print("\nEnter name (string): ");
        String name = sc.nextLine();

        System.out.print("\nEnter phone number (string): ");
        String phoneNumber = sc.nextLine();

        System.out.print("\nEnter email (string): ");
        String email = sc.nextLine();

        System.out.print("\nEnter driver license number (int): ");
        int dlnumber = sc.nextInt();
        if (dlnumber < 0) {
            throw new BadInputException("Driver license number cannot be negative.");
        }
        
        dealership.getUserRecords().addCustomer(name, phoneNumber, email, dlnumber);
    }
    
    private void addSuplier() throws BadInputException {
        System.out.print("\nEnter name (string): ");
        String name = sc.nextLine();

        System.out.print("\nEnter phone number (string): ");
        String phoneNumber = sc.nextLine();
        
        dealership.getUserRecords().addSupplier(name, phoneNumber);
    }

    /**
     * Prints out a list of all users in the database.
     */
    public void showAllUsers() {
        System.out.println("---------------------------------------------------"
                + "-------------------------------------");
        System.out.format("| %10s | %9s | %22s | %25s          | %n", 
                "USER TYPE", "USER ID", "FULL NAME", "OTHER DETAILS");
        System.out.println("---------------------------------------------------"
                + "-------------------------------------");
        for (User u : dealership.getUserRecords().getUsers().values()) {
            u.print();
        }
        System.out.println("---------------------------------------------------"
                + "-------------------------------------");
    }

}
