package assign4;

import java.util.Date;

public class Purchase extends Transaction {

    private int supplierID;
    private float purchPrice;

    public Purchase(String vin, Date date, int empId, int invoiceNo, int supplierID, float purchPrice) {
        super(vin, date, empId, invoiceNo);
        this.supplierID = supplierID;
        this.purchPrice = purchPrice;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    public float getPurchPrice() {
        return purchPrice;
    }

    public void setPurchPrice(float purchPrice) {
        this.purchPrice = purchPrice;
    }

 }
