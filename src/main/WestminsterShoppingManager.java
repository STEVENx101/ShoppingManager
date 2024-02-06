package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class WestminsterShoppingManager  {

 
    private static int totalProducts = 0;
    private static int maxProducts = 50;

    private static List<Product> products = new ArrayList<>();
    private static final String FILE_PATH = "C:\\Users\\janud\\OneDrive\\Desktop\\New folder (2)\\ShoppingManager\\program_data.txt";
    public static List<Product> getProducts() {
        return products;
    }

  
    public static void main(String[] args) {
        consoleMenu(args);
    }

    // Method to display the console menu and handle user input
    public static void consoleMenu(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int option;

        // Load products from the file before showing the menu
        loadProductsFromFile();

        do {
            // Display the menu options
            Menu();
            option = scanner.nextInt();

            // Switch statement to handle user input
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
                    loadProductsFromFile();
                    break;
                case 6:
                    launchGUI(); 
                    break;
                case 7:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (option != 7);

        scanner.close(); // Close the scanner to prevent resource leak
    }

    // Method to display the console menu options
    public static void Menu() {
        System.out.println("\n** Westminster Shopping System - Manager Menu **\n");
        System.out.println("1. Add New Product");
        System.out.println("2. Delete Product");
        System.out.println("3. Print Products List");
        System.out.println("4. Save Products to File");
        System.out.println("5. Load Products from File");
        System.out.println("6. Launch GUI");
        System.out.println("7. Exit");
        System.out.print("Enter your option: ");
    }

    // Method to add a new product to the list
    public static void addNewProduct() {
        if (totalProducts == maxProducts) {
            System.out.println("Maximum number of products reached. Cannot add more products.");
            return;
        }
         Scanner scanner = new Scanner(System.in); 
            System.out.print("\nEnter product ID: ");
            String productId = scanner.next().toUpperCase();

            // Check if the product with the given ID already exists
            for (Product product : products) {
                if (product.getProductId().equals(productId)) {
                    System.out.println("Product already exists with this ID: " + productId);
                    return;
                }
            }

            // Get product details from the user
            System.out.print("Enter product name: ");
            String name = scanner.next();
            System.out.print("[1 for Electronics, 2 for Clothing] \nEnter product Category: ");
            int input = scanner.nextInt();

            // Add product based on the selected category
            if (input == 1) {
                addElectronicsProduct(scanner, productId, name);
            } else if (input == 2) {
                addClothingProduct(scanner, productId, name);
            } else {
                System.out.println("Invalid category choice. Please enter 1 for Electronics or 2 for Clothing.");
                return;
            }
        
        totalProducts++;
        sortProductsById(); 
    }

    // Method to add an electronics product to the list
    private static void addElectronicsProduct(Scanner scanner, String productId, String name) {
        System.out.print("Enter product Brand: ");
        String brand = scanner.next();
        System.out.print("Enter product Warranty Period: ");
        String warranty = scanner.next();
        int availableitems = getAvailableitems(scanner);
        double price = getProductPrice(scanner);

        // Creating a new Electronics object and adding it to the list
        Product newProduct = new Electronics(productId, name, price, availableitems, brand, warranty);
        products.add(newProduct);

        System.out.println("New electronics product added.");
    }

    // Method to add a clothing product to the list
    private static void addClothingProduct(Scanner scanner, String productId, String name) {
        System.out.print("Enter product Size: ");
        String size = scanner.next().toUpperCase();
        System.out.print("Enter product color: ");
        String color = scanner.next();
        int availableitems = getAvailableitems(scanner);
        double price = getProductPrice(scanner);

        // Creating a new Clothing object and adding it to the list
        Product newProduct = new Clothing(productId, name, price, availableitems, size, color);
        products.add(newProduct);

        System.out.println("New clothing product added.");
    }

    // Method to get the product price from the user with error handling
    private static double getProductPrice(Scanner scanner) {
        try {
            System.out.print("Enter product price: ");
            return scanner.nextDouble();
        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.nextLine(); 
            return getProductPrice(scanner);
        }
    }

    // Method to get the number of available items from the user with error handling
    private static int getAvailableitems(Scanner scanner) {
        try {
            System.out.print("How many items are available? ");
            return scanner.nextInt();
        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.nextLine(); 
            return getAvailableitems(scanner); // Retry getting the price
        }
    }

    // Method to print the list of products
    public static void printProductsList() {
        System.out.println("\n** Product List **");

        if (products.isEmpty()) {
            System.out.println("No products available.");
        } else {
            for (Product product : products) {

                System.out.println("Product ID: " + product.getProductId());
                System.out.println("Name: " + product.getName());
                System.out.println("Category: " + product.getCategory());
                System.out.println("Price: " + product.getPrice());
                System.out.println("Available Items: " + product.getAvailableItems());

                // Print additional details for Clothing and Electronics products
                if (product instanceof Clothing) {
                    Clothing clothing = (Clothing) product;
                    System.out.println("Size: " + clothing.getSize());
                    System.out.println("Color: " + clothing.getColor());
                } else if (product instanceof Electronics) {
                    Electronics electronics = (Electronics) product;
                    System.out.println("Brand: " + electronics.getBrand());
                    System.out.println("Warranty: " + electronics.getWarranty());
                }

                System.out.println("-----------------------");
            }
        }
    }

    // Method to delete a product from the list
    public static void deleteProduct() {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter product ID: ");
            String productId = scanner.next().toUpperCase();

            for (Product product : products) {
                if (product.getProductId().equals(productId)) {
                    products.remove(product);
                    totalProducts--;
                    System.out.println("Category : " + product.getCategory());
                    System.out.println("Product deleted successfully.");
                    System.out.println("Remaining products: " + totalProducts);
                    return;
                }
            }
            System.out.println("Product not found with ID: " + productId);
        
    }

    // Getter method for the total number of products
    public static int getTotalProducts() {
        return totalProducts;
    }

    // Method to save product data to a file
    public static void saveProductsToFile() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            writer.write("----------Products----------\n");

            // Iterate over the products and write details to the file
            for (Product product : products) {
                writer.write("Product ID: " + product.getProductId() + "\n");
                writer.write("Name: " + product.getName() + "\n");
                writer.write("Category: " + product.getCategory() + "\n");
                writer.write("Price: " + product.getPrice() + "\n");
                writer.write("Available Items: " + product.getAvailableItems() + "\n");  //https://www.w3schools.com/java/java_files_create.asp

                // Write additional details for Clothing and Electronics products
                if (product instanceof Clothing) {
                    Clothing clothing = (Clothing) product;
                    writer.write("Size: " + clothing.getSize() + "\n");
                    writer.write("Color: " + clothing.getColor() + "\n");
                } else if (product instanceof Electronics) {
                    Electronics electronics = (Electronics) product;
                    writer.write("Brand: " + electronics.getBrand() + "\n");
                    writer.write("Warranty: " + electronics.getWarranty() + "\n");
                } 

                writer.write("\n");
            }

            System.out.println("Product data stored successfully.");
        } catch (IOException e) {
            System.out.println("Error: Failed to store product data.");
            e.printStackTrace();
        }
    }

    // Method to load product data from a file
        public static void loadProductsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            boolean inProductsSection = false;
            int loadTotalProducts = 0;

            // Iterate over the lines in the file
            while ((line = reader.readLine()) != null) {
                if (line.trim().equals("----------Products----------")) {
                    inProductsSection = true;
                } else if (inProductsSection && line.startsWith("Product ID: ")) {
                    // Parse product details and create corresponding objects
                    String productId = line.substring("Product ID: ".length()).trim();
                    loadTotalProducts++;
                    String name = reader.readLine().substring("Name: ".length()).trim();
                    String category = reader.readLine().substring("Category: ".length()).trim();
                    double price = Double.parseDouble(reader.readLine().substring("Price: ".length()).trim());
                    int availableItems = Integer.parseInt(reader.readLine().substring("Available Items: ".length()).trim());

                    // Add product based on the category
                    if ("Electronics".equals(category)) {
                        String brand = reader.readLine().substring("Brand: ".length()).trim();
                        String warranty = reader.readLine().substring("Warranty: ".length()).trim();
                        products.add(new Electronics(productId, name, price, availableItems, brand, warranty));
                    } else if ("Clothing".equals(category)) {
                        String size = reader.readLine().substring("Size: ".length()).trim();
                        String color = reader.readLine().substring("Color: ".length()).trim();
                        products.add(new Clothing(productId, name, price, availableItems, size, color));
                    } else {
                        
                    }

                    reader.readLine(); 
                }
            }

            sortProductsById();
            totalProducts = loadTotalProducts;

            System.out.println("Product data loaded successfully.");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error: Failed to load product data from file.");
            e.printStackTrace();
        }
    }

    // Method to sort products by ID
    public static void sortProductsById() {
        // Define a  comparator based on product IDs
        Comparator<Product> productIdComparator = Comparator.comparing(Product::getProductId);

        // Sort the products list using the comparator
        Collections.sort(products, productIdComparator);
    }

    // Method to launch the GUI
    public static void launchGUI() {
        // Create an instance of WestminsterShoppingManager and pass the products list
        MainWindow mainWindow = new MainWindow(getProducts());
        mainWindow.show();
    }
}
