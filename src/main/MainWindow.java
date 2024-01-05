package main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MainWindow {

    private JFrame window;
    private JComboBox<String> productComboBox;
    private List<Product> products;

    public MainWindow(List<Product> products) {
        this.products = products;

        window = new JFrame("Online Shopping System");
        window.setSize(1000, 800);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));

        JButton cartButton = new JButton("Shopping Cart");
        cartButton.addActionListener(e -> openShoppingCart());
        panel.add(cartButton);
        window.add(panel, BorderLayout.NORTH);

        JPanel productsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        productComboBox = new JComboBox<>(new String[]{"All", "Electronics", "Clothing"});
        productsPanel.add(new JLabel("Select Product Category:"));
        productsPanel.add(productComboBox);
        window.add(productsPanel, BorderLayout.CENTER);

        String[] columnNames = {"ProductID", "Name", "Category", "Price(€)", "Info"};
        Object[][] data = new Object[products.size()][5];

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            data[i][0] = product.getProductId();
            data[i][1] = product.getName();
            data[i][2] = product.getCategory();
            data[i][3] = product.getPrice();
            
            if (product instanceof Clothing) {
                Clothing clothing = (Clothing) product;
                data[i][4] = clothing.getSize() + "," + clothing.getColor();
            } else if (product instanceof Electronics) {
                Electronics electronics = (Electronics) product;
                data[i][4] = electronics.getBrand() + "," + electronics.getWarranty();
            } else {
                // Handle other product types if needed
                data[i][4] = "N/A";

        }


        

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        window.add(scrollPane, BorderLayout.SOUTH);}
    }

    public void show() {
        window.setVisible(true);
    }

    private void openShoppingCart() {
        JOptionPane.showMessageDialog(window, "Shopping Cart Window will be displayed here.");
    }

    public static void main(String[] args) {
        WestminsterShoppingManager console = new WestminsterShoppingManager();
        MainWindow mainWindow = new MainWindow(console.getProducts());
        mainWindow.show();
    }
}
