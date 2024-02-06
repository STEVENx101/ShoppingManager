package main;

public class Clothing extends Product {
    private String size;
    private String color;

    // Constructor
    public Clothing(String productId, String name, double price, String size, String color) {
        super(productId, name, "Clothing", price);
        this.size = size;
        this.color = color;
    }

    // Getter methods for Clothing-specific attributes
    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }
    
// Setter methods for Clothing-specific attributes

    public void setSize(String size) {
        this.size = size;
    }

    public void setColor(String color) {
        this.color = color;
    }
    


    // Override toString() method for better printing in your application
    @Override
    public String toString() {
        return "Clothing{" +
                "productId='" + getProductId() + '\'' +
                ", name='" + getName() + '\'' +
                ", price=" + getPrice() +
                ", size='" + size + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
