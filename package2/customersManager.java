import java.io.File;
import java.util.Scanner;

public class customersManager {
    
    public static Scanner input = new Scanner(System.in);
    public static AVLTree<Integer, Customer> customersIDs = new AVLTree<>();
    public static AVLTree<String, Customer> customersNames = new AVLTree<>();
   
    //==============================================================
    public AVLTree<Integer, Customer> getcustomersIDs() {
        return customersIDs;
    }

    //==============================================================
    public AVLTree<String, Customer> getcustomersNames() {
        return customersNames;
    }
   
    //==============================================================
    public customersManager(String fileName) {
        try {
            File docsfile = new File(fileName);
            Scanner reader = new Scanner(docsfile);
            String line = reader.nextLine(); 
            
            while (reader.hasNext()) {
                line = reader.nextLine();
                String[] data = line.split(",");
                
                int id = Integer.parseInt(data[0].trim());
                String name = data[1].trim();
                String email = data[2].trim();
                
                Customer customer = new Customer(id, name, email);

                // USE GETTERS (important)
                customersIDs.insert(customer.getCustomerId(), customer);
                customersNames.insert(customer.getName(), customer);
            }
            reader.close();
        }  
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
  
    //==============================================================
    public void RegisterCustomer() {
        Customer customer = new Customer();

        System.out.println("Enter customer ID : ");
        customer.setCustomerId(input.nextInt());
        
        while (customersIDs.find(customer.getCustomerId())) {
            System.out.println("Re-Enter again, ID already available: ");
            customer.setCustomerId(input.nextInt());
        }    
        
        System.out.println("Enter customer Name : ");
        input.nextLine();
        customer.setName(input.nextLine());
        
        System.out.println("Enter customer Email : ");
        customer.setEmail(input.nextLine());

        customersIDs.insert(customer.getCustomerId(), customer);
        customersNames.insert(customer.getName(), customer);
    }
    
    //==============================================================
    public void OrderHistory() {
        if (customersIDs.empty()) {
            System.out.println("empty Customers data");
            return;
        }

        System.out.println("Enter customer ID: ");
        int customerID = input.nextInt();
        
        if (customersIDs.find(customerID)) {
            Customer c = customersIDs.retrieve();
            
            if (c.getOrders().empty()) {
                System.out.println("No Order History for " + c.getCustomerId());
            } else {
                System.out.println("Order History:");
                System.out.println(c.getOrders()); 
            }
        } 
        else {
            System.out.println("No such customer ID");
        }
    }
    
    //==============================================================
    public Customer getCustomerID() {
        if (customersIDs.empty()) {
            System.out.println("empty Customers data");
            return null;
        }

        System.out.println("Enter customer ID: ");
        int customerID = input.nextInt();

        if (customersIDs.find(customerID)) {
            System.out.println(customersIDs.retrieve());
            return customersIDs.retrieve();
        }
        
        System.out.println("No such customer ID");
        return null;
    }
    
    //==============================================================
    public void printNamesAlphabetically() {
        customersNames.printKeys();
    }
}
