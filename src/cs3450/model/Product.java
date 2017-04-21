package cs3450.model;

public class Product{
  private int itemId;
  private String name;
  private double price;
  private double discountPrice;
  private int quantity;
  private String provider;
  public Product(int id, String name, double price, double discountPrice, int quantity, String provider){
    this.itemId = id;
    this.name = name;
    this.price = price;
    this.discountPrice = discountPrice;
    this.quantity = quantity;
    this.provider = provider;
  }
  public int getId(){ return this.itemId; }
  public void setId(int id){ this.itemId = id; }
  public String getName(){ return this.name; }
  public void setName(String name){ this.name = name; }
  public double getPrice(){ return this.price; }
  public void setPrice(double price){ this.price = price; }
  public double getDiscountPrice(){ return this.discountPrice; }
  public void setDiscountPrice(double discountPrice){ this.discountPrice = discountPrice; }
  public int getQuantity(){ return this.quantity; }
  public void setQuantity(int quantity){ this.quantity = quantity; }
  public String getProvider(){ return this.provider; }
  public void setProvider(String provider){ this.provider = provider; }
};
