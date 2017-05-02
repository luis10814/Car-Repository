package assign4;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class UserRecords implements Serializable {
    
    private static int userIdCounter = 0;
    private final Map<Integer, User> users;

    public UserRecords() {
        users = new HashMap<Integer, User>();
    }

    public Map<Integer, User> getUsers() {
        return users;
    }

    public static int getUserIdCounter() {
        return userIdCounter;
    }

    public static void setUserIdCounter(int userIdCounter) {
        UserRecords.userIdCounter = userIdCounter;
    }

    /**
     *
     * @param name
     * @param phoneNo
     * @param email
     * @param driverLicNo
     */
    public Customer addCustomer(String name, String phoneNo, String email, int driverLicNo) {
        int userId = userIdCounter++;
        Customer customer = new Customer(userId, name, driverLicNo, phoneNo, email);
        users.put(userId, customer);
        return customer;
    }
    
    public Supplier addSupplier(String name, String phoneNumber) {
        int userId = userIdCounter++;
        Supplier supplier = new Supplier(userId, name, phoneNumber);
        users.put(userId, supplier);
        return supplier;
    }

    /**
     *
     * @param name
     * @param monthlySal
     * @param bankAccNo
     */
    public Employee addEmployee(String name, int pin, float monthlySal, int bankAccNo) {
        int userId = userIdCounter++;
        Employee employee = new Employee(userId, name, pin, monthlySal, bankAccNo, true);
        users.put(userId, employee);
        return employee;
    }

    /**
     *
     * @param name
     * @param monthlySal
     * @param bankAccNo
     */
    public Administrator addAdmin(String name, int pin, float monthlySal, int bankAccNo) {
        int userId = userIdCounter++;
        Administrator admin = new Administrator(userId, name, pin, monthlySal, bankAccNo, true);
        users.put(userId, admin);
        return admin;
    }

    public User findUserByName(String name) {
        for (int id : users.keySet()) {
            User user = users.get(id);
            if (user.getName().equals(name)) {
                return user;
            }
        }

        return null;
    }

    public Customer findCustomerByEmail(String email) {
        for (int id : users.keySet()) {
            User user = users.get(id);

            if (user instanceof Customer) {
                Customer c = (Customer) user;
                if (c.getEmail().equalsIgnoreCase(email))
                    return c;
            }
        }

        return null;
    }
}
