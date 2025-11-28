import java.time.LocalDate;
import java.util.Date;


public class Customer {
    int customerId;
    String name;
    String email;
    AVLTree<Integer,Integer> orders = new AVLTree<Integer,Integer> ();

    public Customer() {
        this.customerId = 0;
        this.name = "";
        this.email = "";
    }

    public Customer(int customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public AVLTree<Integer,Integer> getOrders ()
    {
       return orders;
    }
    
    public void addOrder ( int order)
    {
        orders.insert(order,order);
    }
    
    
      public boolean removeOrder( int R)
    {
        return orders.removeKey(R);
    }
    
    
    @Override
    public String toString() {
        String str =  "\nCustomers{" + "customerId=" + customerId + ", name=" + name + ", email=" + email ;
        if ( ! orders.empty())
        {
            str += "(orders List : " ;
            str += orders.toString();
        }
        str +=  " }";
        return str;        
    }
    
    public String getDataToFile()
    {
        return customerId + ", " + name + ", " + email; 
    }
}
