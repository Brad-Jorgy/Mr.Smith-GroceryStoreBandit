package cs3450.model;

public class Customer {
    private int customerId;
    private String name;
    private boolean premium;
    private int rewardPoints;

    public Customer(int customerId, String name, boolean premium, int rewardPoints) {
        this.customerId = customerId;
        this.name = name;
        this.premium = premium;
        this.rewardPoints = rewardPoints;
    }
    public int getId(){ return this.customerId; }
    public void setId(int id){ this.customerId = id; }
    public String getName(){ return this.name; }
    public void setName(String name){ this.name = name; }
    public boolean getPremium(){ return this.premium; }
    public void setPremium(boolean premium){ this.premium = premium; }
    public int getRewardPoints(){ return this.rewardPoints; }
    public void setRewardPoints(int rewardPoints){ this.rewardPoints = rewardPoints; }
}
