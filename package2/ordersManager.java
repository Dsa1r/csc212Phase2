import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

public class ordersManager {

    public static Scanner input = new Scanner(System.in);

    public static AVLTree<Integer, Order> orders = new AVLTree<Integer, Order>();

    public AVLTree<Integer, Order> getordersData() {
        return orders;
    }

    public ordersManager(String fileName, AVLTree<Integer, Customer> customers) {
        try {
            File docsfile = new File(fileName);
            Scanner reader = new Scanner(docsfile);
            String line = reader.nextLine();

            while (reader.hasNext()) {
                line = reader.nextLine();
                String[] data = line.split(",");
                int oid = Integer.parseInt(data[0]);
                int cid = Integer.parseInt(data[1]);

                String pp = data[2].replaceAll("\"", "");
                String[] p = pp.split(";");
                Integer[] pids = new Integer[p.length];
                for (int i = 0; i < pids.length; i++)
                    pids[i] = new Integer(Integer.parseInt(p[i].trim()));

                double price = Double.parseDouble(data[3]);
                String date = data[4];
                String status = data[5];

                Order order = new Order(oid, cid, pids, price, date, status);
                orders.insert(oid, order);

                if (customers.find(cid)) {
                    Customer c = customers.retrieve();
                    c.addOrder(order);   // FIXED HERE
                    customers.update(c);
                }
            }
            reader.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    // SAME REST OF YOUR CODE ---- NO CHANGES NEEDED
    public void cancelOrder(int oid) {
        if (orders.find(oid)) {
            if (orders.retrieve().getStatus().compareToIgnoreCase("cancelled") == 0)
                System.out.println("Order " + orders.retrieve().getOrderId() + " was cancelled before");
            else {
                Order data = orders.retrieve();
                data.setStatus("cancelled");
                orders.update(data);
                System.out.println("Order " + data.getOrderId() + " hed been cancelled Now");
            }
        } else
            System.out.println("could not find Order ID");
    }

    public boolean UpdateOrder(int orderID) {
        if (orders.find(orderID)) {
            if (orders.retrieve().getStatus().compareToIgnoreCase("cancelled") == 0) {
                System.out.println("could not change status for cancelled order");
                return false;
            }
            Order obj = orders.retrieve();

            System.out.println("Status of order is " + orders.retrieve().getStatus());
            System.out.println("Enter new status  (pending, shipped, delivered)....");
            String str = input.next();

            while ((str.compareToIgnoreCase("pending") != 0) &&
                    (str.compareToIgnoreCase("shipped") != 0) &&
                    (str.compareToIgnoreCase("delivered") != 0)) {
                System.out.println("bad status , try again ");
                str = input.next();
            }

            obj.setStatus(str);
            orders.update(obj);

            System.out.println("Order (" + orderID + ") had been updated");
            return true;
        }

        System.out.println("No such Order ID");
        return false;
    }

    public Order searchOrderID(int orderID) {
        if (orders.find(orderID))
            return orders.retrieve();
        return null;
    }

    public AVLTree<Date, Order> BetweenTwoDates(String date1, String date2) {

        AVLTree<Date, Order> ordersbetweenDates = new AVLTree<Date, Order>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate Ldate1 = LocalDate.parse(date1, formatter);
        LocalDate Ldate2 = LocalDate.parse(date2, formatter);

        ordersbetweenDates = orders.intervalSearchDate(Ldate1, Ldate2);

        return ordersbetweenDates;
    }

    public boolean checkOrderID(int oid) {
        return orders.find(oid);
    }
}
