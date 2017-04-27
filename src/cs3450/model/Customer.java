package cs3450.model;

public class Customer {
    private int customerId;
    //private int orderId;
    private String name;
    private int rewardPoints;

    public Customer(int customerId, String name, int rewardPoints) {
        this.customerId = customerId;
        this.name = name;
        this.rewardPoints = rewardPoints;
    }
    public int getId(){ return this.customerId; }
    public void setId(int id){ this.customerId = id; }
    public String getName(){ return this.name; }
    public void setName(String name){ this.name = name; }
    public int getRewardPoints(){ return this.rewardPoints; }
    public void setRewardPoints(int rewardPoints){ this.rewardPoints = rewardPoints; }
}
