
        
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

public class Main {
    
    //=============================================================================
    // global variables
    //=============================================================================
    public static Scanner input = new Scanner (System.in);

    public static productsManager pdata  = new productsManager("prodcuts.csv");
    public static AVLTree<Integer, Product>  products =pdata.getproductsIDs();

    public static customersManager cdata = new customersManager("customers.csv");
    public  static AVLTree<Integer, Customer> customers = cdata.getcustomersIDs();

     public static ordersManager odata = new ordersManager("orders.csv" , cdata.getcustomersIDs());
     public static AVLTree<Integer, Order>  orders = odata.getordersData();

     public static reviewsManager rdata= new reviewsManager("reviews.csv", pdata.getproductsIDs());
     public static LinkedList <Review>  reviews = rdata.getreviewsData();
 
    //=============================================================================
    public static void productsMenu()
    {
     
        int choice;
        System.out.println("1. Add New Product");
        System.out.println("2. Remove a Product");
        System.out.println("3. Update a Product (name, price, stock)");
        System.out.println("4. Search Product by ID");
        System.out.println("5. Search Product by Name");
        System.out.println("6. List All Products Within a Price Range[minPrice, maxPrice]");
        System.out.println("7. Track All Out-Of-Stock Products");
        System.out.println("8. Display All Customers Who Reviewed Specific Product Sorted by RATING");
        System.out.println("9. Display All Customers Who Reviewed Specific Product Sorted by CUSTOMER ID");
        System.out.println("10. Print All Products");
        System.out.println("11. Return to Main Menu");
        System.out.println("Enter your choice");
        choice = input.nextInt();
        switch (choice)
        {
            case 1:
                pdata.addProduct();
                break;                
            
            case 2:
                pdata.removeProduct();
                break;
            
            case 3:
                pdata.updateProduct();
                break;
            
            case 4:
                pdata.searchProducID();
                break;
            
            case 5:
                pdata.searchProducName();
                break;
            
            case 6:
            {
                System.out.println("Enter range [minPrice, maxPrice]:");
                System.out.println("minPrice:");
                double minPrice = input.nextDouble();
                System.out.println("maxPrice:");
                double maxPrice = input.nextDouble();
                
                while (minPrice >= maxPrice)
                {
                    System.out.println("Re-enter range [minPrice must be less than maxPrice]:");
                    System.out.println("minPrice:");
                    minPrice = input.nextDouble();
                    System.out.println("maxPrice:");
                    maxPrice = input.nextDouble();
                }
                LinkedList<Product> data = pdata.getPriceRange(minPrice, maxPrice);
                if (data.empty())
                     System.out.println("No products in this range");
                else
                    data.print();
            }
            break;
            
            case 7:
                pdata.Out_Stock_Products();
                break;
            
            case 8:
                Customers_Reviewed_product_sort_RATING();
                break;
    
            case 9:
                Customers_Reviewed_product_sort_CID();
                break;
                
            case 10:
                products.printData();
                break;
        
            case 11:
                break;
                
            default:
                System.out.println("Bad choice, Return to Main menu");
        }
    }
    
    //=============================================================================
    public static void Customers_Reviewed_product_sort_RATING()
    {
        LinkedPQ<Customer> Rating = new LinkedPQ<Customer> ();
        
        
        System.out.println("Enter product ID :");
        int pID =input.nextInt();
        while ( ! products.find(pID))
        {
            System.out.println("product ID not available, Re-enter again");
            pID =input.nextInt();
        }

        if ( reviews.empty())
        {
            System.out.println("No reviews are available");
            return;
        }
        
        reviews.findFirst();
        while ( ! reviews.last())
        {
            if (reviews.retrieve().getProduct() == pID )
                if (customers.find(reviews.retrieve().getCustomer()))
                    Rating.enqueue(customers.retrieve(), reviews.retrieve().getRating());
            reviews.findNext();
        }
        if (reviews.retrieve().getProduct() == pID )
            if (customers.find(reviews.retrieve().getCustomer()))
                Rating.enqueue(customers.retrieve(), reviews.retrieve().getRating());

        while ( Rating.length() > 0)
        {
            PQElement pq = Rating.serve();
            System.out.print(pq.data);
            System.out.println("Rating " + pq.priority);
            System.out.println("");
        }
        
    }

    //=============================================================================
    public static void Customers_Reviewed_product_sort_CID()
    {
        AVLTree < Integer, Customer> CIDs = new AVLTree< Integer, Customer> ();
        
        System.out.println("Enter product ID :");
        int pID =input.nextInt();
        while ( ! products.find(pID))
        {
            System.out.println("product ID not available, Re-enter again");
            pID =input.nextInt();
        }

        if ( reviews.empty())
        {
            System.out.println("No reviews are available");
            return;
        }
        
        reviews.findFirst();
        while ( ! reviews.last())
        {
            if (reviews.retrieve().getProduct() == pID )
                if (customers.find(reviews.retrieve().getCustomer()))
                    CIDs.insert(customers.retrieve().getCustomerId(), customers.retrieve());
            reviews.findNext();
        }
        if (reviews.retrieve().getProduct() == pID )
            if (customers.find(reviews.retrieve().getCustomer()))
                CIDs.insert(customers.retrieve().getCustomerId(), customers.retrieve());
        
        CIDs.printKeys_Data();
 }
    
//=============================================================================
    public static void CustomersMenu()
    {
        int choice;
        System.out.println("1. Register new customer");
        System.out.println("2. Search Customer by ID");
        System.out.println("3. Place New Order for specific customer");
        System.out.println("4. View Order history for specific customer");
        System.out.println("5. List All Customers Sorted Alphabetically.");
        System.out.println("6. Extract reviews from a specific customer for all products.");
        System.out.println("7. List All Cusomers");
        System.out.println("8. Return to Main menu");
        System.out.println("Enter your choice");
        choice = input.nextInt();;
        switch (choice)
        {
            case 1:
                cdata.RegisterCustomer();
                break;
            case 2:
                cdata.getCustomerID() ;
                break;
            case 3:
                PlaceOrder();
                break;
            case 4:
                cdata.OrderHistory();
                break;
            case 5:
                cdata.printNamesAlphabetically();
                break;
            case 6:
                AllReviewsForCustomer();
                break;
            case 7:
                customers.printData();
                break;
            case 8:
                break;
            default:
                System.out.println("Bad choice, Return to Main menu");
        }
    }
    
    //=================================================================
    //return all the reviews for spececific customer
    public static LinkedList <Review> AllReviewsForCustomer()
    {
            System.out.println("Enter customer ID:");
            int cid = input.nextInt();
            while(! customers.find(cid))
            {
                System.out.println("Re-enter customer ID, is not available , try again");
                cid = input.nextInt();
            }
        
            // collecting all reviews
            LinkedList <Review> selectedReviews = new LinkedList<Review> ();
            reviews.findFirst();
            while (! reviews.last())
            {
                if ( reviews.retrieve().getCustomer() == cid)
                    selectedReviews.insert(reviews.retrieve());
                reviews.findNext();
            }
            if ( reviews.retrieve().getCustomer() == cid)
                selectedReviews.insert(reviews.retrieve());

            // printing all reviews
            if (selectedReviews.empty())
                System.out.println("No reviews for customer " + cid);
            else
            {
                int count=0;
                selectedReviews.findFirst();
                while ( ! selectedReviews.last())
                {
                    count++;
                    System.out.println(count +":"+ selectedReviews.retrieve());
                    if (products.find(selectedReviews.retrieve().getProduct()))
                        System.out.println(products.retrieve());
                    else
                        System.out.println("Product has been deleted!");
                    System.out.println("");
                    
                    selectedReviews.findNext();
                }
                count++;
                System.out.println(count+":"+selectedReviews.retrieve());
                if (products.find(selectedReviews.retrieve().getProduct()))
                    System.out.println(products.retrieve());
                else
                    System.out.println("Product has been deleted!");
                System.out.println("");

            }
            return selectedReviews;
    }
    
    //=================================================================
    public static void OrdersMenu()
    {
        int choice;
        System.out.println("1. Place New Order");
        System.out.println("2. Cancel Order");
        System.out.println("3. Update Order status");
        System.out.println("4. Search By ID");
        System.out.println("5. List All orders between two dates");
        System.out.println("6. Return to Main menu");
        System.out.println("Enter your choice");
        choice = input.nextInt();
        
        switch (choice)
        {
            case 1:
                PlaceOrder();
                break;
            case 2:
            {
                System.out.println("Enter order id :");
                int oid = input.nextInt();
                odata.cancelOrder(oid);
            }
            break;
            case 3:
            {
                System.out.println("Enter order id :");
                int oid = input.nextInt();
                odata.UpdateOrder(oid);
            }
            break;
            case 4:
            {
                System.out.println("Enter order id :");
                int oid = input.nextInt();
                if (orders.find(oid))
                    System.out.println(orders.retrieve());
                else
                    System.out.println("No order id ");
            }
            break;

            case 5:
            {
                System.out.println("Enter first date (dd/MM/yyyy)");
                String date1 = input.next();
                
                System.out.println("Enter second date (dd/MM/yyyy)");
                String date2 = input.next();
                
                AVLTree<Date, Order> o = odata.BetweenTwoDates(date1, date2);
                o.printData();
            }
            break;
            case 6:
                break;

            default:
                System.out.println("Bad choice, Return to Main menu");
        }
    }
    
    //=================================================================
    public static void PlaceOrder()
    {
            Order new_order = new Order ();
            int total_price = 0;
            
            System.out.println("Enter order ID: ");
            int oid = input.nextInt();
            while ( orders.find(oid))
            {
                System.out.println("ُEntered ID is already used, Re-enter order id");
                oid = input.nextInt();
            }
            new_order.setOrderId(oid);
            
            System.out.println("Enter customer ID:");
            int cid = input.nextInt();
            while(! customers.find(cid))
            {
                System.out.println("No such customer ID, Re-enter customer ID");
                cid = input.nextInt();
            }
            new_order.setCustomerRefrence(cid);
            Customer cus = customers.retrieve();
            cus.addOrder(oid);
            customers.update(cus);
            System.out.println("Order-" + oid + " placed for customer-" + cid );

            char answer = 'y';
            while (answer == 'y' || answer == 'Y')
            {
                System.out.println("Enter product ID:");
                int pid = input.nextInt();
               
                if (products.find(pid))
                {
                        if (products.retrieve().getStock() == 0)
                            System.out.println("product out stock , try another time");
                        else
                        {
                            Product pp = products.retrieve();
                            pp.setStock(pp.getStock()-1);
                            products.update(pp);
                            new_order.addProduct(pid);
                        
                            System.out.println("product added to order");
                            
                            total_price += pp.getPrice();
                        }
                }
                else
                    System.out.println("No such product ID");
                    
                
                System.out.println("Do you want to continue adding product? (Y/N)");
                answer = input.next().charAt(0);
            }
            
            new_order.setTotal_price(total_price);
            
            System.out.println("Enter order date (dd/mm/yyyy)");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate Ldate = LocalDate.parse(input.next(), formatter);
            new_order.setDate(Ldate);
     
            System.out.println("Enter order status  (pending, shipped, delivered)");
            new_order.setStatus(input.next());
            
            orders.insert(new_order.getOrderId(), new_order);
               
            System.out.println("Order had been added ");
            System.out.println(new_order);
    }
    
    //=================================================================
    public static void ReviewsMenu()
    {
        System.out.println("1. Add review");
        System.out.println("2. Edit review (rating - comment)");
        System.out.println("3. Get an average rating for product");
        System.out.println("4. Show the Top 3 Highest AVG Rated Products");
        System.out.println("5. Show the Top 3 Most Reviewed.");
        System.out.println("6. Common products with an average rating 4 and more between 2 cusomers");
        System.out.println("7. Return Main menu");
        System.out.println("Enter your choice");
        int choice = input.nextInt();;
        switch (choice)
        {
            case 1:
                AddNewReview();
                break;
            case 2:
                rdata.updateReview();
                break;
            case 3:
            {
                System.out.println("Enter product ID to Get an average rating :");
                int pid = input.nextInt();

                while (!products.find(pid))
                {
                    System.out.println("ID is not available, Re-Enter ID");
                    pid = input.nextInt();
                }
                float AVG = avgRating(pid);
                System.out.println("Average Rating for " + pid + " is " + AVG);
            }
            break;
            case 4:
                top3ProductsRating();
                break;
            case 5:
                top3ProductsReviewd();
                break;
            case 6:   
            {
                Customer cid1 =cdata.getCustomerID();
                Customer cid2 =cdata.getCustomerID();
                commonProducts(cid1.getCustomerId(), cid2.getCustomerId());
            }
            break;
            case 7:
                break;
            default:
                System.out.println("Bad choice, Return to Main menu");
        }
    }
    
    //=================================================================
    public static void AddNewReview()
    {
        System.out.println("Enter cutomer Id :");
        int cID =input.nextInt();
        while ( ! customers.find(cID))
        {
            System.out.println("customer ID not available, Re-enter again");
            cID =input.nextInt();
        }
        
        System.out.println("Enter product ID :");
        int pID =input.nextInt();
        while ( ! products.find(pID))
        {
            System.out.println("product ID not available, Re-enter again");
            pID =input.nextInt();
        }
        
        Review review = rdata.AddReview(cID, pID);
        System.out.println("New Review( " + review.getReviewId() 
                + ") added for " + review.getProduct() 
                + " by customer (" + review.getCustomer() +"), Rate(" + review.getRating() + ") with comment: " + review.getComment() +"." );
    }
    
    //=================================================================
    //Get an average rating for product.
    public static float avgRating(int pid)
    {
        int counter =0;
        float rate = 0;
        
        reviews.findFirst();
        while (! reviews.last())
        {
            if (reviews.retrieve().getProduct() == pid)
            {
                counter ++;
                rate += reviews.retrieve().getRating();
            }
            reviews.findNext();
        }
        if (reviews.retrieve().getProduct() == pid)
        {
            counter ++;
            rate += reviews.retrieve().getRating();
        }
        
        return (rate/counter);
    }

    //=================================================================
    public static void top3ProductsReviewd()
    {
        LinkedPQ<Product> top3 = new LinkedPQ<Product> ();

        LinkedList<Product> allProducts = products.inOrdertraverseData();
        
        if (!allProducts.empty())
        {
            allProducts.findFirst();
            for (int i = 0 ; i < allProducts.size() ; i++)
            {
                Product p = allProducts.retrieve();
                
                int ReviewdCounter = 0;
                reviews.findFirst();
                while ( ! reviews.last())
                {
                    if ( reviews.retrieve().getProduct() == p.getProductId())
                        ReviewdCounter += 1;
                    reviews.findNext();
                }                
                if ( reviews.retrieve().getProduct() == p.getProductId())
                    ReviewdCounter += 1;

                top3.enqueue(p, ReviewdCounter);
                
                allProducts.findNext();
            }
        }
        
        System.out.println("top 3 products reviwed from most to least.");
        for ( int j = 1 ; j <= 3 && top3.length() > 0 ; j++)
        {
            PQElement<Product> top = top3.serve();
            System.out.println("Product " + j + "  " + top.data.getProductId() 
                    + " " + top.data.getName() + " number of reviews (" + top.priority + ")" );
        }
    }
    //=================================================================
    public static void top3ProductsRating()
    {
        LinkedPQ<Product> top3 = new LinkedPQ<Product> ();

        LinkedList<Product> allProducts = products.inOrdertraverseData();
        
        if (!allProducts.empty())
        {
            allProducts.findFirst();
            for (int i = 0 ; i < allProducts.size() ; i++)
            {
                
                Product p = allProducts.retrieve();
                float AVGrating = avgRating (p.productId);
                top3.enqueue(p, AVGrating);
                
                allProducts.findNext();
            }
        }
        
        System.out.println("top 3 products by average rating from most to least.");
        for ( int j = 1 ; j <= 3 && top3.length() > 0 ; j++)
        {
            PQElement<Product> top = top3.serve();
            System.out.println("Product " + j + "  " + top.data.getProductId() 
                    + " " + top.data.getName() + " AVG rate (" + top.priority + ")" );
        }
    }
    
    //=================================================================
    //Given two customers IDs, show a list of common products that have been 
    //reviewed with an average rating of more than 4 out of 5.
    public static void commonProducts(int cid1, int cid2) {
    LinkedPQ<Product> highRatedProducts = new LinkedPQ<Product>();

    // Get all products from AVL tree using inorder traversal
    LinkedList<Product> allProducts = products.inOrdertraverseData();
    
    if (!allProducts.empty()) {
        allProducts.findFirst();
        for (int i = 0; i < allProducts.size(); i++) {
            Product product = allProducts.retrieve();
            int productId = product.getProductId();

            // Check if both customers reviewed this product
            boolean customer1Reviewed = false;
            boolean customer2Reviewed = false;

            reviews.findFirst();
            for (int j = 0; j < reviews.size(); j++) {
                Review review = reviews.retrieve();
                if (review.getProduct() == productId) {
                    if (review.getCustomer() == cid1)
                        customer1Reviewed = true;
                    if (review.getCustomer() == cid2)
                        customer2Reviewed = true;
                }
                reviews.findNext();
            }

            // If both reviewed it and average rating >= 4, add to results
            if (customer1Reviewed && customer2Reviewed) {
                float avgRating = avgRating(productId);
                if (avgRating >= 4) {
                    highRatedProducts.enqueue(product, avgRating);
                }
            }

            allProducts.findNext();
        }
    }

    // Display results
    if (highRatedProducts.length() > 0) {
        System.out.println("Common Products with average rate above 4 are:");
        while (highRatedProducts.length() > 0) {
            PQElement<Product> productRate = highRatedProducts.serve();
            System.out.println("Product (" + productRate.data.getProductId() + ") " +
                    productRate.data.getName() + " with average rate " + productRate.priority);
        }
    } else {
        System.out.println("No common products found with rating 4 or higher between these customers.");
    }
}
    
    //=================================================================
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        
        System.out.println("loading data from CVSs files");
        int choice;
        do {
                System.out.println("*********************");
                System.out.println("             AVL TREE   ");
                System.out.println("*********************");
                System.out.println("1. Products");
                System.out.println("2. Customers");
                System.out.println("3. Orders");
                System.out.println("4. Reviews");
                System.out.println("5. Exist with saving all data to files");
                System.out.println("6. Exist (without saving)");
                System.out.println("Enter your choice");
                choice = input.nextInt();
                
               switch (choice)
                {
                    case 1:
                        productsMenu();
                        break;
                    case 2:
                        CustomersMenu();
                        break;
                    case 3:
                        OrdersMenu();
                        break;
                    case 4:
                        ReviewsMenu();
                        break;
                    case 5:
                        System.out.println("Exit with saving all data to files");
                        customers.LoadToFile("customers.txt");
                        System.out.println("Successfully wrote to customers file.");

                        products.LoadToFile("products.txt");
                        System.out.println("Successfully wrote to products file.");

                        pdata.getproductsArchived().LoadToFile("Archivedproducts.txt");
                        System.out.println("Successfully wrote to archived products file.");
                        
                        reviews.LoadToFile("reviews.txt");
                        System.out.println("Successfully wrote to reviews file.");

                        orders.LoadToFile("orders.txt");
                        System.out.println("Successfully wrote to orders file.");
                        break;
                    case 6 :
                        break;
                    default:
                        System.out.println("Bad choice, Try again");
                }
        }while (choice != 5);
    }
    
}
