package model;

public class Customer {
    private int customerID;
    private String customerName;
    private String customerPassword;
    private String customerPhone;
    private String orderNumber;

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPassword() {
        return customerPassword;
    }

    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void seamyCall(String myCall) {
        this.customerPhone = myCall;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Override
    public String toString() {
        return "Costumer [customerID=" + customerID + ", customerName=" + customerName + ", customerPassword=" + customerPassword + ", customerPhone=" + customerPhone
                + ", orderNumber=" + orderNumber + "]";
    }
}
