package assign4;

public class Customer extends User {

    private int driverLicNo;
    private String phoneNo;
    private String email;

    public Customer(int id, String name, int driverLicNo, String phoneNo, String email) {
        super(id, name);
        this.driverLicNo = driverLicNo;
        this.phoneNo = phoneNo;
        this.email = email;
    }

    public int getDriverLicNo() {
        return driverLicNo;
    }

    public void setDriverLicNo(int driverLicNo) {
        this.driverLicNo = driverLicNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Print the attributes of the customer, in a formatted fashion.
     */
    @Override
    public void print() {
        System.out.format("| %10s | %9d | %22s | Ph#: %12s, DL#:%11d | %n", 
                "Customer", id, name, phoneNo, driverLicNo);
    }
}
