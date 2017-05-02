package assign4;

import java.util.Date;

public class Sale extends Transaction {

    private int custId;
    private float salePrice;

    public Sale(String vin, Date date, int empId, int invoiceNo, int custId, float salePrice) {
        super(vin, date, empId, invoiceNo);
        this.custId = custId;
        this.salePrice = salePrice;
    }

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public float getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(float salePrice) {
        this.salePrice = salePrice;
    }
    
}
