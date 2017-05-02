package assign4;

public class Employee extends User {

    protected int pin;
    protected float monthlySal;
    protected int bankAccNo;
    protected boolean active;

    public Employee(int id, String name, int pin, float monthlySal, int bankAccNo, boolean active) {
        super(id, name);
        this.pin = pin;
        this.monthlySal = monthlySal;
        this.bankAccNo = bankAccNo;
        this.active = active;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public float getMonthlySal() {
        return monthlySal;
    }

    public void setMonthlySal(float monthlySal) {
        this.monthlySal = monthlySal;
    }

    public int getBankAccNo() {
        return bankAccNo;
    }

    public void setBankAccNo(int bankAccNo) {
        this.bankAccNo = bankAccNo;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    /**
     * Print the attributes of the employee, in a formatted fashion.
     */
    @Override
    public void print() {
        System.out.format("| %10s | %9d | %22s | Salary: %9s, Bank#: %8d | %n", 
                "Employee", id, name, monthlySal, bankAccNo);
    }
}
