package main;


// This class represents a clothing product, which is a specific type of product.
public class Clothing extends Product {
    private String size;        
    private String color;       

    

    // constructor to initialize a clothing product with specified attributes.
    public Clothing(String productId, String name, double price, int availableItems, String size, String color) {
        // Call the constructor of the superclass (Product) to set common attributes
        super(productId, name, "Clothing", price, availableItems);
        this.size = size;
        this.color = color;
    }

    // Getter methods

    // Get the size of the clothing product
    public String getSize() {
        return size;
    }

    // Get the color of the clothing product
    public String getColor() {
        return color;
    }

    // Setter methods

    // Set the size of the clothing product
    public void setSize(String size) {
        this.size = size;
    }

    // Set the color of the clothing product
    public void setColor(String color) {
        this.color = color;
    }
}
