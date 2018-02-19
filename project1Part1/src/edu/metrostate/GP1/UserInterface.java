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
  private static final int ADD_SHOW = 9;
  private static final int LIST_ALL_SHOWS = 10;
  private static final int SAVE = 11;
  private static final int RETRIEVE = 12;
  private static final int HELP = 13;
  
  /**
   * Made private for singleton pattern.
   * Conditionally looks for any saved data. Otherwise, it gets
   * a singleton Library object.
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
    System.out.println(ADD_SHOW + " to  add a new show");
    System.out.println(LIST_ALL_SHOWS + " to  list all shows");
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
   * Method to be called for removing books.
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
          System.out.println("This client has a scheduled show");
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
   * Method to be called for adding a book.
   * Prompts the user for the appropriate values and
   * uses the appropriate Library method for adding the book.
   *  
   */
  public void addBooks() {
    Book result;
    do {
      String title = getToken("Enter  title");
      String bookID = getToken("Enter id");
      String author = getToken("Enter author");
      result = theatre.addBook(title, author, bookID);
      if (result != null) {
        System.out.println(result);
      } else {
        System.out.println("Book could not be added");
      }
      if (!yesOrNo("Add more books?")) {
        break;
      }
    } while (true);
  }
  /**
   * Method to be called for issuing books.
   * Prompts the user for the appropriate values and
   * uses the appropriate Library method for issuing books.
   *  
   */
  public void issueBooks() {
    Book result;
    String memberID = getToken("Enter member id");
    if (theatre.searchMembership(memberID) == null) {
      System.out.println("No such member");
      return;
    }
    do {
      String bookID = getToken("Enter book id");
      result = theatre.issueBook(memberID, bookID);
      if (result != null){
        System.out.println(result.getTitle()+ "   " +  result.getDueDate());
      } else {
          System.out.println("Book could not be issued");
      }
      if (!yesOrNo("Issue more books?")) {
        break;
      }
    } while (true);
  }
  /**
   * Method to be called for renewing books.
   * Prompts the user for the appropriate values and
   * uses the appropriate Library method for renewing books.
   *  
   */
  public void renewBooks() {
    Book result;
    String memberID = getToken("Enter member id");
    if (theatre.searchMembership(memberID) == null) {
      System.out.println("No such member");
      return;
    }
    Iterator issuedBooks = theatre.getBooks(memberID);
    while (issuedBooks.hasNext()){
      Book book = (Book)(issuedBooks.next());
      if (yesOrNo(book.getTitle())) {
        result = theatre.renewBook(book.getId(), memberID);
        if (result != null){
          System.out.println(result.getTitle()+ "   " + result.getDueDate());
        } else {
          System.out.println("Book is not renewable");
        }
      }
    }
  }
  /**
   * Method to be called for returning books.
   * Prompts the user for the appropriate values and
   * uses the appropriate Library method for returning books.
   *  
   */
  public void returnBooks() {
    int result;
    do {
      String bookID = getToken("Enter book id");
      result = theatre.returnBook(bookID);
      switch(result) {
        case Theatre.BOOK_NOT_FOUND:
          System.out.println("No such Book in Library");
          break;
        case Theatre.BOOK_NOT_ISSUED:
          System.out.println(" Book  was not checked out");
          break;
        case Theatre.BOOK_HAS_HOLD:
          System.out.println("Book has a hold");
          break;
        case Theatre.OPERATION_FAILED:
          System.out.println("Book could not be returned");
          break;
        case Theatre.OPERATION_COMPLETED:
          System.out.println(" Book has been returned");
          break;
        default:
          System.out.println("An error has occurred");
      }
      if (!yesOrNo("Return more books?")) {
        break;
      }
    } while (true);
  }
  /**
   * Method to be called for removing books.
   * Prompts the user for the appropriate values and
   * uses the appropriate Library method for removing books.
   *  
   */
  public void removeBooks() {
    int result;
    do {
      String bookID = getToken("Enter book id");
      result = theatre.removeBook(bookID);
      switch(result){
        case Theatre.BOOK_NOT_FOUND:
          System.out.println("No such Book in Library");
          break;
        case Theatre.BOOK_ISSUED:
          System.out.println(" Book is currently checked out");
          break;
        case Theatre.BOOK_HAS_HOLD:
          System.out.println("Book has a hold");
          break;
        case Theatre.OPERATION_FAILED:
          System.out.println("Book could not be removed");
          break;
        case Theatre.OPERATION_COMPLETED:
          System.out.println(" Book has been removed");
          break;
        default:
          System.out.println("An error has occurred");
      }
      if (!yesOrNo("Remove more books?")) {
        break;
      }
    } while (true);
  }
  /**
   * Method to be called for placing a hold.
   * Prompts the user for the appropriate values and
   * uses the appropriate Library method for placing a hold.
   *  
   */
  public void placeHold() {
    String memberID = getToken("Enter member id");
    String bookID = getToken("Enter book id");
    int duration = getNumber("Enter duration of hold");
    int result = theatre.placeHold(memberID, bookID, duration);
    switch(result){
      case Theatre.BOOK_NOT_FOUND:
        System.out.println("No such Book in Library");
        break;
      case Theatre.BOOK_NOT_ISSUED:
        System.out.println(" Book is not checked out");
        break;
      case Theatre.NO_SUCH_MEMBER:
        System.out.println("Not a valid member ID");
        break;
      case Theatre.HOLD_PLACED:
        System.out.println("A hold has been placed");
        break;
      default:
        System.out.println("An error has occurred");
    }
  }
  /**
   * Method to be called for removing a holds.
   * Prompts the user for the appropriate values and
   * uses the appropriate Library method for removing a hold.
   *  
   */
  public void removeHold() {
    String memberID = getToken("Enter member id");
    String bookID = getToken("Enter book id");
    int result = theatre.removeHold(memberID, bookID);
    switch(result){
      case Theatre.BOOK_NOT_FOUND:
        System.out.println("No such Book in Library");
        break;
      case Theatre.NO_SUCH_MEMBER:
        System.out.println("Not a valid member ID");
        break;
      case Theatre.OPERATION_COMPLETED:
        System.out.println("The hold has been removed");
        break;
      default:
        System.out.println("An error has occurred");
    }
  }
  /**
   * Method to be called for processing books.
   * Prompts the user for the appropriate values and
   * uses the appropriate Library method for processing books.
   *  
   */
  public void processHolds() {
    Member result;
    do {
      String bookID = getToken("Enter book id");
      result = theatre.processHold(bookID);
      if (result != null) {
        System.out.println(result);
      } else {
        System.out.println("No valid holds left");
      }
      if (!yesOrNo("Process more books?")) {
        break;
      }
    } while (true);
  }
  /**
   * Method to be called for displaying transactions.
   * Prompts the user for the appropriate values and
   * uses the appropriate Library method for displaying transactions.
   *  
   */
  public void getTransactions() {
    Iterator result;
    String memberID = getToken("Enter member id");
    Calendar date  = getDate("Please enter the date for which you want records as mm/dd/yy");
    result = theatre.getTransactions(memberID,date);
    if (result == null) {
      System.out.println("Invalid Member ID");
    } else {
      while(result.hasNext()) {
        Transaction transaction = (Transaction) result.next();
        System.out.println(transaction.getType() + "   "   + transaction.getTitle() + "\n");
      }
      System.out.println("\n  There are no more transactions \n" );
    }
  }
  /**
   * Method to be called for saving the Library object.
   * Uses the appropriate Library method for saving.
   *  
   */
  private void save() {
    if (theatre.save()) {
      System.out.println(" The library has been successfully saved in the file LibraryData \n" );
    } else {
      System.out.println(" There has been an error in saving \n" );
    }
  }
  /**
   * Method to be called for retrieving saved data.
   * Uses the appropriate Library method for retrieval.
   *  
   */
  private void retrieve() {
    try {
      Theatre tempLibrary = Theatre.retrieve();
      if (tempLibrary != null) {
        System.out.println(" The library has been successfully retrieved from the file LibraryData \n" );
        theatre = tempLibrary;
      } else {
        System.out.println("File doesnt exist; creating new library" );
        theatre = Theatre.instance();
      }
    } catch(Exception cnfe) {
      cnfe.printStackTrace();
    }
  }
  /**
   * Orchestrates the whole process.
   * Calls the appropriate method for the different functionalties.
   *  
   */
  public void process() {
    int command;
    help();
    while ((command = getCommand()) != EXIT) {
      switch (command) {
        case ADD_CLIENT:        addClient();
                                break;
        case REMOVE_CLIENT:         addBooks();
                                break;
        case LIST_ALL_CLIENTS:       issueBooks();
                                break;
        case ADD_CUSTOMER:      returnBooks();
                                break;
        case ADD_CREDITCARD:      removeBooks();
                                break;
        case REMOVE_CUSTOMER:       renewBooks();
                                break;
        case REMOVE_CREDITCARD:        placeHold();
                                break;
        case lIST_ALL_CUSTOMERS:       removeHold();
                                break;
        case ADD_SHOW:      processHolds();
                                break;
        case LIST_ALL_SHOWS:  getTransactions();
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