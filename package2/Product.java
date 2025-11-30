public class Product {
    private int productId;
    private String name;
    private double price;
    private int stock;

   //a
    private AVLTree<Integer, Review> reviews = new AVLTree<>();

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

  
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public void addStock(int value) { this.stock += value; }
    public void removeStock(int value) { this.stock -= value; }

    
    public void addReview(Review r) {
        if (r != null)
            reviews.insert(r.getReviewId(), r);
    }

    public boolean removeReview(int reviewId) {
        return reviews.removeKey(reviewId);
    }

    public AVLTree<Integer, Review> getReviews() {
        return reviews;
    }

    
    public double getAverageRating() {
        if (reviews.empty()) return 0;

        int[] sum = {0};
        int[] count = {0};

        reviews.inOrder((key, reviewObj) -> {
            sum[0] += reviewObj.getRating();
            count[0]++;
        });

        return (count[0] == 0) ? 0 : (double) sum[0] / count[0];
    }

    @Override
    public String toString() {
        return "\nProduct { ID=" + productId +
               ", Name=" + name +
               ", Price=" + price +
               ", Stock=" + stock +
               ", Reviews=" + reviews.toString() +
               " }";
    }

    public String getDataToFile() {
        return productId + "," + name + "," + price + "," + stock;
    }
}
