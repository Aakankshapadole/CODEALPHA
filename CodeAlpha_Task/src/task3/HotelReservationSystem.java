package task3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class HotelReservationSystem {
    private static final ArrayList<Room> rooms = new ArrayList<>();
    private static final HashMap<Integer, Reservation> reservations = new HashMap<>();
    private static int reservationIdCounter = 1;

    public static void main(String[] args) {
        initializeRooms();
        Scanner scanner = new Scanner(System.in);
        int choice;

        System.out.println("Welcome to the Hotel Reservation System!");

        do {
            System.out.println("\nMenu:");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Make a Reservation");
            System.out.println("3. View Booking Details");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1 -> displayAvailableRooms();
                case 2 -> makeReservation(scanner);
                case 3 -> viewBookingDetails(scanner);
                case 0 -> System.out.println("Thank you for using the Hotel Reservation System!");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);

        scanner.close();
    }

    // Initialize rooms with different categories
    private static void initializeRooms() {
        rooms.add(new Room(101, "Single", 100.0, true));
        rooms.add(new Room(102, "Single", 100.0, true));
        rooms.add(new Room(201, "Double", 150.0, true));
        rooms.add(new Room(202, "Double", 150.0, true));
        rooms.add(new Room(301, "Suite", 250.0, true));
        rooms.add(new Room(302, "Suite", 250.0, true));
    }

    // Display available rooms
    private static void displayAvailableRooms() {
        System.out.println("\nAvailable Rooms:");
        for (Room room : rooms) {
            if (room.isAvailable()) {
                System.out.printf("Room %d - %s - $%.2f per night%n", room.getRoomNumber(), room.getCategory(), room.getPrice());
            }
        }
    }

    // Make a reservation
    private static void makeReservation(Scanner scanner) {
        System.out.print("Enter room number to book: ");
        int roomNumber = scanner.nextInt();

        Room roomToBook = null;
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber && room.isAvailable()) {
                roomToBook = room;
                break;
            }
        }

        if (roomToBook == null) {
            System.out.println("Invalid room number or room is not available.");
            return;
        }

        System.out.print("Enter number of nights: ");
        int nights = scanner.nextInt();
        if (nights <= 0) {
            System.out.println("Invalid number of nights. Please try again.");
            return;
        }

        double totalCost = nights * roomToBook.getPrice();
        System.out.printf("Total cost for %d nights: $%.2f%n", nights, totalCost);
        System.out.print("Enter payment amount: ");
        double payment = scanner.nextDouble();

        if (payment < totalCost) {
            System.out.println("Insufficient payment. Reservation not completed.");
            return;
        }

        // Process reservation
        roomToBook.setAvailable(false);
        Reservation reservation = new Reservation(reservationIdCounter++, roomNumber, nights, totalCost);
        reservations.put(reservation.getReservationId(), reservation);
        System.out.println("Reservation completed successfully. Your reservation ID is " + reservation.getReservationId());
    }

    // View booking details
    private static void viewBookingDetails(Scanner scanner) {
        System.out.print("Enter reservation ID: ");
        int reservationId = scanner.nextInt();

        Reservation reservation = reservations.get(reservationId);
        if (reservation == null) {
            System.out.println("No reservation found with this ID.");
        } else {
            System.out.println("\nBooking Details:");
            System.out.println("Reservation ID: " + reservation.getReservationId());
            System.out.println("Room Number: " + reservation.getRoomNumber());
            System.out.println("Nights: " + reservation.getNights());
            System.out.printf("Total Cost: $%.2f%n", reservation.getTotalCost());
        }
    }

    // Room class representing a hotel room
    static class Room {
        private final int roomNumber;
        private final String category;
        private final double price;
        private boolean isAvailable;

        public Room(int roomNumber, String category, double price, boolean isAvailable) {
            this.roomNumber = roomNumber;
            this.category = category;
            this.price = price;
            this.isAvailable = isAvailable;
        }

        public int getRoomNumber() {
            return roomNumber;
        }

        public String getCategory() {
            return category;
        }

        public double getPrice() {
            return price;
        }

        public boolean isAvailable() {
            return isAvailable;
        }

        public void setAvailable(boolean available) {
            isAvailable = available;
        }
    }

    // Reservation class representing a reservation
    static class Reservation {
        private final int reservationId;
        private final int roomNumber;
        private final int nights;
        private final double totalCost;

        public Reservation(int reservationId, int roomNumber, int nights, double totalCost) {
            this.reservationId = reservationId;
            this.roomNumber = roomNumber;
            this.nights = nights;
            this.totalCost = totalCost;
        }

        public int getReservationId() {
            return reservationId;
        }

        public int getRoomNumber() {
            return roomNumber;
        }

        public int getNights() {
            return nights;
        }

        public double getTotalCost() {
            return totalCost;
        }
    }
}
