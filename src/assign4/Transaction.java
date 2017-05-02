package assign4;

import java.io.Serializable;
import java.util.Date;

public abstract class Transaction implements Serializable {

    protected String vin;
    protected Date date;
    protected int empId;
    protected int invoiceNo;

    public Transaction(String vin, Date date, int empId, int invoiceNo) {
        this.vin = vin;
        this.date = date;
        this.empId = empId;
        this.invoiceNo = invoiceNo;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public int getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(int invoiceNo) {
        this.invoiceNo = invoiceNo;
    }
    
    public void print() {
        System.out.println(date.toString() +" -- VIN: " + vin + ", Invoice: " + invoiceNo);
    }
}
