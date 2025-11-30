import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Order {
    int orderId;
    int customerRefrence;
    AVLTree<Integer, Integer> products = new AVLTree<Integer, Integer>();
    double total_price;
    LocalDate date;
    String status;

    public Order() {
        this.orderId = 0;
        this.customerRefrence = 0;
        this.total_price = 0;
        this.status = "";
    }

    public Order(int orderId, int customerRefrence, Integer[] pids, double total_price, String date, String status) {
        this.orderId = orderId;
        this.customerRefrence = customerRefrence;
        this.total_price = total_price;
        this.date = LocalDate.parse(date);
        this.status = status;

        for (int i = 0; i < pids.length; i++)
            products.insert(pids[i], pids[i]);
    }

    public int getOrderId() { return orderId; }
    public int getCustomerRefrence() { return customerRefrence; }
    public AVLTree<Integer, Integer> getProducts() { return products; }
    public double getTotal_price() { return total_price; }
    public LocalDate getDate() { return date; }
    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
    public void setOrderId(int id) { orderId = id; }
    public void setCustomerRefrence(int cid) { customerRefrence = cid; }
     public void setTotal_price(double price) { total_price = price; }
    public void setDate(LocalDate date) { this.date = date; }

    public void addProduct(Integer product) {
        products.insert(product, product);
    }

    public boolean removeProduct(Integer p) {
        return products.removeKey(p);
    }

    public boolean isDateBetween(LocalDate start, LocalDate end) {
        return (!date.isBefore(start) && !date.isAfter(end));
    }

    @Override
    public String toString() {
        String str = "\nOrder{" + "orderId=" + orderId +
                ", customer Refrence=" + customerRefrence +
                ", total price=" + total_price +
                " , status =" + status +
                ", date =" + date;

        if (!products.empty()) {
            str += "( products List";
            str += products;
            str += " )";
        }
        str += " }";
        return str;
    }
  public String getDataToFile() {
    StringBuilder productIds = new StringBuilder();
    
    // Get all product IDs from the AVL tree
    LinkedList<Integer> productList = products.inOrdertraverseData();
    
    if (!productList.empty()) {
        productList.findFirst();
        while (!productList.last()) {
            productIds.append(productList.retrieve()).append(";");
            productList.findNext();
        }
        productIds.append(productList.retrieve());
    }
    
    // Format: orderId,customerRefrence,"product1;product2;...",total_price,date,status
    return orderId + "," + 
           customerRefrence + "," + 
           "\"" + productIds.toString() + "\"," + 
           total_price + "," + 
           date + "," + 
           status;
}
}
