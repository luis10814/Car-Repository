package assign4;

import java.util.InputMismatchException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerView extends ConsoleUserInterface {
    
    private static final Logger logger;

    static { //Initialize static class fields
        logger = Logger.getLogger(CustomerView.class.getName());
        logger.setUseParentHandlers(true);
    }
    
    protected void printMenu() {
        System.out.println(
                "\n1. Search inventory.  \n"
                + "2. Send message to dealership. \n"
                + "3. Exit customer view.");
    }
    
    public void run() {
        logger.log(Level.FINE, "CustomerView started.");
        
        int choice = 0;
        boolean exitView = false;
        do {
            printMenu();
            try {
                choice = sc.nextInt();
                sc.nextLine(); //clear the new line character from the input
            
                switch (choice) {
                    case 1: searchInventory(); break;
                    case 2: sendMsg(); break;
                    case 3: exitView = true; break;
                    default: System.err.print("\nPlease select a number between 1 and 3: ");
                }
            } catch (InputMismatchException ex) {
                logger.log(Level.WARNING, "Input missmatch. Please Try again.");
                sc.nextLine(); //clear the new line character from the input
            } catch (BadInputException ex) {
                logger.log(Level.WARNING, ex.getMessage(), ex);
            } 
        } while (!exitView);
        
        logger.log(Level.FINE, "CustomerView exited.");
    }

    private void searchInventory() throws BadInputException {
        
        System.out.println("\n1. Search by VIN.");
        System.out.println("2. Search by vehicle properties.");
        System.out.print("Choice: ");
        
        int choice = sc.nextInt();
        
        if (choice == 1) {
            System.out.print("\nEnter VIN: ");
            String vin = sc.nextLine();
            
            BusyThread busy = new BusyThread();
            busy.start();
            Vehicle v = dealership.getVehInv().searchInventory(vin);
            busy.setBusy(false);
            
            if (v != null)
                v.print();
            
        } else if (choice == 2) {
            System.out.println("\nSelect vehicle type (1=PassengerCar, 2=Truck, 3=Motorocycle): ");
            int vehType = sc.nextInt();
            
            System.out.println("Enter make (leave empty to ignore): ");
            String make = sc.nextLine();
            
            System.out.println("Enter model (leave empty to ignore): ");
            String model = sc.nextLine();
            
            System.out.println("Enter year (leave empty to ignore): ");
            String yearStr = sc.nextLine();
            
            System.out.println("Enter price range (leave empty to ignore). ");
            System.out.println("Low: ");
            String lowPriceStr = sc.nextLine();
            System.out.println("High: ");
            String highPriceStr = sc.nextLine();
            
            BusyThread busy = new BusyThread();
            busy.start();
            Set<Vehicle> vehicles = dealership.getVehInv().searchInventory(vehType, make, model, yearStr, lowPriceStr, highPriceStr);
            busy.setBusy(false);
            
            showVehicles(vehicles);
        } else
            throw new BadInputException("Unknow search choice. Please try again.");
    }
    
    public void showVehicles(Set<Vehicle> vehicles) {
        
        System.out.println("---------------------------------------------------"
                + "-------------------------------------------------------");
        System.out.format("| %12s | %12s | %8s | %8s | %6s | %9s | %10s | %17s | %n", 
                "VEHICLE TYPE", "VIN", "MAKE", "MODEL", "YEAR", "MILEAGE", "PRICE", "EXTRA DETAILS");
        System.out.println("---------------------------------------------------"
                + "-------------------------------------------------------");
        for (Vehicle v : vehicles) {
            v.print();
        }
        System.out.println("---------------------------------------------------"
                + "-------------------------------------------------------");
    }

    private void sendMsg() throws BadInputException {
            
        System.out.print("\nEnter your name: ");
        String name = sc.nextLine();
        if (name.length() == 0)
            throw new BadInputException("Name cannot be empty.");
        
        System.out.print("\nEnter your phone#: ");
        String phoneNumber = sc.nextLine();
        if (phoneNumber.length() == 0)
            throw new BadInputException("Phone# cannot be empty.");
        
        System.out.print("\nEnter your email: ");
        String email = sc.nextLine();
        if (email.length() == 0)
            throw new BadInputException("Email cannot be empty.");
        
        System.out.println("\nEnter your Question (all in one line): ");
        String text = sc.nextLine();
        if (text.length() == 0)
            throw new BadInputException("Question cannot be empty.");
        
        BusyThread busy = new BusyThread();
        busy.start();
        
        dealership.getComm().sendMessage(name, name, email, text);
        
        busy.setBusy(false);
        
        System.out.println("\nMessage sent.");
    }

}
