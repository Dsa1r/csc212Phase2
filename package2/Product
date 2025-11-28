
public class Product {
    int productId;
    String name;
    double price;
    int stock;
    AVLTree <Integer,Integer> reviews = new AVLTree <Integer,Integer> ();

    public Product() {
        this.productId = 0;
        this.name = "";
        this.price = 0;
        this.stock = 0;
    }

    public Product(int pid, String n, double p, int s) {
        this.productId = pid;
        this.name = n;
        this.price = p;
        this.stock = s;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void addStock ( int stock)
    {
        this.stock += stock;
    }
    
    public void removeStock ( int stock)
    {
        this.stock -= stock;
    }
    
    
    public void addReview( Integer R)
    {
        reviews.insert(R, R);
    }
    
    public boolean removeReview( Integer R)
    {
        return reviews.removeKey(R);
    }
    
    public AVLTree<Integer,Integer> getReviews ()
    {
        return reviews;
    }
    
    @Override
    public String toString() {
        String str =  "\nproductId=" + productId + ", name=" + name + ", price=" + price ;
        if ( stock == -1)
            str += "\nProduct had been Archived, not avialable for use ";
        else
            str += ", stock=" + stock; 
        if ( ! reviews.empty())
        {
            str += "(reviews List : " ;
            str += reviews;
            str += " )";
        }
        return str;        
    }
    
    public String getDataToFile()
    {
        return productId + ", " + name + ", " + price + ", " + stock; 
    }
}
