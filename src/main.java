import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Stock Management System");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        StockManage stockSystem = new StockManage();

        JTextField productField = new JTextField(10);
        JTextField quantityField = new JTextField(5);
        JButton addButton = new JButton("Add Product");
        JButton removeButton = new JButton("Remove Product");
        JTextArea logArea = new JTextArea(10, 30);
        logArea.setEditable(false);

        addButton.addActionListener(e -> {
            String product = productField.getText();
            int quantity;
            try {
                quantity = Integer.parseInt(quantityField.getText());
                stockSystem.addProduct(product, quantity);
                logArea.setText(String.join("\n", stockSystem.getStockAction().showLog()));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid number");
            }
        });

        removeButton.addActionListener(e -> {
            String product = productField.getText();
            stockSystem.removeProduct(product);
            logArea.setText(String.join("\n", stockSystem.getStockAction().showLog()));
        });

        frame.add(new JLabel("Product: "));
        frame.add(productField);
        frame.add(new JLabel("Quantity: "));
        frame.add(quantityField);
        frame.add(addButton);
        frame.add(removeButton);
        frame.add(new JScrollPane(logArea));

        frame.setVisible(true);
    }
}