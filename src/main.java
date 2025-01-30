import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Stock Management System");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(240, 248, 255));

        StockManage stockSystem = new StockManage();

        DefaultListModel<String> stockListModel = new DefaultListModel<>();
        JList<String> stockList = new JList<>(stockListModel);
        stockList.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane stockScrollPane = new JScrollPane(stockList);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(4, 2, 10, 10));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        controlPanel.setBackground(new Color(224, 255, 255));

        JTextField productField = new JTextField();
        JTextField quantityField = new JTextField();
        JButton addButton = new JButton("Add Product");
        JButton updateButton = new JButton("Update Product");
        JButton removeButton = new JButton("Remove Product");

        addButton.setBackground(new Color(50, 205, 50));
        addButton.setForeground(Color.WHITE);
        updateButton.setBackground(new Color(30, 144, 255));
        updateButton.setForeground(Color.WHITE);
        removeButton.setBackground(new Color(220, 20, 60));
        removeButton.setForeground(Color.WHITE);

        JTextArea logArea = new JTextArea(10, 30);
        logArea.setEditable(false);
        logArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane logScrollPane = new JScrollPane(logArea);

        addButton.addActionListener(e -> {
            String product = productField.getText();
            try {
                int quantity = Integer.parseInt(quantityField.getText());
                stockSystem.addProduct(product, quantity);
                updateStockList(stockListModel, stockSystem);
                logArea.setText(String.join("\n", stockSystem.getStockAction().showLog()));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid number");
            }
        });

        updateButton.addActionListener(e -> {
            String product = stockList.getSelectedValue();
            if (product != null) {
                try {
                    int quantity = Integer.parseInt(JOptionPane.showInputDialog("Enter new quantity:"));
                    stockSystem.updateProduct(product.split(" - ")[0], quantity);
                    updateStockList(stockListModel, stockSystem);
                    logArea.setText(String.join("\n", stockSystem.getStockAction().showLog()));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid number");
                }
            }
        });

        removeButton.addActionListener(e -> {
            String product = stockList.getSelectedValue();
            if (product != null) {
                stockSystem.removeProduct(product.split(" - ")[0]);
                updateStockList(stockListModel, stockSystem);
                logArea.setText(String.join("\n", stockSystem.getStockAction().showLog()));
            }
        });

        controlPanel.add(new JLabel("Product:"));
        controlPanel.add(productField);
        controlPanel.add(new JLabel("Quantity:"));
        controlPanel.add(quantityField);
        controlPanel.add(addButton);
        controlPanel.add(updateButton);
        controlPanel.add(removeButton);

        frame.add(stockScrollPane, BorderLayout.WEST);
        frame.add(controlPanel, BorderLayout.CENTER);
        frame.add(logScrollPane, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private static void updateStockList(DefaultListModel<String> model, StockManage stockSystem) {
        model.clear();
        for (Map.Entry<String, Integer> entry : stockSystem.viewStock().entrySet()) {
            model.addElement(entry.getKey() + " - " + entry.getValue());
        }
    }
}