package assign4;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommunicationMngr implements Serializable {
    
    private final Dealership dealership;

    private final Queue<Message> newMessages;
    private final Set<Message> answeredMessages;

    public CommunicationMngr(Dealership d) {
        this.dealership = d;
        newMessages = new LinkedBlockingQueue<>();
        answeredMessages = new HashSet<>();
    }

    /**
     * Generates a new message submitted by a customer and adds it in the new
     * messages queue.
     *
     * @param customer
     * @param text
     */
    public void newMessage(Customer customer, String text) {
        Message msg = new Message(customer, text, null);
        newMessages.add(msg);
    }

    public Queue<Message> getNewMessages() {
        return newMessages;
    }

    public Set<Message> getAnsweredMessages() {
        return answeredMessages;
    }

    /**
     * New message sent by a customer.
     * @param name
     * @param phone
     * @param email
     * @param text 
     */
    public void sendMessage(String name, String phone, String email, String text) {
        
        // Check if the customer already exists in the database
        Customer customer = dealership.getUserRecords().findCustomerByEmail(email);
        
        if (customer == null) // Customer does not exist in database
            customer = dealership.getUserRecords().addCustomer(name, phone, email, 0);
        
        newMessage(customer, text);
        
        
        // Delay message sending process to simulate slow back-end response.
        try {
            Thread.sleep((int) (Math.random() * 3000));
        } catch (InterruptedException ex) {
            Logger.getLogger(VehicleInventory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Used by employee to respond to a customer message. Removes the original
     * customer message from the new messages queue and adds to the set of
     * answered messages, along with the response message.
     *
     * @param originalMsg
     * @param employee
     * @param text
     */
    public void respondToMessage(Message originalMsg, Employee employee, String text) {
        answeredMessages.add(originalMsg);
        
        Message responseMsg = new Message(employee, text, originalMsg);
        answeredMessages.add(responseMsg);
        
        // Delay message sending process to simulate slow back-end response.
        try {
            Thread.sleep((int) (Math.random() * 3000));
        } catch (InterruptedException ex) {
            Logger.getLogger(VehicleInventory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
