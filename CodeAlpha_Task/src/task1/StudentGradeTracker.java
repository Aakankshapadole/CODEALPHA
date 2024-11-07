package task1;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class StudentGradeTracker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Double> grades = new ArrayList<>();

        System.out.println("Enter student grades (type '-1' to finish):");

        // Input loop with validation
        while (true) {
            System.out.print("Enter grade: ");
            try {
                double grade = scanner.nextDouble();

                if (grade == -1) { // Sentinel value to end input
                    break;
                } else if (grade >= 0 && grade <= 100) { // Valid grade range
                    grades.add(grade);
                } else {
                    System.out.println("Please enter a valid grade between 0 and 100.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numeric grade.");
                scanner.next(); // Clear invalid input
            }
        }

        // Check if there are any grades entered
        if (grades.isEmpty()) {
            System.out.println("No grades were entered.");
        } else {
            // Calculate the average, highest, and lowest grades
            double total = 0;
            double highest = grades.get(0);
            double lowest = grades.get(0);

            for (double grade : grades) {
                total += grade;
                if (grade > highest) highest = grade;
                if (grade < lowest) lowest = grade;
            }

            double average = total / grades.size();

            // Display the results
            System.out.println("\nGrade Summary:");
            System.out.printf("Average Grade: %.2f\n", average);
            System.out.println("Highest Grade: " + highest);
            System.out.println("Lowest Grade: " + lowest);
        }

        scanner.close();
    }
}
