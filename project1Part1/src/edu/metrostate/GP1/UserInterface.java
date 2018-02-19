package edu.metrostate.GP1;

import java.util.*;
import java.text.*;
import java.io.*;

/**
 * 
 * This class implements the user interface for the Theatre project.
 * The commands are encoded as integers using a number of
 * static final variables. A number of utility methods exist to
 * make it easier to parse the input.
 *
 */
public class UserInterface {
  private static UserInterface UI;
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  private static Theatre theatre;
  private static final int EXIT = 0;
  private static final int ADD_CLIENT = 1;
  private static final int REMOVE_CLIENT = 2;
  private static final int LIST_ALL_CLIENTS = 3;
  private static final int ADD_CUSTOMER = 4;
  private static final int REMOVE_CUSTOMER = 5;
  private static final int ADD_CREDITCARD = 6;
  private static final int REMOVE_CREDITCARD = 7;
  private static final int lIST_ALL_CUSTOMERS = 8;
  private static final int ADD_PLAY = 9;
  private static final int LIST_ALL_PLAYS = 10;
  private static final int SAVE = 11;
  private static final int RETRIEVE = 12;
  private static final int HELP = 13;
  
  /**
   * Made private for singleton pattern.
   * Conditionally looks for any saved data. Otherwise, it gets
   * a singleton Theatre object.
   */
  private UserInterface() {
    if (yesOrNo("Look for saved data and  use it?")) {
      retrieve();
    } else {
      theatre = Theatre.instance();
    }
  }
  /**
   * Supports the singleton pattern
   * 
   * @return the singleton object
   */
  public static UserInterface instance() {
    if (UI == null) {
      return UI = new UserInterface();
    } else {
      return UI;
    }
  }
  
  /**
   * Gets a token after prompting
   * 
   * @param prompt - whatever the user wants as prompt
   * @return - the token from the keyboard
   * 
   */
  public String getToken(String prompt) {
    do {
      try {
        System.out.println(prompt);
        String line = reader.readLine();
        StringTokenizer tokenizer = new StringTokenizer(line,"\n\r\f");
        if (tokenizer.hasMoreTokens()) {
          return tokenizer.nextToken();
        }
      } catch (IOException ioe) {
        System.exit(0);
      }
    } while (true);
  }
  
  /**
   * Queries for a yes or no and returns true for yes and false for no
   * 
   * @param prompt The string to be prepended to the yes/no prompt
   * @return true for yes and false for no
   * 
   */
  private boolean yesOrNo(String prompt) {
    String more = getToken(prompt + " (Y|y)[es] or anything else for no");
    if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
      return false;
    }
    return true;
  }
  
  /**
   * Converts the string to a number
   * @param prompt the string for prompting
   * @return the integer corresponding to the string
   * 
   */
  public int getNumber(String prompt) {
    do {
      try {
        String item = getToken(prompt);
        Integer number = Integer.valueOf(item);
        return number.intValue();
      } catch (NumberFormatException nfe) {
        System.out.println("Please input a number ");
      }
    } while (true);
  }
  
  /**
   * Prompts for a date and gets a date object
   * @param prompt the prompt
   * @return the data as a Calendar object
   */
  public Calendar getDate(String prompt) {
    do {
      try {
        Calendar date = new GregorianCalendar();
        String item = getToken(prompt);
        DateFormat dateFormat = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
        date.setTime(dateFormat.parse(item));
        return date;
      } catch (Exception fe) {
        System.out.println("Please input a date as mm/dd/yy");
      }
    } while (true);
  }
  
  /**
   * Prompts for a command from the keyboard
   * 
   * @return a valid command
   * 
   */
  public int getCommand() {
    do {
      try {
        int value = Integer.parseInt(getToken("Enter command:" + HELP + " for help"));
        if (value >= EXIT && value <= HELP) {
          return value;
        }
      } catch (NumberFormatException nfe) {
        System.out.println("Enter a number");
      }
    } while (true);
  }
  
  /**
   * Displays the help screen
   * 
   */
  public void help() {
    System.out.println("Enter a number between 0 and 12 as explained below:");
    System.out.println(EXIT + " to Exit\n");
    System.out.println(ADD_CLIENT + " to add a client");
    System.out.println(REMOVE_CLIENT + " to  remove a client");
    System.out.println(LIST_ALL_CLIENTS + "to list all clients ");
    System.out.println(ADD_CUSTOMER + " to  add a customer ");
    System.out.println(REMOVE_CUSTOMER + " to  remove customer ");
    System.out.println(ADD_CREDITCARD + " to  add a creditcard");
    System.out.println(REMOVE_CREDITCARD + " to  remove a creditcard");
    System.out.println(lIST_ALL_CUSTOMERS + " to  to list all customers");
    System.out.println(ADD_PLAY + " to  add a new play");
    System.out.println(LIST_ALL_PLAYS + " to  list all plays");
    System.out.println(SAVE + " to  save data");
    System.out.println(RETRIEVE + " to  retrieve");
    System.out.println(HELP + " for help");
  }
  
  /**
   * Method to be called for adding a client.
   * Prompts the user for the appropriate values and
   * uses the appropriate Theatre method for adding the client.
   *  
   */
  public void addClient() {
    String name = getToken("Enter client name");
    String address = getToken("Enter address");
    String phone = getToken("Enter phone");
    Client result;
    result = theatre.addClient(name, address, phone);
    if (result == null) {
      System.out.println("Could not add client");
    }
    System.out.println(result);
  }
  
  /**
   * Method to be called for removing client.
   * Prompts the user for the appropriate values and
   * uses the appropriate Theatre method for removing clients.
   *  
   */
  public void removeClient() {
    int result;
    String clientID = getToken("Enter client ID");
    result = theatre.removeClient(clientID);
    
    switch(result){
        case Theatre.CLIENT_NOT_FOUND:
          System.out.println("Client not found");
          break;
        case Theatre.HAS_SCHEDULED_PLAY:
          System.out.println("This client has a scheduled play");
          break;
        case Theatre.OPERATION_FAILED:
          System.out.println("Client could not be removed");
          break;
        case Theatre.OPERATION_COMPLETED:
          System.out.println(" Client has been removed");
          break;
        default:
          System.out.println("An error has occurred");
          }
  }
  
  /**
   * Method to be called to list all clients.
   * uses the appropriate Theatre method for listing clients. 
   */
  public void listAllClients() {
	  Client result;
	  result = theatre.getClients(); 
  }
  
  /**
   * Method to be called for adding a customer.
   * Prompts the user for the appropriate values and
   * uses the appropriate Theatre method for adding the customer.
   *  
   */
  public void addCustomer() {
    String name = getToken("Enter customer name");
    String address = getToken("Enter address");
    String phone = getToken("Enter phone");
    String creditCard = getToken("Enter credit card number");
    String expiryDate = getToken("Enter expiration date in mm/yy format");
    Customer result;
    result = theatre.addCustomer(name, address, phone,creditCard);
    if (result == null) {
      System.out.println("Could not add customer");
    }
    System.out.println(result);
  }
  
  /**
   * Method to be called for removing customer.
   * Prompts the user for the appropriate values and
   * uses the appropriate Theatre method for removing customer.
   *  
   */
  public void removeCustomer() {
    int result;
    String customerID = getToken("Enter customer ID");
    result = theatre.removeCustomer(customerID);
    
    switch(result){
        case Theatre.CUSTOMER_NOT_FOUND:
          System.out.println("Customer not found");
          break;
        case Theatre.OPERATION_FAILED:
          System.out.println("Customer could not be removed");
          break;
        case Theatre.OPERATION_COMPLETED:
          System.out.println(" Customer has been removed");
          break;
        default:
          System.out.println("An error has occurred");
          }
  }
  
  
  /**
   * Method to be called for adding a credit card.
   * Prompts the user for the appropriate values and
   * uses the appropriate Theatre method for adding credit card.
   *  
   */
  public void addCreditCard() {
    creditCard result;
    String customerID = getToken("Enter the customer's ID ");
    if (theatre.searchCustomers(customerID) == null)
    {
    	System.out.println("No such customer.");
        return;
    }
    do {
      String creditCard = getToken("Enter credit card numbers");
      String expiryDate = getToken("Enter date in mm/yy format");
      result = theatre.addCreditCard(creditCard, expiryDate);
      if (result != null) {
        System.out.println(result);
      } else {
        System.out.println("Credit card could not be added");
      }
    } while (true);
  }
  
  /**
   * Method to be called for removing credit cards.
   * Prompts the user for the appropriate values and
   * uses the appropriate Theatre method for removing credit cards.
   *  
   */
  public void removeCreditCard() {
    int result;
    String customerID = getToken("Enter customer ID");
    if (theatre.searchCustomers(customerID) == null)
    {
    	System.out.println("No such customer.");
        return;
    }
    do {
      String creditCard = getToken("Enter credit card number");
      result = theatre.removeCreditCard(creditCard);
      switch(result){
        case Theatre.CREDITCARD_NOT_FOUND:
          System.out.println("No such credit card found");
          break;
        case Theatre.CREDITCARD_ONLY_ONE:
          System.out.println("Creditcard can be removed. It's the only one.");
          break;
        case Theatre.OPERATION_FAILED:
          System.out.println("Credit card could not be removed");
          break;
        case Theatre.OPERATION_COMPLETED:
          System.out.println(" Credit card has been removed");
          break;
        default:
          System.out.println("An error has occurred");
      }
    } while (true);
  }
  
  /**
   * Method to be called to list all customers.
   * uses the appropriate Theatre method for listing customers. 
   */
  public void listAllCustomers() {
	  Customers result;
	  result = theatre.getCustomers(); 
  }
  
  /**
   * Method to be called for adding a new play.
   * Prompts the user for the appropriate values and
   * uses the appropriate Theatre method for adding a play.
   *  
   */
  public void addPlay() {
    Play result;
    String clientID = getToken("Enter the client's id");
    if (theatre.searchClients(clientID) == null) {
      System.out.println("No such client");
      return;
    }
    do {
      String playName = getToken("Enter the name for the play");
      Calendar startDate  = getDate("Please enter the begining date as mm/dd/yy");
      Calendar endDate  = getDate("Please enter the date for which you want records as mm/dd/yy");
      result = theatre.addPlay(playName, startDate,endDate);
      if (result != null){
        System.out.println(result.getTitle()+ "   " +  result.getStartDate()+ "-"+ result.getEndDate);
      } else {
          System.out.println("Play could not be added.");
      }
    } while (true);
  }
  
  /**
   * Method to be called to list all plays.
   * uses the appropriate Theatre method for listing plays. 
   */
  public void listAllPlays() {
	  Play result;
	  result = theatre.getPlays(); 
  }

  /**
   * Method to be called for saving the Theatre object.
   * Uses the appropriate Theatre method for saving.
   *  
   */
  private void save() {
    if (theatre.save()) {
      System.out.println(" The theatre has been successfully saved in the file TheatreData \n" );
    } else {
      System.out.println(" There has been an error in saving \n" );
    }
  }
  /**
   * Method to be called for retrieving saved data.
   * Uses the appropriate Theatre method for retrieval.
   *  
   */
  private void retrieve() {
    try {
      Theatre tempTheatre = Theatre.retrieve();
      if (tempTheatre != null) {
        System.out.println(" The theatre has been successfully retrieved from the file TheatreData \n" );
        theatre = tempTheatre;
      } else {
        System.out.println("File doesnt exist; creating new theatre" );
        theatre = Theatre.instance();
      }
    } catch(Exception cnfe) {
      cnfe.printStackTrace();
    }
  }
  
  /**
   * Orchestrates the whole process.
   * Calls the appropriate method for the different functionalities.
   *  
   */
  public void process() {
    int command;
    help();
    while ((command = getCommand()) != EXIT) {
      switch (command) {
        case ADD_CLIENT:        addClient();
                                break;
        case REMOVE_CLIENT:         removeClient();
                                break;
        case LIST_ALL_CLIENTS:       listAllClients();
                                break;
        case ADD_CUSTOMER:      addCustomer();
                                break;
        case ADD_CREDITCARD:      addCreditCard();
                                break;
        case REMOVE_CUSTOMER:       removeCustomer();
                                break;
        case REMOVE_CREDITCARD:        removeCreditCard();
                                break;
        case lIST_ALL_CUSTOMERS:       listAllCustomers();
                                break;
        case ADD_PLAY:      addPlay();
                                break;
        case LIST_ALL_PLAYS:  listAllPlays();
                                break;
        case SAVE:              save();
                                break;
        case RETRIEVE:          retrieve();
                                break;
        case HELP:              help();
                                break;
      }
    }
  }
  
  /**
   * The method to start the application. Simply calls process().
   * @param args not used
   */
  public static void main(String[] args) {
    UI.instance().process();
  }
}