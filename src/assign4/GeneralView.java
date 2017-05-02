package assign4;

import java.util.InputMismatchException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class GeneralView extends ConsoleUserInterface {
    
    private static final Logger logger;

    static { //Initialize static class fields
        logger = Logger.getLogger(CustomerView.class.getName());
        logger.setUseParentHandlers(true);
    }

    public GeneralView(Dealership dealership) {
        GeneralView.dealership = dealership;
    }

    protected void printMenu() {
        System.out.println(
                "\n1. Switch to customer view.  \n"
                + "2. Switch to employee view. \n"
                + "3. Switch to admin view.\n"
                + "4. Exit program.");
    }
    
    public void run() {
        logger.log(Level.FINE, "GeneralView started.");
        int choice = 0;
        boolean exitProgram = false;
        do {
            printMenu();
            try {
                choice = sc.nextInt();
                sc.nextLine(); //clear the new line character from the input
            
                switch (choice) {
                    case 1: switchToCustView(); break;
                    case 2: switchToEmpView(); break;
                    case 3: switchToAdminView(); break;
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
        logger.log(Level.FINE, "GeneralView exited.");
    }

    private void switchToCustView() {
        cust2 customer = new cust2();
        customer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        customer.setVisible(true);
    }

    private void switchToEmpView() {
        new EmployeeView().run();
    }

    private void switchToAdminView() {
        new AdminView().run();
    }

}
