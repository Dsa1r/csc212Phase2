import java.io.File;
import java.util.Scanner;

public class productsManager {

    public static Scanner input = new Scanner(System.in);

    public static AVLTree<Integer, Product> productsIDs = new AVLTree<>();
    public static AVLTree<Integer, Product> productsArchived = new AVLTree<>();

    //a==============================================================
    public AVLTree<Integer, Product> getproductsIDs() {
        return productsIDs;
    }

    //==============================================================
    public AVLTree<Integer, Product> getproductsArchived() {
        return productsArchived;
    }

    //==============================================================
    public productsManager(String fileName) {

        try {
            File docsfile = new File(fileName);
            Scanner reader = new Scanner(docsfile);
            String line = reader.nextLine(); // skip header

            while (reader.hasNext()) {
                line = reader.nextLine();
                String[] data = line.split(",");

                int pid = Integer.parseInt(data[0].trim());
                String name = data[1].trim();
                double price = Double.parseDouble(data[2].trim());
                int stock = Integer.parseInt(data[3].trim());

                Product product = new Product(pid, name, price, stock);
                productsIDs.insert(pid, product);
            }
            reader.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    //==============================================================
    public void addProduct() {

        System.out.println("Enter product ID:");
        int pid = input.nextInt();

        while (productsIDs.find(pid)) {
            System.out.println("ID already exists, enter a new product ID:");
            pid = input.nextInt();
        }

        System.out.println("Product Name:");
        String name = input.next();

        System.out.println("Price:");
        double price = input.nextDouble();

        System.out.println("Stock:");
        int stock = input.nextInt();

        Product product = new Product(pid, name, price, stock);
        productsIDs.insert(pid, product);
    }

    //==============================================================
    public Product searchProducID() {

        if (productsIDs.empty()) {
            System.out.println("Products list is empty");
            return null;
        }

        System.out.println("Enter product ID:");
        int pid = input.nextInt();

        if (productsIDs.find(pid)) {
            Product p = productsIDs.retrieve();
            System.out.println(p);
            return p;
        }

        System.out.println("No such product ID");
        return null;
    }

    //==============================================================
    public Product removeProduct() {

        if (productsIDs.empty()) {
            System.out.println("Products list is empty");
            return null;
        }

        System.out.println("Enter product ID:");
        int pid = input.nextInt();

        if (productsIDs.find(pid)) {
            Product p = productsIDs.retrieve();
            productsIDs.removeKey(pid);
            p.setStock(0); // archived

            productsArchived.insert(p.getProductId(), p);
            System.out.println("Product (" + pid + ") archived");
            return p;
        }

        System.out.println("No such product ID");
        return null;
    }

    //==============================================================
    public Product updateProduct() {

        if (productsIDs.empty()) {
            System.out.println("Products list is empty");
            return null;
        }

        System.out.println("Enter product ID:");
        int pid = input.nextInt();

        if (productsIDs.find(pid)) {

            Product p = productsIDs.retrieve();

            System.out.println("1. Update Name");
            System.out.println("2. Update Price");
            System.out.println("3. Update Stock");
            System.out.println("Enter choice:");
            int c = input.nextInt();

            switch (c) {
                case 1:
                    System.out.println("New Name:");
                    p.setName(input.next());
                    break;

                case 2:
                    System.out.println("New Price:");
                    p.setPrice(input.nextDouble());
                    break;

                case 3:
                    System.out.println("New Stock:");
                    p.setStock(input.nextInt());
                    break;

                default:
                    System.out.println("Invalid choice");
            }

            productsIDs.update(p);
            return p;
        }

        System.out.println("No such product ID");
        return null;
    }

    //==============================================================
    public Product searchProducName() {

        if (productsIDs.empty()) {
            System.out.println("Products list is empty");
            return null;
        }

        System.out.println("Enter product Name:");
        input.nextLine();
        String name = input.nextLine();

        LinkedList<Product> inOrder = productsIDs.inOrdertraverseData();

        if (inOrder.empty()) {
            System.out.println("No products found");
            return null;
        }

        inOrder.findFirst();
        while (!inOrder.last()) {

            if (inOrder.retrieve().getName().equalsIgnoreCase(name)) {
                System.out.println("Product found:");
                System.out.println(inOrder.retrieve());
                return inOrder.retrieve();
            }

            inOrder.findNext();
        }

        if (inOrder.retrieve().getName().equalsIgnoreCase(name)) {
            System.out.println("Product found:");
            System.out.println(inOrder.retrieve());
            return inOrder.retrieve();
        }

        System.out.println("No such product name");
        return null;
    }

    //==============================================================
    public void Out_Stock_Products() {
        if (productsIDs.empty())
            System.out.println("Products list is empty");
        else
            productsIDs.inOrdertraverseOutStock();
    }

    //==============================================================
    public boolean checkProductID(int pid) {
        return productsIDs.find(pid);
    }

    //==============================================================
    public Product getProductData(int pid) {
        if (productsIDs.find(pid))
            return productsIDs.retrieve();
        return null;
    }

    //==============================================================
    public LinkedList<Product> getPriceRange(double minPrice, double maxPrice) {
        return productsIDs.intervalSearchprice(minPrice, maxPrice);
    }

    //==============================================================
    public void printTop3RatedProducts() {

        LinkedList<Product> allProducts = productsIDs.inOrdertraverseData();

        if (allProducts.empty()) {
            System.out.println("No products available");
            return;
        }

       
        Product[] arr = new Product[allProducts.size()];
        allProducts.toArray(arr);

     
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {

                if (arr[j].getAverageRating() > arr[i].getAverageRating()) {
                    Product temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }

        System.out.println("\nTop 3 Rated Products:");

        for (int i = 0; i < Math.min(3, arr.length); i++) {
            System.out.println((i + 1) + " - " + arr[i].getName() +
                               " (Avg Rating = " + arr[i].getAverageRating() + ")");
        }
    }
}
