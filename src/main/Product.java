package main;

// This class represents a generic product with common attributes.
public abstract class  Product {
    private String productId;       
    private String name;            
    private String category;        
    private double price;           
    private int availableItems;     

    

    //  constructor to initialize a product with specified attributes.
    public Product(String productId, String name, String category, double price, int availableItems) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.availableItems = availableItems;
    }

    // Default constructor
    public Product() {
        
    }

    // Getter methods

    // Get the product ID
    public String getProductId() {
        return productId;
    }

    // Get the product name
    public String getName() {
        return name;
    }

    // Get the product category
    public String getCategory() {
        return category;
    }

    // Get the product price
    public double getPrice() {
        return price;
    }

    // Get the number of available items
    public int getAvailableItems() {
        return availableItems;
    }

    // Setter methods

    // Set the product ID
    public void setProductId(String productId) {
        this.productId = productId;
    }

    // Set the product name
    public void setName(String name) {
        this.name = name;
    }

    // Set the product category
    public void setCategory(String category) {
        this.category = category;
    }

    // Set the product price
    public void setPrice(double price) {
        this.price = price;
    }

    // Set the number of available items
    public void setAvailableItems(int availableItems) {
        this.availableItems = availableItems;
    }

    // Method to retrieve details of the product (to be overridden by subclasses)
    public String getDetails() {
        return null;
    }
}
