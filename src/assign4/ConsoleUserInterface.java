package assign4;

import java.util.Scanner;

/**
 * Abstract class that represents a Console User Interface.
 */
public abstract class ConsoleUserInterface implements Runnable {

    protected static Dealership dealership;
    protected static Scanner sc = new Scanner(System.in);

    /**
     * Abstract method. To be implemented by subclasses.
     */
    protected abstract void printMenu();
    

    /**
     * Abstract method. To be implemented by subclasses.
     */
    //public abstract void run();

}
