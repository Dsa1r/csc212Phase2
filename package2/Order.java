import java.time.LocalDate;

public class Order {
    private int orderId;
    private int customerReference;
    private AVLTree<Integer, Product> products = new AVLTree<>();
    private double total_price;
    private LocalDate date;
    private String status;

    public Order() {
        this.orderId = 0;
        this.customerReference = 0;
        this.total_price = 0;
        this.status = "";
        this.date = LocalDate.now();
    }

    public Order(int orderId, int customerReference, Product[] productList, double total_price, String date, String status) {
        this.orderId = orderId;
        this.customerReference = customerReference;
        this.total_price = total_price;
        this.date = LocalDate.parse(date);
        this.status = status;

        for (Product p : productList) {
            products.insert(p.getProductId(), p);
        }
    }

   
    public int getOrderId() { return orderId; }
    public int getCustomerReference() { return customerReference; }
    public AVLTree<Integer, Product> getProducts() { return products; }
    public double getTotal_price() { return total_price; }
    public LocalDate getDate() { return date; }
    public String getStatus() { return status; }

  
    public void setStatus(String status) { this.status = status; }
    public void setDate(LocalDate date) { this.date = date; }

   
    public void addProduct(Product p) {
        products.insert(p.getProductId(), p);
    }

    public boolean removeProduct(int pid) {
        return products.removeKey(pid);
    }

    public boolean isBetween(LocalDate start, LocalDate end) {
        return (!date.isBefore(start) && !date.isAfter(end));
    }

    @Override
    public String toString() {
        String s = "\nOrder { ID=" + orderId +
                   ", Customer=" + customerReference +
                   ", Total=" + total_price +
                   ", Status=" + status +
                   ", Date=" + date +
                   ", Products=" + products.toString() +
                   " }";
        return s;
    }
}
