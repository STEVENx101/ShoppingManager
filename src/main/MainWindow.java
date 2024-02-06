package main;



import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

// This class represents the main window of the online shopping system.
public class MainWindow extends JFrame {
    private ShoppingCartWindow shoppingCartWindow;
    private JFrame window;
    private JComboBox<String> productComboBox;
    private List<Product> products;
    private DefaultTableModel tableModel;
    private JTextArea infoTextArea;
    private JTable table;

    // Constructor
    public MainWindow(List<Product> products) {
        this.products = products;
        this.shoppingCartWindow = new ShoppingCartWindow();
        initializeWindow();
        setupUI();
    }

    // Initialize the main window
    public void initializeWindow() {
        window = new JFrame("Online Shopping System");
        window.setSize(1000, 800);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);

        // Create a panel with FlowLayout.CENTER for the "Add to Cart" button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.addActionListener(e -> addToCart());
        // Add the "Add to Cart" button to the panel
        buttonPanel.add(addToCartButton);

        // Add the panel to the SOUTH position of the frame
        window.add(buttonPanel, BorderLayout.SOUTH);
    }

    // Setup the user interface
        private void setupUI() {
        JPanel middlePanel = createMiddlePanel();
        createTable();
        createTextArea();

        // Add the JTextArea and JTable to a single JPanel with a BorderLayout
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(new JScrollPane(table), BorderLayout.CENTER);                     // https://www.youtube.com/watch?v=PLvIyoWPfmM&list=PL3bGLnkkGnuV699lP_f9DvxyK5lMFpq6U&index=9
        centerPanel.add(new JScrollPane(infoTextArea), BorderLayout.SOUTH);

        window.add(middlePanel, BorderLayout.NORTH);
        window.add(centerPanel, BorderLayout.CENTER);
    }

    // Create JTextArea for displaying product information
    private void createTextArea() {
        infoTextArea = new JTextArea(21, 40);
        infoTextArea.setEditable(false);
        infoTextArea.setCursor(Cursor.getDefaultCursor());
        JScrollPane textAreaScrollPane = new JScrollPane(infoTextArea);
        window.add(textAreaScrollPane, BorderLayout.CENTER);
    }

    // Create the middle panel containing product selection components
    private JPanel createMiddlePanel() {
        JPanel middlePanel = new JPanel(new BorderLayout());

        JPanel productsPanel = createProductsPanel();
        middlePanel.add(productsPanel, BorderLayout.NORTH);

        // Create a sub-panel for the "Shopping Cart" button and add it to the center
        JPanel cartButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cartButton = new JButton("Shopping Cart");
        Dimension buttonSize = new Dimension(cartButton.getPreferredSize().width, 40);
        cartButton.setPreferredSize(buttonSize);
        cartButton.addActionListener(e -> openShoppingCart());
        cartButtonPanel.add(cartButton);

        // Create another sub-panel for JComboBox and add it below the "Shopping Cart" button
        JPanel comboBoxPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        comboBoxPanel.add(new JLabel("Select Product Category:"));
        comboBoxPanel.add(productComboBox);
        productComboBox.addActionListener(e -> filterTable());

        // Add the sub-panels to the main middlePanel vertically
        middlePanel.add(cartButtonPanel, BorderLayout.CENTER);
        middlePanel.add(comboBoxPanel, BorderLayout.SOUTH);

        return middlePanel;
    }

    // Add the selected product to the shopping cart
    public void addToCart() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow != -1) {
            Object productId = tableModel.getValueAt(selectedRow, 0);
            Product selectedProduct = findProductById(productId);

            Product availableItems = findProductById(productId);

            if (availableItems.getAvailableItems() > 0) {
                availableItems.setAvailableItems(availableItems.getAvailableItems() - 1);

                if (availableItems.getAvailableItems() == 0) {
                    shoppingCartWindow.addToCart(selectedProduct);
                    JOptionPane.showMessageDialog(window, "No more items available for this product", "Error", JOptionPane.ERROR_MESSAGE);
                    
                    refreshTable();
                }

                shoppingCartWindow.addToCart(selectedProduct);
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(window, "No more items available for this product", "Error", JOptionPane.ERROR_MESSAGE);
                refreshTable();
            }
        }
    }




    private void refreshTable() {

         // Store the selected row index before refresh
        int selectedRow = table.getSelectedRow();

        // Update the table data in the model
        Object[][] data = createTableData();
        tableModel.setDataVector(data, new String[]{"ProductID", "Name", "Category", "Price(€)", "Info"});
    
        // Notify the table that the data has changed
        tableModel.fireTableDataChanged();
    
        // Repaint the table to reflect the changes
        table.repaint();
         // Set the selection to the previously selected row          //https://stackoverflow.com/questions/3179136/jtable-how-to-refresh-table-model-after-insert-delete-or-update-the-data
             if (selectedRow != -1 && selectedRow < table.getRowCount()) {
            table.setRowSelectionInterval(selectedRow, selectedRow);
        }
    }

    // Find a product by its ID
    private Product findProductById(Object productId) {
        for (Product product : products) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    // Create a sub-panel for product selection
    private JPanel createProductsPanel() {
        JPanel productsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        productsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        productComboBox = new JComboBox<>(new String[]{"All", "Electronics", "Clothing"});

        productsPanel.add(productComboBox);
        productComboBox.addActionListener(e -> filterTable());
        return productsPanel;
    }

    // Create the table for displaying products
    private void createTable() {
        String[] columnNames = {"<html>"+"<b>"+"ProductID"+"<html>"+"<b>", "<html>"+"<b>"+"Name"+"<html>"+"<b>", "<html>"+"<b>"+"Category"+"<html>"+"<b>","<html>"+"<b>"+ "Price(€)"+"<html>"+"<b>", "<html>"+"<b>"+"Info"+"<html>"+"<b>"};
        Object[][] data = createTableData();
        

        tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        // Center-align the data in the cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();      // https://stackoverflow.com/questions/7433602/how-to-center-in-jtable-cell-a-value
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        table.getTableHeader().setReorderingAllowed(false);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {

           
            @Override 
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Check if the available items column (index 4) is less than 3
                Object productId = tableModel.getValueAt(row, 0);
                Product product = findProductById(productId);

                if (product != null) {
                    int availableItems = product.getAvailableItems();

                    if (availableItems < 3) {
                        // Set the background color to red for rows with less than 3 available items
                        cellComponent.setBackground(Color.RED);
                    } else {
                        // Reset the background color for other rows
                        cellComponent.setBackground(table.getBackground());
                    }
                }

                return cellComponent;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);

        table.getSelectionModel().addListSelectionListener(createTableSelectionListener());

        // Add the table to the center of the BorderLayout
        window.add(scrollPane, BorderLayout.CENTER);
    }

    // Create data for the table
    private Object[][] createTableData() {
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
                data[i][4] = "N/A";
            }
        }
        return data;
        
    }

    // Create a listener for table selection changes
    private ListSelectionListener createTableSelectionListener() {
        return e -> {
            if (!e.getValueIsAdjusting()) {
                ListSelectionModel selectionModel = (ListSelectionModel) e.getSource();
                int selectedRow = selectionModel.getMinSelectionIndex();

                if (selectedRow != -1) {
                    Object productId = tableModel.getValueAt(selectedRow, 0);
                    Object name = tableModel.getValueAt(selectedRow, 1);
                    Object category = tableModel.getValueAt(selectedRow, 2);
                    Object price = tableModel.getValueAt(selectedRow, 3);
                    Object info = tableModel.getValueAt(selectedRow, 4);

                    showSelectedRowInfo(productId, name, category, price, info);
                }
            }
        };
    }

    // Show information about the selected product
    private void showSelectedRowInfo(Object productId, Object name, Object category, Object price, Object info) {
        String productInfo = "    Selected Product - Details\n"
                +"\n"+ "    ProductID: " + productId + "\n"
                + "    Name: " + name + "\n"
                + "    Category: " + category + "\n"
                + "    Price: " + price + "\n";

        if ("Clothing".equals(category)) {
            Clothing clothing = (Clothing) findProductById(productId);
            productInfo += "    Size: " + clothing.getSize() + "\n" + "    Color: " + clothing.getColor()+ "\n";
        } else if ("Electronics".equals(category)) {
            Electronics electronics = (Electronics) findProductById(productId);
            productInfo += "    Brand: " + electronics.getBrand() + "\n" + "    Warranty: " + electronics.getWarranty() + "\n";
        } else {
            productInfo += "Info: N/A" + "\n";
        }

        Product availableItems = (Product) findProductById(productId);
        productInfo += "    Available Items: " + availableItems.getAvailableItems();

        infoTextArea.setText(productInfo);
    }

    // Open the shopping cart window
    private void openShoppingCart() {
        shoppingCartWindow.showWindow();
    }

    // Filter the table based on the selected product category
    private void filterTable() {
        tableModel.setRowCount(0);
        String selectedCategory = (String) productComboBox.getSelectedItem();
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            String category = product.getCategory();
            if ("All".equals(selectedCategory) || selectedCategory.equals(category)) {
                Object[] rowData = new Object[5];
                rowData[0] = product.getProductId();
                rowData[1] = product.getName();
                rowData[2] = category;
                rowData[3] = product.getPrice();

                if (product instanceof Clothing) {
                    Clothing clothing = (Clothing) product;
                    rowData[4] = clothing.getSize() + "," + clothing.getColor();
                } else if (product instanceof Electronics) {
                    Electronics electronics = (Electronics) product;
                    rowData[4] = electronics.getBrand() + "," + electronics.getWarranty();
                } else {
                    rowData[4] = "N/A";
                }

                tableModel.addRow(rowData);
                
            }
        }
    }

    // Show the main window
    public void show() {
        window.setVisible(true);
        refreshTable();
    }

   
    public static void main(String[] args) {
        WestminsterShoppingManager console = new WestminsterShoppingManager();
        MainWindow mainWindow = new MainWindow(console.getProducts());
        mainWindow.show();
        
    }
}
