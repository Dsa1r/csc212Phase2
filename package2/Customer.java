public class Customer {
    private int customerId;
    private String name;
    private String email;

    // A
    private AVLTree<Integer, Order> orders = new AVLTree<>();

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

    // Return the AVL tree of orders
    public AVLTree<Integer, Order> getOrders() {
        return orders;
    }


    public void addOrder(Order order) {
        if (order != null) {
            orders.insert(order.getOrderId(), order);
        }
    }

  
    public boolean removeOrder(int orderId) {
        return orders.removeKey(orderId);
    }

    @Override
    public String toString() {
        String str = "\nCustomer { ID=" + customerId + 
                     ", Name=" + name + 
                     ", Email=" + email;

        if (!orders.empty()) {
            str += ", Orders: " + orders.toString();
        }

        str += " }";
        return str;
    }


    public String getDataToFile() {
        return customerId + "," + name + "," + email;
    }
}
