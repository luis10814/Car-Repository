package assign4;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Dealership implements Serializable {
    
    private static final Logger logger;
    private static ConsoleUserInterface cui;
    
    private UserRecords userRecords;
    private TransRecords transRec;
    private VehicleInventory vehInv;
    private CommunicationMngr comm;
    
    static { //Initialize static class fields
        
        //---------------------------------------------------------------------
        // Initialize the root logger. Local class loggers will 
        // inherit from this.
        Logger rootLogger = Logger.getLogger("assign4");
        rootLogger.setUseParentHandlers(false);
        rootLogger.setLevel(Level.ALL);
        
        //Create a Console logger
        ConsoleHandler cHandler = new ConsoleHandler();
        cHandler.setFormatter(new Formatter() {
            public String format(LogRecord record) {
                return "\n" + record.getLevel() + ": " + record.getMessage() + "\n";
            }
        });
        cHandler.setLevel(Level.INFO); //Only show down to INFO level messages at the console
        rootLogger.addHandler(cHandler);
        
        
        //Create a File logger
        try {
            FileHandler fHandler = new FileHandler("DealershipLog.%u.%g.txt", true);
            fHandler.setFormatter(new SimpleFormatter());
            fHandler.setLevel(Level.FINE); //Log down to FINE LEVEL messages.
            rootLogger.addHandler(fHandler);
        } catch (Exception ex) {
            rootLogger.log(Level.SEVERE, "Could not create logging file.", ex);
        }
        //----------------------------------------------------------------------
        
        //Local logger for Dealership class
        logger = Logger.getLogger(CustomerView.class.getName());
        logger.setUseParentHandlers(true);
    }

    public Dealership() {
        this.userRecords = new UserRecords();
        this.transRec = new TransRecords(this);
        this.vehInv = new VehicleInventory();
        this.comm = new CommunicationMngr(this);
        Dealership.cui = new GeneralView(this);
    }

    public ConsoleUserInterface getCui() {
        return cui;
    }

    public UserRecords getUserRecords() {
        return userRecords;
    }

    public TransRecords getTransRec() {
        return transRec;
    }

    public VehicleInventory getVehInv() {
        return vehInv;
    }

    public CommunicationMngr getComm() {
        return comm;
    }
    
     /**
     * This method is used to read the database from a file, as serializable object.
     * @return The Dealership serialized object.
     */
    public static Dealership readDatabase() {
        System.out.print("Reding database...");
        Dealership cds = null;

        // Try to read existing dealership database from a file
        InputStream file = null;
        InputStream buffer = null;
        ObjectInput input = null;
        try {
            file = new FileInputStream("Dealership.ser");
            buffer = new BufferedInputStream(file);
            input = new ObjectInputStream(buffer);
                
            // deserialize Dealership object
            cds = (Dealership) input.readObject();
            cds.userRecords.setUserIdCounter(input.readInt());
            input.close();
        } catch (ClassNotFoundException ex) {
            System.err.println(ex.toString());
        } catch (FileNotFoundException ex) {
            System.err.println("Database file not found.");
        } catch (IOException ex) {
            System.err.println(ex.toString());
        } finally {
            close(file);
        }
        System.out.println("Done.");
        
        return cds;
    }
    
    /**
     * This method is used to save the Dealership database as a 
     * serializable object.
     * @param cds
     */
    public static void writeDatabase(Dealership cds) {
        System.out.print("Writing database...");
        //serialize the database
        OutputStream file = null;
        OutputStream buffer = null;
        ObjectOutput output = null;
        try {
            file = new FileOutputStream("Dealership.ser");
            buffer = new BufferedOutputStream(file);
            output = new ObjectOutputStream(buffer);
            output.writeObject(cds);
            output.writeInt(cds.userRecords.getUserIdCounter());
            output.close();
        } catch (IOException ex) {
            System.err.println(ex.toString());
        } finally {
            close(file);
        }
        System.out.println("Done.");
    }
    
    /**
     * Auxiliary convenience method used to close a file and handle possible
     * exceptions that may occur.
     * @param c
     */
    public static void close(Closeable c) {
        if (c == null) {
            return;
        }
        try {
            c.close();
        } catch (IOException ex) {
            System.err.println(ex.toString());
        }
    }

    /**
     *
     * @param String
     */
    public static void main(String[] args) {
        Dealership d = readDatabase();
        
        // If you could not read from the file, create a new database.
        if (d == null) {
            System.out.println("Creating a new database.");
            d = new Dealership();

            Administrator admin = d.userRecords.addAdmin("Owner", 0000, 0.0f, 12343414);
            System.out.println("The first system user is an administrator with the following credentials.");
            System.out.println("User id: " + admin.getId());
            System.out.println("PIN: " + admin.getPin());
        }
        else {
            Dealership.cui = new GeneralView(d); //Initialize static field cui
        }
        
        d.cui.run();
        writeDatabase(d);
    }

}
