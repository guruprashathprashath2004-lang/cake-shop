package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.table.DefaultTableModel;
import view.Menu;

public class Transection {

    private Menu menu;

    public Transection(Menu menu) {
        this.menu = menu;
    }

    // OVERLOADED addToCart - Version 1     
    public void addToCart() {
        String CustomerName = menu.getCustomerNameText().trim();
        String FlavororCategory = menu.getFlavorOrCategoryText().trim();
        String Qty = menu.getCustomerQtyText().trim();
        String Price = menu.getCustomerPriceText().trim();

        addToCart(CustomerName, FlavororCategory, Qty, Price); // call version 2
    }

    // addToCart - Version 2
    public void addToCart(String CustomerName, String FlavororCategory, String Qty, String Price) {
        try {
            if (CustomerName.isEmpty() || FlavororCategory.isEmpty() || Qty.isEmpty() || Price.isEmpty()) {
                menu.showMessage("All fields (Customer, Flavor, Quantity, Price) must be filled!");
                return;
            }

            int qty = Integer.parseInt(Qty);
            double price = Double.parseDouble(Price);

            if (qty <= 0 || price <= 0) {
                menu.showMessage("Quantity and Price must be greater than 0!");
                return;
            }

            double total = qty * price;
            menu.setTotalAmountText(String.valueOf(total));

            // Append order details to temporary cart file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("Cart.txt", true))) {
                writer.write(CustomerName + "|" + FlavororCategory + "|" + Qty + "|" + Price + "|" + total);
                writer.newLine();
            }

            menu.showMessage("Item added to cart successfully!");

        } catch (NumberFormatException e) {
            menu.showMessage("Please enter valid numbers for Quantity and Price!");
        } catch (IOException e) {
            menu.showMessage("Error writing to cart file: " + e.getMessage());
        }
    }

    // Completes the sale and transfers cart items to Transactions.txt    
    public void completeSale() {
        File cart = new File("Cart.txt");
        File transaction = new File("Transactions.txt");

        if (!cart.exists()) {
            menu.showMessage("Cart is empty! Add items before completing sale.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(cart));
                BufferedWriter writer = new BufferedWriter(new FileWriter(transaction, true))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue; // skip empty lines
                }
                writer.write(line);
                writer.newLine();
            }

        } catch (IOException ex) {
            menu.showMessage("Error saving transactions: " + ex.getMessage());
            return;
        }

        // Clear the cart file
        cart.delete();

        menu.showMessage("Sale completed successfully! Transaction saved.");
        menu.clearCartField();
    }

    // Views all transactions in JTable    
    public void viewAllTransactions() {
        DefaultTableModel model = (DefaultTableModel) menu.getTransactionTable().getModel();
        model.setRowCount(0); // clear existing rows

        File transactionFile = new File("Transactions.txt");
        if (!transactionFile.exists()) {
            menu.showMessage("No transactions found yet!");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(transactionFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue; // skip empty lines
                }
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    String customer = parts[0];
                    String flavor = parts[1];
                    int qty = Integer.parseInt(parts[2]);
                    double price = Double.parseDouble(parts[3]);
                    double total = Double.parseDouble(parts[4]);

                    model.addRow(new Object[]{customer, flavor, qty, price, total});
                }
            }
            menu.showMessage("“The transection records were successfully retrieved from the text file and displayed in the table.”");    
            
        } catch (IOException e) {
            menu.showMessage("Error loading transactions: " + e.getMessage());
        }
    }

    // Views total sales in TextField    
    public void viewAllSales() {
        DefaultTableModel model = (DefaultTableModel) menu.getTransactionTable().getModel();
        model.setRowCount(0); // clear existing rows

        File transactionFile = new File("Transactions.txt");
        if (!transactionFile.exists() || transactionFile.length() == 0) {
            menu.showMessage("No transactions found yet!");
            return;
        }

        double totalSales = 0.0; // total accumulator

        try (BufferedReader reader = new BufferedReader(new FileReader(transactionFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue; // skip empty lines
                }
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    String customer = parts[0];
                    String flavor = parts[1];
                    int qty = Integer.parseInt(parts[2]);
                    double price = Double.parseDouble(parts[3]);
                    double total = Double.parseDouble(parts[4]);

                    model.addRow(new Object[]{customer, flavor, qty, price, total});
                    totalSales += total; // All sales total
                }
            }

            // after loop, show total
            menu.setTotalSales(String.valueOf(totalSales));

        } catch (IOException e) {
            menu.showMessage("Error loading transactions: " + e.getMessage());
        }
    }

}
