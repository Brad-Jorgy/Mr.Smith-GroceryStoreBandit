package cs3450.model;

public class Customer {
    private int customerId;
    private int orderId;
    private String name;
    private String creditCard;
    private String address;
    private String city;
    private String state;
    private String zipcode;
    private String country;

    public Customer(int customerId, int orderId, String name, String creditCard, String address, String city, String state, String zipcode, String country) {
        this.customerId = customerId;
        this.orderId = orderId;
        this.name = name;
        this.creditCard = creditCard;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.country = country;
    }

    public int getId(){ return this.customerId; }
    public void setId(int id){ this.customerId = id; }
    public String getName(){ return this.name; }
    public void setName(String name){ this.name = name; }
    public String getCreditCard(){ return this.creditCard; }
    public void setCreditCard(String cc){ this.creditCard = cc; }
    public String getAddress(){ return this.address; }
    public void setAddress(String address){ this.address = address; }
    public String getCity(){ return this.city; }
    public void setCity(String city){ this.city = city; }
    public String getState(){ return this.state; }
    public void setState(String state){ this.state = state; }
    public String getZipcode(){ return this.zipcode; }
    public void setZipcode(String zipcode){ this.zipcode = zipcode; }
    public String getCountry(){ return this.country; }
    public void setCountry(String country){ this.country = country; }
    public int getOrderId() { return this.orderId; }
    public void setOrderId(int oId) { this.orderId = oId; }

//    @Override
//    public String toString() {
//        return mProduct.getName() + "        " + mProduct.getPrice() + "        " + mQuantity + "        " + String.format("%.2f", mProduct.getPrice() * mQuantity);
//    }
}
