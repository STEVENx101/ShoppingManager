package main;

// This class represents an electronics product, which is a specific type of product.
public class Electronics extends Product {
    private String brand;        
    private String warranty;     

   

    //  constructor to initialize an electronics product with specified attributes.
    
    public Electronics(String productId, String name, double price, int availableItems, String brand, String warranty) {
        // Call the constructor of the superclass (Product) to set common attributes
        super(productId, name, "Electronics", price, availableItems);
        this.brand = brand;
        this.warranty = warranty;
    }

    // Getter methods

    // Get the brand of the electronics product
    public String getBrand() {
        return brand;
    }

    // Get the warranty period of the electronics product
    public String getWarranty() {
        return warranty;
    }

    // Setter methods

    // Set the brand of the electronics product
    public void setBrand(String brand) {
        this.brand = brand;
    }

    // Set the warranty period of the electronics product
    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }
}
