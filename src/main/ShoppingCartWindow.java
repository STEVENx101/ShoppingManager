package main;


import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

// This class represents the shopping cart window for displaying and managing items added to the cart.
public class ShoppingCartWindow extends JFrame {

    private DefaultTableModel cartTableModel;
    private JTable cartTable;
    private Map<Product, Integer> cartItems;
    private JTextArea textArea;
    double categoryDiscount = 0;

    // Constructor
    public ShoppingCartWindow() {
        initializeWindow();
        setupUI();
        cartItems = new HashMap<>();
    }

    // Initialize the shopping cart window
    private void initializeWindow() {
        setTitle("Shopping Cart");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    // Set up the user interface of the shopping cart window
    private void setupUI() {
        JPanel panel = new JPanel(new BorderLayout());

        cartTableModel = new DefaultTableModel(new Object[]{"<html>"+"<b>"+"Product"+"<html>"+"<b>", "<html>"+"<b>"+"Quantity"+"<html>"+"<b>","<html>"+"<b>"+ "Price"+"<html>"+"<b>"}, 0);
        cartTable = new JTable(cartTableModel);


        // Center-align all columns in the JTable
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < cartTableModel.getColumnCount(); i++) {
        cartTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        cartTable.getTableHeader().setReorderingAllowed(false);

        cartTable.setRowHeight(50);
        JScrollPane scrollPane = new JScrollPane(cartTable);

        textArea = new JTextArea(13, 20);
        textArea.setEditable(false);
        

        JScrollPane textScrollPane = new JScrollPane(textArea);

     
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(textScrollPane, BorderLayout.SOUTH);

        getContentPane().add(panel, BorderLayout.CENTER);
    }

    // Add a product to the shopping cart
    public void addToCart(Product product) {
        int quantity = cartItems.getOrDefault(product, 0) + 1;
        cartItems.put(product, quantity);
        updateCartTable();
    }

    // Update the contents of the cart table
    private void updateCartTable() {
        cartTableModel.setRowCount(0);
    
        for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            double price = product.getPrice() * quantity;
    
            String productInfo = "";
    
            if ("Clothing".equals(product.getCategory())) {
                Clothing clothing = (Clothing) product;
                productInfo +=  clothing.getSize() +  clothing.getColor() ;
            } else if ("Electronics".equals(product.getCategory())) {
                Electronics electronics = (Electronics) product;
                productInfo += electronics.getBrand() +  electronics.getWarranty() ;
            } 
    
            cartTableModel.addRow(new Object[]{"<html>" + product.getProductId() + "<br>" + product.getName() + "<br>" + productInfo + "</html>", quantity, price});
        }
    
        updateTextArea();
    }
    

    // Update the summary text area with the current cart information
    private void updateTextArea() {
        double total = calculateTotal();
        double firstdiscount = calculateFirstDiscount();
        double seconddiscount = calculateSecondDiscount();
        double discount = firstdiscount + seconddiscount;
        double finalTotal = total - discount;
        // Set left alignment for the text within the JTextArea
        textArea.setAlignmentX(Component.LEFT_ALIGNMENT);

        textArea.setText("Shopping Cart Summary:\n\n");

        textArea.append("\nTotal: " + total + "\n");
        textArea.append("First Purchase Discount (10%): " + firstdiscount + "\n");
        
        textArea.append("Final Total: " + finalTotal + "\n");
    }

    // Calculate the total price of items in the cart
    private double calculateTotal() {
        double total = 0;

        for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            total += product.getPrice() * quantity;
        }

        return total;
    }

    // Calculate the first purchase discount (10% of the total)
    private double calculateFirstDiscount() {
        double total = calculateTotal();
        double firstPurchaseDiscount = total * 0.1;
        return firstPurchaseDiscount;
    }

    // Calculate the second discount for three items in the same category (20% of the total price of those items)
    private double calculateSecondDiscount() {
        double categoryDiscount = 0;

        for (Product product : cartItems.keySet()) {
            if (cartItems.get(product) == 3) {
                categoryDiscount += product.getPrice() * 0.2;
            }
        }

        return categoryDiscount;
    }

    // Show the shopping cart window
    public void showWindow() {
        setVisible(true);
    }

    // Entry point for testing the shopping cart window
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ShoppingCartWindow shoppingCartWindow = new ShoppingCartWindow();
            shoppingCartWindow.showWindow();
        });
    }
}
