import java.io.File;
import java.util.Scanner;


public class productsManager {
    
    public static Scanner input = new Scanner (System.in);
    public static AVLTree<Integer, Product> productsIDs = new AVLTree<Integer, Product> ();
    public static AVLTree<Integer, Product> productsArchived = new AVLTree<Integer, Product> ();
    
//==============================================================
    public AVLTree<Integer, Product>  getproductsIDs ( )
    {
        return productsIDs;
    }

//==============================================================
    public AVLTree<Integer, Product>  getproductsArchived ( )
    {
        return productsArchived;
    }

    
//==============================================================
    public productsManager ( String fileName)
    {
        
       try{
                File docsfile = new File(fileName);
                Scanner reader = new Scanner (docsfile);
                String line = reader.nextLine();
                
                while(reader.hasNext())
                {
                    line = reader.nextLine();
                    String [] data = line.split(","); 
                    
                    int pid = Integer.parseInt(data[0]);
                    String name =  data[1].trim();
                    double price = Double.parseDouble(data[2]);
                    int stock = Integer.parseInt(data[3]);

                    Product product = new Product(pid, name, price, stock );
                   productsIDs.insert(pid,product);
                }
                reader.close();
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
    }
 
//==================================================================    
    public void addProduct()
    {
        System.out.println("Enter data productID:");
        int pid = input.nextInt();
        while (productsIDs.find(pid))
        {
            System.out.println("Re-Enter productID again , number already taken:");
            pid = input.nextInt();
        }
        
        System.out.println("product Name:");
        String name =input.next();
        
        System.out.println("price:");
        double price = input.nextDouble();
        
        System.out.println("stock:");
        int stock = input.nextInt();
        
        Product product = new Product(pid, name, price, stock );
       productsIDs.insert(pid,product);

    }
//==================================================================    
    public Product searchProducID()
    {
        if (productsIDs.empty())
        {
            System.out.println("empty Products data");
        }
        else
        {
            System.out.println("Enter product ID: ");
            int productID = input.nextInt();
            
            if (productsIDs.find(productID))
            {
                Product p = productsIDs.retrieve();
                System.out.println(p);
                return p;
            }
                
        }
        System.out.println("No such product ID");
        return null;
    }

//==================================================================    
    public Product removeProduct()
    {
        if (productsIDs.empty())
        {
            System.out.println("empty Products data");
        }
        else
        {
            System.out.println("Enter product ID: ");
            int productID = input.nextInt();
            
            if (productsIDs.find(productID))
            {
                Product product = productsIDs.retrieve();
                productsIDs.removeKey(productID);
                product.setStock(0);
                productsArchived.insert(product.getProductId(), product);
                System.out.println("Product (" + product.getProductId() + " ) removed to archived files");
                return product;
            }
            System.out.println("No such product ID");
        }
        return null;
    }

//==================================================================    
    public Product updateProduct()
    {
        if (productsIDs.empty())
        {
            System.out.println("empty Products data");
        }
        else
        {
            System.out.println("Enter product ID: ");
            int productID = input.nextInt();
            
            if ( productsIDs.find(productID))
            {
                Product product = productsIDs.retrieve();
               
                System.out.println("1. Update Name");
                System.out.println("2. Update price");
                System.out.println("3. Update stock");
                System.out.println("Enter your choice");
                int choice = input.nextInt();
                
                switch ( choice )
                {
                    case 1:
                    {
                        System.out.println("product Name:");
                        product.setName(input.next());
                    }
                    break;
                    case 2:
                    {
                        System.out.println("price:");
                        product.setPrice( input.nextDouble());
                    }
                    break;
                    case 3:
                    {
                        System.out.println("stock:");
                        int stock = input.nextInt();
                        product.setStock(stock);
                    }
                    break;
                    default:
                        System.out.println("Bad Choice");
                }
                productsIDs.update(product);
                return product;
            }
        }
        System.out.println("No such product ID");
        return null;
    }

//==================================================================    
    public Product searchProducName()
    {
        if (productsIDs.empty())
            System.out.println("empty Products data");
        else
        {
            System.out.println("Enter product Name: ");
            String name = input.nextLine();
            name = input.nextLine();
            
            LinkedList < Product> productsNames = productsIDs.inOrdertraverseData();
            
            if (productsNames.empty())
                System.out.println("No such product Name");
            else
            {
                productsNames.findFirst();
                while (! productsNames.last())
                {
                    if (productsNames.retrieve().getName().compareToIgnoreCase(name) == 0)
                    {
                        System.out.println("Product Name found ");
                        System.out.println(productsNames.retrieve());
                        return productsNames.retrieve();
                    }
                    productsNames.findNext();
                }
                if (productsNames.retrieve().getName().compareToIgnoreCase(name) == 0)
                {
                    System.out.println("Product Name found ");
                    System.out.println(productsNames.retrieve());
                    return productsNames.retrieve();
                }
            }
        }
        System.out.println("No such product Name");
        return null;
    }

//==================================================================    
    public void Out_Stock_Products()
    {
        if (productsIDs.empty())
            System.out.println("empty Products data");
        else
            productsIDs.inOrdertraverseOutStock();
    }

//==============================================================
    public boolean checkProductID( int PID )
    {
        return productsIDs.find(PID);
    }

    //==============================================================
    public Product getProductData ( int PID )
    {
        if (productsIDs.find(PID))
            return productsIDs.retrieve();
        return null;
    }

    //==============================================================
    /*
    List All Products Within a Price Range.
    */
    public LinkedList <Product> getPriceRange( double minPrice, double maxPrice)
    {
        LinkedList <Product> data = productsIDs.intervalSearchprice(minPrice, maxPrice);
        return data;
    }
    
}
