import java.io.File;
import java.util.Scanner;


public class customersManager {
    
    public static Scanner input = new Scanner (System.in);
    public static AVLTree<Integer, Customer> customersIDs = new AVLTree<Integer, Customer> ();
    public static AVLTree<String, Customer> customersNames = new AVLTree<String, Customer> ();
   
 //==============================================================
    public AVLTree<Integer, Customer> getcustomersIDs ( )
    {
        return customersIDs;
    }

 //==============================================================
    public AVLTree<String, Customer> getcustomersNames ( )
    {
        return customersNames;
    }
   
    
//==============================================================
    public customersManager(String fileName)
    {
            try{
                File docsfile = new File(fileName);
                Scanner reader = new Scanner (docsfile);
                String line = reader.nextLine();
                
                while(reader.hasNext())
                {
                    line = reader.nextLine();
                    String [] data = line.split(","); 
                    Customer customer = new Customer(Integer.parseInt(data[0]),data[1], data[2] );
                    customersIDs.insert(customer.customerId, customer);
                    customersNames.insert(customer.name, customer);
                }
                reader.close();
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
    }
  
//==============================================================
public void RegisterCustomer()
{
    Customer customer = new Customer ();

    System.out.println("Enter customer ID : ");
    customer.setCustomerId(input.nextInt());
    
    while (customersIDs.find(customer.getCustomerId()))
    {
        System.out.println("Re-Enter agian, ID already avialable: ");
        customer.setCustomerId(input.nextInt());
    }    
    
    System.out.println("Enter customer Name : ");
    String name = input.nextLine();
    name = input.nextLine();
    customer.setName(name);
    
    System.out.println("Enter customer Email : ");
    customer.setEmail(input.nextLine());

    customersIDs.insert(customer.customerId, customer);
    customersNames.insert(customer.name, customer);
}
    
//==============================================================
public void OrderHistory()
{
        if (customersIDs.empty())
            System.out.println("empty Customers data");
        else
        {
            System.out.println("Enter customer ID: ");
            int customerID = input.nextInt();
            
            if (customersIDs.find(customerID))
            {
                if (customersIDs.retrieve().getOrders().empty())
                    System.out.println("No Order History for " + customersIDs.retrieve().getCustomerId());
                else
                {
                    System.out.println("Order History");
                    System.out.println(customersIDs.retrieve().getOrders());
                }
            }
            else
                System.out.println("No such customer ID");
        }
    }
    
//==============================================================
    public Customer getCustomerID()
    {
        if (customersIDs.empty())
            System.out.println("empty Customers data");
        else
        {
            System.out.println("Enter customer ID: ");
            int customerID = input.nextInt();

            if (customersIDs.find(customerID))
            {
                System.out.println(customersIDs.retrieve());
                return customersIDs.retrieve();
            }
        }
        
        System.out.println("No such customer ID");
        return null;
    }
    
//==============================================================
    public void printNamesAlphabetically()
    {
        customersNames.printKeys();
    }
}
