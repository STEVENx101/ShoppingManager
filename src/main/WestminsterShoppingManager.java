package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WestminsterShoppingManager {

    private static List<Product> products = new ArrayList<>();

    public static List<Product> getProducts() {
        return products;
    }

    public static void main(String[] args) {
        consoleMenu(args);
    }

    public static void consoleMenu(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            Menu();
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    addNewProduct();
                    break;
                case 2:
                    deleteProduct();
                    break;
                case 3:
                    printProductsList();
                    break;
                case 4:
                    saveProductsToFile();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                case 6:
                    launchGUI(); // Option to launch GUI
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (option != 5);

        scanner.close(); // close the scanner to prevent resource leak
    }

    public static void Menu() {
        System.out.println("\n** Westminster Shopping System - Manager Menu **");
        System.out.println("1. Add New Product");
        System.out.println("2. Delete Product");
        System.out.println("3. Print Products List");
        System.out.println("4. Save Products to File");
        System.out.println("5. Exit");
        System.out.print("Enter your option: ");
    }

    public static void addNewProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter product ID: ");
        String productId = scanner.next().toUpperCase();

        System.out.print("Enter product name: ");
        String name = scanner.next();

        System.out.print("Enter product Category (1 for Electronics, 2 for Clothing): ");
        int input = scanner.nextInt();
        

        if (input == 1) {
            addElectronicsProduct(scanner, productId, name);
        } else if (input == 2) {
            addClothingProduct(scanner, productId, name);
        } else {
            System.out.println("Invalid category choice. Please enter 1 for Electronics or 2 for Clothing.");
            return;
            
        }
       
    }

    private static void addElectronicsProduct(Scanner scanner, String productId, String name) {
        System.out.print("Enter product Brand: ");
        String brand = scanner.next();
        System.out.print("Enter product Warranty Period: ");
        String warranty = scanner.next();

        double price = getProductPrice(scanner);

        // Creating a new Electronics object and adding it to the list
        Product newProduct = new Electronics(productId, name, price, brand, warranty);
        products.add(newProduct);

        System.out.println("New electronics product added.");
    }

    private static void addClothingProduct(Scanner scanner, String productId, String name) {
        System.out.print("Enter product Size: ");
        String size = scanner.next().toUpperCase();
        System.out.print("Enter product color: ");
        String color = scanner.next();

        double price = getProductPrice(scanner);

        // Creating a new Clothing object and adding it to the list
        Product newProduct = new Clothing(productId, name, price, size, color);
        products.add(newProduct);

        System.out.println("New clothing product added.");
    }

    private static double getProductPrice(Scanner scanner) {
        try {
            System.out.print("Enter product price: ");
            return scanner.nextDouble();
        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.nextLine(); // Consume the invalid input to prevent an infinite loop
            return getProductPrice(scanner); // Retry getting the price
        }
    }

    public static void printProductsList() {
        System.out.println("\n** Product List **");

        if (products.isEmpty()) {
            System.out.println("No products available.");
        } else {
            for (Product product : products) {
                System.out.println(product); // Utilize the overridden toString() method in Product and its subclasses
                System.out.println("-----------------------");
            }
        }
    }

    public static void deleteProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter product ID: ");
        String productId = scanner.next().toUpperCase();
        

        for (Product product : products) {
            if (product.getProductId().equals(productId)) {
                products.remove(product);
                System.out.println("Product deleted successfully.");
                return;
            }
        }

        System.out.println("Product not found with ID: " + productId);
    }

    public static void saveProductsToFile() {
        // Implementation for saving products to a file
        System.out.println("Saving products to a file...");
    }

    public static void launchGUI() {
        // Create an instance of WestminsterShoppingManager and pass the products list
        MainWindow mainWindow = new MainWindow(getProducts());
        mainWindow.show();
    }
}
