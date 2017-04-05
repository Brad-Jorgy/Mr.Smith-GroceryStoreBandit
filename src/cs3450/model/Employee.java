package cs3450.model;

import java.io.InputStream;

public class Employee{
  private int employeeId;
  private String name;
  private InputStream image;
  private String username;
  private String password;
  private String position;
  public Employee(int id, String name, InputStream image, String username, String password, String position){
    this.employeeId = id;
    this.name = name;
    this.image = image;
    this.username = username;
    this.password = password;
    this.position = position;
  }
  public int getId(){ return this.employeeId; }
  public void setId(int id){ this.employeeId = id; }
  public String getName(){ return this.name; }
  public void setName(String name){ this.name = name; }
  public InputStream getImage(){ return this.image; }
  public void setImage(InputStream image){ this.image = image; }
  public String getUsername(){ return this.username; }
  public void setUsername(String username){ this.username = username; }
  public String getPassword(){ return this.password; }
  public void setPassword(String password){ this.password = password; }
  public String getPosition(){ return this.position; }
  public void setPosition(String position){ this.position = position; }
  public boolean isManager(){ return this.position == "Manager"; }
};
