package assign4;

public class Supplier extends User {

    private String phoneNo;

    public Supplier(int id, String name, String phoneNo) {
        super(id, name);
        this.phoneNo = phoneNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    /**
     * Print the attributes of the supplier, in a formatted fashion.
     */
    @Override
    public void print() {
        System.out.format("| %10s | %9d | %22s | Ph#: %12s, DL#:%11d | %n", 
                "Supplier", id, name, phoneNo, 0);
    }
}
