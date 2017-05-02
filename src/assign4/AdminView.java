package assign4;

import static assign4.ConsoleUserInterface.sc;
import java.util.InputMismatchException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminView extends ConsoleUserInterface {
    
    private static final Logger logger;

    static { //Initialize static class fields
        logger = Logger.getLogger(CustomerView.class.getName());
        logger.setUseParentHandlers(true);
    }

    protected void printMenu() {
        System.out.println(
                "\n1. Add Employee.  \n"
                + "2. Show all employees login info. \n"
                + "3. Show all transactions. \n"
                + "4. Exit Admin view.");
    }

    public void run() {
        
        System.out.print("\nEnter your user id: ");
        int id = sc.nextInt();
        
        System.out.print("\nEnter your PIN: ");
        int pin = sc.nextInt();
        
        User u = dealership.getUserRecords().getUsers().get(id);
        if (u == null || !(u instanceof Administrator)  || ((Administrator)u).getPin()!=pin) {
            System.out.println("Admin user with given credentials not found in the database. Exiting...");
            return;
        }
        
        logger.log(Level.FINE, "AdminView started.");
        int choice = 0;
        boolean exitProgram = false;
        do {
            printMenu();
            try {
                choice = sc.nextInt();
                sc.nextLine(); //clear the new line character from the input
            
                switch (choice) {
                    case 1: addNewEmployee(); break;
                    case 2: showEmplLoginInfo(); break;
                    case 3: showTransactions(); break;
                    case 4: exitProgram = true; break;
                    default: System.err.println("Please select a number between 1 and 4.");
                }
            } catch (InputMismatchException ex) {
                logger.log(Level.WARNING, "Input missmatch. Please Try again.", ex);
                sc.nextLine(); //clear the new line character from the input
            } catch (UnsupportedOperationException ex) {
                ex.printStackTrace(); //For debugging purposes only.
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "An unknown error has occured. Check log file for details.", ex);
            }
        } while (!exitProgram);
        logger.log(Level.FINE, "AdminView exited.");
    }

    private void addNewEmployee() {
        System.out.print("\nEnter employee name: ");
        String name = sc.nextLine();
        
        System.out.print("\nEnter employee pin (integer): ");
        int pin = sc.nextInt();
        
        System.out.print("\nEnter employee salary (float): ");
        float salary = sc.nextFloat();
        
        System.out.print("\nEnter employee bank account #: ");
        int bankAccNo = sc.nextInt();
        sc.nextLine(); //clear the new line character from the input
        
        System.out.print("\nIs new employee an administrator (y/n)?: ");
        String isAdmin = sc.nextLine();
        
        if (isAdmin.equalsIgnoreCase("y"))
            dealership.getUserRecords().addAdmin(name, pin, salary, bankAccNo);
        else
            dealership.getUserRecords().addEmployee(name, pin, salary, bankAccNo);
    }

    private void showEmplLoginInfo() {
        for (User u : dealership.getUserRecords().getUsers().values()) {
            if (u instanceof Administrator || u instanceof Employee) {
                System.out.println("\nUser type: " + u.getClass().getSimpleName());
                System.out.println("Name: " + u.getName());
                System.out.println("ID: " + u.getId());
                System.out.println("PIN: " + ((Employee)u).getPin());
            }
        }
    }

    private void showTransactions() {
        for (Transaction t : dealership.getTransRec().getTransactions()) {
            System.out.print("\n" + t.getClass().getSimpleName() + " :: ");
            t.print();
        }
    }

}
