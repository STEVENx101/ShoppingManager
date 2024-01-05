package main;

public class Electronics extends Product {
    private String brand;
    private String warranty;

    // Constructor
    public Electronics(String productId, String name, double price, String brand, String warranty ) {
        super(productId, name, "Electronics", price);
        this.brand = brand;
        this.warranty = warranty;
    }

    // Getter methods for Electronics-specific attributes
    public String getBrand() {
        return brand;
    }

    public String getWarranty() {
        return warranty;
    }


    

    // Override toString() method for better printing in your application
    @Override
    public String toString() {
        return "Electronics{" +
                "productId='" + getProductId() + '\'' +
                ", name='" + getName() + '\'' +
                ", price=" + getPrice() +
                ", brand='" + brand + '\'' +
                ", warranty='" + warranty + '\'' +
                '}';
    }
}
