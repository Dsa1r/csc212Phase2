import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class reviewsManager {
    
    public static Scanner input = new Scanner(System.in);
    public static LinkedList<Review> reviews = new LinkedList<Review>();

    //==============================================================
    public LinkedList<Review> getreviewsData() {
        return reviews;
    }

    //==============================================================
    public reviewsManager(String fileName, AVLTree<Integer, Product> products) {

        try {
            File docsfile = new File(fileName);
            Scanner reader = new Scanner(docsfile);
            String line = reader.nextLine(); // skip header

            while (reader.hasNext()) {
                line = reader.nextLine();
                String[] data = line.split(",");

                int rid = Integer.parseInt(data[0]);
                int pid = Integer.parseInt(data[1]);
                int cid = Integer.parseInt(data[2]);
                int rating = Integer.parseInt(data[3]);
                String comment = data[4];

                // Create Review object
                Review review = new Review(rid, pid, cid, rating, comment);

                // Add the review to the linked list
                reviews.insert(review);

                // Add review into Product AVLTree
                if (products.find(pid)) {
                    Product p = products.retrieve();

                    // FIXED: Pass Review object, not int
                    p.addReview(review);

                    products.update(p);
                }
            }

            reader.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    //==============================================================
    public Review AddReview(int cID, int pID) {

        System.out.println("Enter Review ID :");
        int reviewID = input.nextInt();

        while (checkReviewID(reviewID)) {
            System.out.println("Re-enter Review ID, already available :");
            reviewID = input.nextInt();
        }

        System.out.println("Enter rating (5..1): ");
        int rate = input.nextInt();
        while (rate < 1 || rate > 5) {
            System.out.println("Re-Enter rating (5..1):");
            rate = input.nextInt();
        }

        System.out.println("Enter comment: ");
        String comment = input.nextLine();
        comment = input.nextLine();

        Review review = new Review(reviewID, pID, cID, rate, comment);

        reviews.findLast();
        reviews.insert(review);

        return review;
    }

    //==============================================================
    public void updateReview() {

        if (reviews.empty()) {
            System.out.println("No reviews data ");
            return;
        }

        System.out.println("Enter Review ID to update:");
        int reviewID = input.nextInt();

        while (!checkReviewID(reviewID)) {
            System.out.println("Re-Enter Review ID, not available:");
            reviewID = input.nextInt();
        }

        reviews.findFirst();

        while (!reviews.last()) {
            if (reviews.retrieve().getReviewId() == reviewID)
                break;
            reviews.findNext();
        }

        if (reviews.retrieve().getReviewId() == reviewID) {

            Review review = reviews.retrieve();
            reviews.remove();

            System.out.println("1. update rate");
            System.out.println("2. update comment");
            System.out.println("Enter choice:");
            int choice = input.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Enter new rating (5..1): ");
                    int rate = input.nextInt();
                    while (rate < 1 || rate > 5) {
                        System.out.println("Re-Enter rating (5..1):");
                        rate = input.nextInt();
                    }
                    review.setRating(rate);
                    break;

                case 2:
                    System.out.println("Enter comment: ");
                    String comment = input.nextLine();
                    comment = input.nextLine();
                    review.setComment(comment);
                    break;
            }

            reviews.findLast();
            reviews.insert(review);

            System.out.println("Review " + review.getReviewId() + " has been updated");
            System.out.println(review);
        }
    }

    //==============================================================
    public boolean checkReviewID(int rID) {

        if (!reviews.empty()) {
            reviews.findFirst();
            for (int i = 0; i < reviews.size(); i++) {
                if (reviews.retrieve().getReviewId() == rID)
                    return true;
                reviews.findNext();
            }
        }
        return false;
    }
}
