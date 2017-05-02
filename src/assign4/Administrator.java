package assign4;

public class Administrator extends Employee {

    public Administrator(int id, String name, int pin, float monthlySal, int bankAccNo, boolean active) {
        super(id, name, pin, monthlySal, bankAccNo, active);
    }
    
    /**
     * Print the attributes of the administrator, in a formatted fashion.
     */
    @Override
    public void print() {
        System.out.format("| %10s | %9d | %22s | Salary: %9s, Bank#: %8d | %n", 
                "Admin", id, name, monthlySal, bankAccNo);
    }
}