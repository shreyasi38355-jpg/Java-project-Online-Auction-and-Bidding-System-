import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<Auction> auctions = new ArrayList<>();
    private static String[] categories = {"Electronics", "Books", "Art", "Sports"};
    private static final Path FILE_PATH = Paths.get(System.getProperty("user.dir"), "auction.txt").toAbsolutePath().normalize();
    public static void main(String[] args) {
        auctions.add(new Auction("Laptop", 30000.0));
        auctions.add(new Auction("Vintage Watch", 5000.0));
        boolean running = true;
        while (running) {
            System.out.println("\n=================================");
            System.out.println("     ONLINE AUCTION SYSTEM       ");
            System.out.println("=================================");
            System.out.println("1. Add Product");
            System.out.println("2. View All Auctions");
            System.out.println("3. Place a Bid");
            System.out.println("4. Close an Auction");
            System.out.println("5. View Categories (Array Sort)");
            System.out.println("6. Save to File (File I/O)");
            System.out.println("7. Load from File (File I/O)");
            System.out.println("8. Generate Report (Streams & StringBuilder)");
            System.out.println("9. String .equals() vs == Demo");
            System.out.println("10. Exit");
            System.out.print("Enter your choice (1-10): ");
            int choice = 0;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                continue;
            }
            switch (choice) {
                case 1:
                    addAuction();
                    break;
                case 2:
                    viewAuctions();
                    break;
                case 3:
                    placeBid();
                    break;
                case 4:
                    closeAuction();
                    break;
                case 5:
                    showCategories();
                    break;
                case 6:
                    saveToFile();
                    break;
                case 7:
                    loadFromFile();
                    break;
                case 8:
                    generateReport();
                    break;
                case 9:
                    demonstrateStringEquals();
                    break;
                case 10:
                    System.out.println("Exiting system. Thank you!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice! Choose between 1 and 10.");
                    break;
            }
        }
    }
    private static void addAuction() {
        System.out.print("\nEnter item name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Item name cannot be empty!");
            return;
        }
        System.out.print("Enter starting price: ");
        double price;
        try {
            price = Double.parseDouble(scanner.nextLine().trim());
            if (price <= 0) {
                System.out.println("Starting price must be greater than zero!");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a valid numerical price.");
            return;
        }
        Auction newAuction = new Auction(name, price);
        auctions.add(newAuction);
        System.out.println("Auction added successfully for: " + name);
    }
    private static void viewAuctions() {
        System.out.println("\n--- Current Auctions ---");
        if (auctions.isEmpty()) {
            System.out.println("No auctions available.");
            return;
        }
        for (int i = 0; i < auctions.size(); i++) {
            System.out.println((i + 1) + ". " + auctions.get(i));
        }
        System.out.println("\n(Demonstrating Lambda Expression: auctions.forEach):");
        auctions.forEach(a -> System.out.println(" -> " + a.getItemName()));
    }
    private static void placeBid() {
        viewAuctions();
        if (auctions.isEmpty()) return;
        System.out.print("\nEnter auction number to bid on: ");
        int index;
        try {
            index = Integer.parseInt(scanner.nextLine().trim()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a valid auction number.");
            return;
        }
        if (index < 0 || index >= auctions.size()) {
            System.out.println("Invalid auction number!");
            return;
        }
        System.out.print("Enter bidder name: ");
        String bidder = scanner.nextLine().trim();
        if (bidder.isEmpty()) {
            System.out.println("Bidder name cannot be empty!");
            return;
        }
        System.out.print("Enter bid amount: ");
        double amount;
        try {
            amount = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a valid bid amount.");
            return;
        }
        try {
            Auction target = auctions.get(index);
            target.placeBid(bidder, amount);
            System.out.println("Bid accepted! " + bidder + " is now highest bidder with $" + amount);
        } catch (InvalidBidException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    private static void closeAuction() {
        viewAuctions();
        if (auctions.isEmpty()) return;
        System.out.print("\nEnter auction number to close: ");
        int index;
        try {
            index = Integer.parseInt(scanner.nextLine().trim()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a valid auction number.");
            return;
        }
        if (index >= 0 && index < auctions.size()) {
            Auction a = auctions.get(index);
            if (!a.isOpen()) {
                System.out.println("Auction is already closed!");
                return;
            }
            a.closeAuction();
            System.out.println("\nAuction closed for: " + a.getItemName());
            if ("None".equals(a.getHighestBidder())) {
                System.out.println("No bids were placed on this item. Closed without a winner.");
            } else {
                System.out.println("Winner: " + a.getHighestBidder() + " with final bid: $" + a.getHighestBid());
            }
        } else {
            System.out.println("Invalid auction number!");
        }
    }
    private static void showCategories() {
        System.out.println("\n--- Available Item Categories (Array) ---");
        Arrays.sort(categories);
        for (int i = 0; i < categories.length; i++) {
            System.out.println((i + 1) + ". " + categories[i]);
        }
    }
    private static void saveToFile() {
        try {
            Path parent = FILE_PATH.getParent();
            if (parent != null && !Files.exists(parent)) {
                Files.createDirectories(parent);
            }
            List<String> lines = new ArrayList<>();
            for (Auction a : auctions) {
                String line = a.getItemName() + "," + a.getStartingPrice() + "," +
                              a.getHighestBid() + "," + a.getHighestBidder() + "," + a.isOpen();
                lines.add(line);
            }
            Files.write(FILE_PATH, lines);
            System.out.println("Auctions successfully saved to " + FILE_PATH);
        } catch (IOException e) {
            System.out.println("File Error: " + e.getMessage());
        }
    }
    private static void loadFromFile() {
        try {
            if (!Files.exists(FILE_PATH)) {
                System.out.println("File " + FILE_PATH + " does not exist yet. Save first!");
                return;
            }
            List<String> lines = Files.readAllLines(FILE_PATH);
            auctions.clear();
            for (String line : lines) {
                if (line == null || line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split(",", 5);
                if (parts.length >= 5) {
                    try {
                        String name = parts[0].trim();
                        double start = Double.parseDouble(parts[1].trim());
                        double high = Double.parseDouble(parts[2].trim());
                        String bidder = parts[3].trim();
                        boolean open = Boolean.parseBoolean(parts[4].trim());

                        Auction a = new Auction(name, start, high, bidder, open);
                        auctions.add(a);
                    } catch (NumberFormatException e) {
                        System.out.println("Skipping malformed row: " + line);
                    }
                }
            }
            System.out.println("Loaded " + auctions.size() + " auction(s) from " + FILE_PATH);
        } catch (IOException e) {
            System.out.println("File Error: " + e.getMessage());
        }
    }
    private static void generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("\n=================================\n");
        report.append("         AUCTION REPORT          \n");
        report.append("=================================\n");
        long totalCount = auctions.size();
        long openCount = auctions.stream().filter(a -> a.isOpen()).count();
        long closedCount = totalCount - openCount;
        report.append("Total Items Listed : ").append(totalCount).append("\n");
        report.append("Active/Open Items  : ").append(openCount).append("\n");
        report.append("Closed Items       : ").append(closedCount).append("\n");
        report.append("---------------------------------\n");
        System.out.print(report.toString());
        System.out.println("High Value Auctions (Bid > $10,000 using Stream filter):");
        auctions.stream()
                .filter(a -> a.getHighestBid() > 10000.0)
                .forEach(System.out::println);
    }
    private static void demonstrateStringEquals() {
        System.out.println("\n--- CO5 DEMO: .equals() vs == ---");
        String s1 = "Auction";
        String s2 = new String("Auction");
        System.out.println("String s1 = \"Auction\";");
        System.out.println("String s2 = new String(\"Auction\");");
        System.out.println("s1 == s2      : " + (s1 == s2) + " (Compares memory addresses -> False)");
        System.out.println("s1.equals(s2) : " + s1.equals(s2) + " (Compares character contents -> True)");
    }
}
class InvalidBidException extends Exception {
    public InvalidBidException(String message) {
        super(message);
    }
}
class Auction {
    private String itemName;
    private double startingPrice;
    private double highestBid;
    private String highestBidder;
    private boolean isOpen;
    public Auction() {
        this("Sample Item", 100.0);
    }
    public Auction(String itemName, double startingPrice) {
        this(itemName, startingPrice, startingPrice, "None", true);
    }
    public Auction(String itemName, double startingPrice, double highestBid, String highestBidder, boolean isOpen) {
        this.itemName = itemName;
        this.startingPrice = startingPrice;
        this.highestBid = highestBid;
        this.highestBidder = highestBidder;
        this.isOpen = isOpen;
    }
    public void placeBid(String bidder, double bidAmount) throws InvalidBidException {
        if (!isOpen) {
            throw new InvalidBidException("Auction is closed! Cannot place bids.");
        }
        if (bidAmount <= highestBid) {
            throw new InvalidBidException("Bid must be strictly higher than current highest bid of $" + highestBid);
        }
        this.highestBid = bidAmount;
        this.highestBidder = bidder;
    }
    public void closeAuction() {
        this.isOpen = false;
    }
    public String getItemName() {
        return itemName;
    }
    public double getStartingPrice() {
        return startingPrice;
    }
    public double getHighestBid() {
        return highestBid;
    }
    public String getHighestBidder() {
        return highestBidder;
    }
    public boolean isOpen() {
        return isOpen;
    }
    @Override
    public String toString() {
        String status = isOpen ? "OPEN" : "CLOSED";
        return itemName + " | Base: $" + startingPrice + " | Top Bid: $" + highestBid + " by " + highestBidder + " | [" + status + "]";
    }
}