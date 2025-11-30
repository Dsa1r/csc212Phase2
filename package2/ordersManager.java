public class ordersManager {
//a
    public static Scanner input = new Scanner(System.in);
    public static AVLTree<Integer, Order> orders = new AVLTree<>();

    public ordersManager(String fileName, 
                         AVLTree<Integer, Customer> customers, 
                         AVLTree<Integer, Product> products) {
        try {
            File file = new File(fileName);
            Scanner r = new Scanner(file);
            String header = r.nextLine();

            while (r.hasNext()) {
                String line = r.nextLine();
                String[] data = line.split(",");

                int oid = Integer.parseInt(data[0]);
                int cid = Integer.parseInt(data[1]);

                // Product IDs in CSV
                String pp = data[2].replaceAll("\"", "");
                String[] p = pp.split(";");

                Product[] productList = new Product[p.length];
                for (int i = 0; i < p.length; i++) {
                    int pid = Integer.parseInt(p[i].trim());
                    if (products.find(pid))
                        productList[i] = products.retrieve();
                }

                double price = Double.parseDouble(data[3]);
                String date = data[4];
                String status = data[5];

                Order order = new Order(oid, cid, productList, price, date, status);
                orders.insert(oid, order);

                if (customers.find(cid)) {
                    Customer c = customers.retrieve();
                    c.addOrder(order); // IMPORTANT FIX
                    customers.update(c);
                }
            }
            r.close();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public boolean checkOrderID(int oid) {
        return orders.find(oid);
    }

    public Order searchOrderID(int id) {
        if (orders.find(id))
            return orders.retrieve();
        return null;
    }
}
