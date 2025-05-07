package myQAGRTP;

import java.util.Scanner;

public class ProductPriceExtractor {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the product name: ");
        String productName = scanner.nextLine();

        String inputString = "CoffeeMaker:1.37; 2.73;Laptop:499.99; 599.99;Phone:99.99; 129.99";

        String[] products = inputString.split(";");

        for (String product : products) {
            String[] parts = product.split(":");
            if (parts[0].trim().equalsIgnoreCase(productName)) {
                System.out.println("Prices for " + productName + ":");
                for (int i = 1; i < parts.length; i++) {
                    System.out.println("Price " + i + ": " + parts[i].trim());
                }
                break;
            }
        }
    }
}