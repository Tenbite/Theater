package edu.metrostate.GP1;
//test
import java.util.*;
import java.io.*;

public class Client implements Serializable {
  private static final long serialVersionUID = 1L;
  private String name;
  private String address;
  private String phone;
  private String id;
  private double balance = 0;
  private static final String CLIENT_STRING = "Cl";

  private List clientPlays = new LinkedList();
  
  /**
   * Represents a single client
   * @param name name of the client
   * @param address address of the client
   * @param phone phone number of the client
   */
  public  Client (String name, String address, String phone,int balance) {
    this.name = name;
    this.address = address;
    this.phone = phone;
    id = CLIENT_STRING + (ClientIdServer.instance()).getId();
    this.balance = balance;
  }
  
  /**
   * Getter for name
   * @return client name
   */
  public String getName() {
    return name;
  }
  /**
   * Getter for phone number
   * @return phone number
   */
  public String getPhone() {
    return phone;
  }
  /**
   * Getter for address
   * @return client address
   */
  public String getAddress() {
    return address;
  }
  /**
   * Getter for id
   * @return client id
   */
  public String getId() {
    return id;
  }
  /**
   * Getter for balance
   * @return client's balance
   */
  public double getBalance()
  {
	  return balance;
  }
  /**
   * Setter for name
   * @param newName client's new name
   */
  public void setName(String newName) {
    name = newName;
  }
  /**
   * Setter for address
   * @param newName client's new address
   */
  public void setAddress(String newAddress) {
    address = newAddress;
  }
  /**
   * Setter for phone
   * @param newName client's new phone
   */
  public void setPhone(String newPhone) {
    phone = newPhone;
  }
  /**
   * setter for balance
   * @param newBalance client's new balance
   */
  public void setBalance(Double newBalance)
  {
	  balance = newBalance;
  }
  /**
   * Checks whether the client is equal to the one with the given id
   * @param id of the client who should be compared
   * @return true iff the client ids match
   */
  public boolean equals(String id) {
    return this.id.equals(id);
  }
  /** 
   *  String form of the client 
   *  
  */
 @Override
  public String toString() {
    String string = "Client name " + name + " address " + address + " id " + id + "phone " + phone;
    string += " has show: [";
    for (Iterator iterator = clientPlays.iterator(); iterator.hasNext(); ) {
      Play play = (Play) iterator.next();
      string += " " + play.getTitle();
    }
    string += "]";
    return string;
  }
}