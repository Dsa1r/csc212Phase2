import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class Order {
    int orderId;
    int customerRefrence;
    AVLTree <Integer,Integer> products = new AVLTree <Integer,Integer> ();  
    double total_price;
    LocalDate date;
    String status; // (pending, shipped, delivered, cancelled)

    public Order() {
        this.orderId = 0;
        this.customerRefrence = 0;
        this.total_price = 0;
        this.status = "";
    }

    public Order(int orderId, int customerRefrence, Integer [] pids, double total_price, String date, String status) {
        this.orderId = orderId;
        this.customerRefrence = customerRefrence;
        this.total_price = total_price;
        this.date = LocalDate.parse(date);
        this.status = status;
        
        for (int i = 0 ; i < pids.length ; i++)
          products.insert(pids[i],pids[i]);
    }

    public int getOrderId() {
        return orderId;
    }

    public int getCustomerRefrence() {
        return customerRefrence;
    }

    public AVLTree<Integer,Integer> getProducts() {
        return products;
    }

    public double getTotal_price() {
        return total_price;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setCustomerRefrence(int customerRefrence) {
        this.customerRefrence = customerRefrence;
    }

    public void setProducts(int pid) {
        this.products.insert(pid, pid);
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void addProduct (Integer product )
    {
        products.insert(product, product);
    }

    public boolean removeProduct( Integer P)
    {
        return products.removeKey(P);
    }
   
    @Override
    public String toString() {
        String str =  "\nOrder{" + "orderId=" + orderId + ", customer Refrence=" + customerRefrence 
                + ",total price=" + total_price 
                + " , status =" + status
                + ", date =" + date;
        if ( ! products.empty())
        {
            str += "( products List" ;
            str += products;
            str += " )";
        }
        str +=  " }";
        return str;        
    }

    public String getDataToFile()
    {
        String str = "";
        if ( ! products.empty())
        {
            str += products;
        }
        
        str = str.replaceAll(", ", "; ");
        
        return orderId + ", " + customerRefrence + ", " + str
                    + ", " + total_price + ", " + date + ", " + status;
    }

}
