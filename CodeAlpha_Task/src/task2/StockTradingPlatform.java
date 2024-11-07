package task2;

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class StockTradingPlatform {
    // Define stock data and user portfolio
    private static final HashMap<String, Double> stockMarket = new HashMap<>();
    private static final HashMap<String, StockHolding> portfolio = new HashMap<>();
    private static final Random random = new Random();

    public static void main(String[] args) {
        initializeMarketData();
        Scanner scanner = new Scanner(System.in);
        int choice;

        System.out.println("Welcome to the Stock Trading Platform!");

        // Main program loop
        do {
            System.out.println("\nMenu:");
            System.out.println("1. View Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1 -> displayMarketData();
                case 2 -> buyStock(scanner);
                case 3 -> sellStock(scanner);
                case 4 -> displayPortfolio();
                case 0 -> System.out.println("Thank you for using the Stock Trading Platform!");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);

        scanner.close();
    }

    // Initialize stock data with random prices
    private static void initializeMarketData() {
        stockMarket.put("AAPL", generateRandomPrice());
        stockMarket.put("GOOGL", generateRandomPrice());
        stockMarket.put("AMZN", generateRandomPrice());
        stockMarket.put("MSFT", generateRandomPrice());
        stockMarket.put("TSLA", generateRandomPrice());
    }

    // Generate random stock price between 100 and 1500
    private static double generateRandomPrice() {
        return 100 + random.nextDouble() * 1400;
    }

    // Display the current market data
    private static void displayMarketData() {
        System.out.println("\nMarket Data:");
        System.out.println("Symbol\tPrice");
        for (String symbol : stockMarket.keySet()) {
            System.out.printf("%s\t$%.2f%n", symbol, stockMarket.get(symbol));
        }
    }

    // Handle buying stocks
    private static void buyStock(Scanner scanner) {
        System.out.print("Enter stock symbol to buy: ");
        String symbol = scanner.next().toUpperCase();

        if (!stockMarket.containsKey(symbol)) {
            System.out.println("Invalid stock symbol. Please try again.");
            return;
        }

        System.out.print("Enter quantity to buy: ");
        int quantity = scanner.nextInt();
        double price = stockMarket.get(symbol);

        if (quantity <= 0) {
            System.out.println("Invalid quantity. Please try again.");
            return;
        }

        StockHolding holding = portfolio.getOrDefault(symbol, new StockHolding(symbol, 0, 0.0));
        holding.buy(quantity, price);
        portfolio.put(symbol, holding);
        System.out.printf("Bought %d shares of %s at $%.2f per share.%n", quantity, symbol, price);
    }

    // Handle selling stocks
    private static void sellStock(Scanner scanner) {
        System.out.print("Enter stock symbol to sell: ");
        String symbol = scanner.next().toUpperCase();

        if (!portfolio.containsKey(symbol)) {
            System.out.println("You do not own any shares of this stock.");
            return;
        }

        StockHolding holding = portfolio.get(symbol);
        System.out.print("Enter quantity to sell: ");
        int quantity = scanner.nextInt();

        if (quantity <= 0 || quantity > holding.getQuantity()) {
            System.out.println("Invalid quantity. Please try again.");
            return;
        }

        double price = stockMarket.get(symbol);
        holding.sell(quantity, price);

        if (holding.getQuantity() == 0) {
            portfolio.remove(symbol); // Remove stock from portfolio if fully sold
        }

        System.out.printf("Sold %d shares of %s at $%.2f per share.%n", quantity, symbol, price);
    }

    // Display portfolio details
    private static void displayPortfolio() {
        System.out.println("\nPortfolio:");
        if (portfolio.isEmpty()) {
            System.out.println("Your portfolio is empty.");
        } else {
            System.out.println("Symbol\tQuantity\tAvg. Cost\tCurrent Price\tValue\tProfit/Loss");
            for (StockHolding holding : portfolio.values()) {
                String symbol = holding.getSymbol();
                double currentPrice = stockMarket.get(symbol);
                double value = holding.getQuantity() * currentPrice;
                double profitLoss = value - (holding.getAverageCost() * holding.getQuantity());
                System.out.printf("%s\t%d\t\t$%.2f\t\t$%.2f\t\t$%.2f\t$%.2f%n",
                        symbol, holding.getQuantity(), holding.getAverageCost(), currentPrice, value, profitLoss);
            }
        }
    }

    // Nested class to represent a stock holding in the portfolio
    static class StockHolding {
        private final String symbol;
        private int quantity;
        private double averageCost;

        public StockHolding(String symbol, int quantity, double averageCost) {
            this.symbol = symbol;
            this.quantity = quantity;
            this.averageCost = averageCost;
        }

        public String getSymbol() {
            return symbol;
        }

        public int getQuantity() {
            return quantity;
        }

        public double getAverageCost() {
            return averageCost;
        }

        public void buy(int quantity, double price) {
            double totalCost = this.averageCost * this.quantity + price * quantity;
            this.quantity += quantity;
            this.averageCost = totalCost / this.quantity;
        }

        public void sell(int quantity, double price) {
            this.quantity -= quantity;
            if (this.quantity == 0) {
                this.averageCost = 0.0;
            }
        }
    }
}
