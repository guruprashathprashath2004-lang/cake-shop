package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.table.DefaultTableModel;
import model.Cakes;
import view.Menu;

public abstract class Functions {

    public Menu view; // Menu JFrame

    // Constructors
    public Functions(Menu view) {
        this.view = view;
    }
    
// Searches cupcakes by category and updates JTable    
    public abstract void searchCupcakeByCategory();

// Deletes a cupcake by flavor    
    public abstract void deleteCupcake();    

// Adds a new cupcake to the system and saves it to Cake_Details.txt
    public  void addCupcakeDetails() {

        // Read values from Menu JFrame text fields
        String flavor = view.getFlavorText().trim();
        String category = view.getCategoryText().trim();
        String size = view.getSizeText().trim();
        String qty = view.getQtyText().trim();
        String price = view.getPriceText().trim();

        try {

            // Validate empty fields first
            if (flavor.isEmpty() || category.isEmpty() || size.isEmpty() || qty.isEmpty() || price.isEmpty()) {
                view.showMessage("All fields (Flavor, Category, Size, Quantity, Price) must be filled!");
                return;
            }

            // Validate numeric fields
            int Qty;
            double Price;
            try {
                Qty = Integer.parseInt(qty);
                Price = Double.parseDouble(price);
            } catch (NumberFormatException e) {
                view.showMessage("Please enter valid numbers for Quantity and Price!");
                return;
            }

            // Validate positive values
            if (Qty <= 0 || Price <= 0) {
                view.showMessage("Quantity and Price must be greater than 0!");
                return;
            }

            // Create cake object
            Cakes cake = new Cakes(flavor, category, size, Qty, Price);

            // Append cake details to a text file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("Cake_Details.txt", true))) {
                writer.write(cake.getFlavor() + "|" + cake.getCategory() + "|" + cake.getSize() + "|" + cake.getQty() + "|" + cake.getPrice());
                writer.newLine();
            }

            view.showMessage("Cupcake details added successfully!");
            view.clearFields();

        } catch (IOException ex) {
            view.showMessage("Error writing file: " + ex.getMessage());
        }
    }

// Displays all cupcakes in the JTable    
    public void viewCupcakeDetails() {
        
        DefaultTableModel model = (DefaultTableModel) view.getTblcupcakes().getModel();
        model.setRowCount(0); // clear existing rows

        File ViewFile = new File("Cake_Details.txt");
        if (!ViewFile.exists() || ViewFile.length() == 0) {
            view.showMessage("No cupcake detail found yet!");
            return;
        }
        
        
        try (BufferedReader reader = new BufferedReader(
                new FileReader("Cake_Details.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|"); // split by pipe |
                if (parts.length == 5) {
                    String flavor = parts[0];
                    String category = parts[1];
                    String size = parts[2];
                    int qty = Integer.parseInt(parts[3]);
                    double price = Double.parseDouble(parts[4]);

                    model.addRow(new Object[]{flavor, category, size, qty, price});
                }
            }
            view.showMessage("“The cupcake records were successfully retrieved from the text file and displayed in the table.”");    
            
        } catch (Exception e) {
            view.showMessage("Error loading cupcakes: " + e.getMessage());
        }

    }



}
