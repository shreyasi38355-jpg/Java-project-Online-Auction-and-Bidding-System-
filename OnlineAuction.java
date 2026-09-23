import java.util.Scanner;
public class OnlineAuction{
    static Scanner sc = new Scanner(System.in);
    static int[] productId = new int[10];
    static String[] productName = new String[10];
    static double[] startingPrice = new double[10];
    static double[] highestBid = new double[10];
    static String[] highestBidder = new String[10];
    static int productCount = 0;
    static void addProduct() {
        if (productCount >= 10) {
            System.out.println("Product limit reached!");
            return;
        }
        System.out.print("Enter Product ID: ");
        productId[productCount] = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Product Name: ");
        productName[productCount] = sc.nextLine();
        System.out.print("Enter Starting Price: ");
        startingPrice[productCount] = sc.nextDouble();
        highestBid[productCount] = startingPrice[productCount];
        highestBidder[productCount] = "No bidder";
        productCount++;
        System.out.println("Product added successfully!");
    }
    static void displayProducts() {
        if (productCount == 0) {
            System.out.println("No products available.");
            return;
        }
        System.out.println("\n----- Available Products -----");
        for (int i = 0; i < productCount; i++) {
            System.out.println("Product ID: " + productId[i]);
            System.out.println("Product Name: " + productName[i]);
            System.out.println("Starting Price: ₹" + startingPrice[i]);
            System.out.println("Highest Bid: ₹" + highestBid[i]);
            System.out.println("Highest Bidder: " + highestBidder[i]);
        }
    }
    static void placeBid() {
        if (productCount == 0) {
            System.out.println("No products available for bidding.");
            return;
        }
        System.out.print("Enter Product ID: ");
        int id = sc.nextInt();
        int index = -1;
        for (int i = 0; i < productCount; i++) {
            if (productId[i] == id) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            System.out.println("Product not found!");
            return;
        }
        sc.nextLine();
        System.out.print("Enter your name: ");
        String bidder = sc.nextLine();
        System.out.print("Enter your bid amount: ");
        double bid = sc.nextDouble();
        if (bid > highestBid[index]) {
            highestBid[index] = bid;
            highestBidder[index] = bidder;
            System.out.println("Bid placed!");
        } else {
            System.out.println("Your bid must be higher than the current highest bid.");
        }
    }
    static void declareWinner() {
        if (productCount == 0) {
            System.out.println("No products available.");
            return;
        }
        System.out.print("Enter Product ID: ");
        int id = sc.nextInt();
        int index = -1;
        for (int i = 0; i < productCount; i++) {
            if (productId[i] == id) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            System.out.println("Product not found!");
            return;
        }
        System.out.println("\n----- Auction Result -----");
        System.out.println("Product: " + productName[index]);
        System.out.println("Highest Bid: ₹" + highestBid[index]);
        System.out.println("Winner: " + highestBidder[index]);
    }
    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n===== ONLINE AUCTION SYSTEM =====");
            System.out.println("1. Seller - Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Buyer - Place Bid");
            System.out.println("4. Declare Winner");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    displayProducts();
                    break;
                case 3:
                    placeBid();
                    break;
                case 4:
                    declareWinner();
                    break;
                case 5:
                    System.out.println("Thank you for using Online Auction System!");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 5);
        sc.close();
    }
}